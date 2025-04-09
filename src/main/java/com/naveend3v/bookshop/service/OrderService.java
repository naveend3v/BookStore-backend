package com.naveend3v.bookshop.service;

import com.naveend3v.bookshop.dto.cart.CartDto;
import com.naveend3v.bookshop.dto.cart.CartItemsDto;
import com.naveend3v.bookshop.dto.checkout.CheckoutItemDto;
import com.naveend3v.bookshop.dto.order.GetOrderDetailsDto;
import com.naveend3v.bookshop.dto.order.OrderDto;
import com.naveend3v.bookshop.entity.OrderItems;
import com.naveend3v.bookshop.entity.Orders;
import com.naveend3v.bookshop.entity.UserInfo;
import com.naveend3v.bookshop.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserInfoService userInfoService;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        //success and failure url
        String successURL = baseURL + "payment/success";
        String failureURL = baseURL + "payment/failed";

        Stripe.apiKey = secretKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .addAllLineItem(sessionItemList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long) (checkoutItemDto.getPrice() * 100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                ).build();
    }

    public void placeOrder(UserInfo userInfo, String sessionId){

        CartDto cartDto = cartService.listCartItems(userInfo);

        List<CartItemsDto> cartItemDtoList = cartDto.getCartItemsDto();

        Orders newOrders = new Orders();
        newOrders.setUserId(userInfo.getId());
        newOrders.setCreatedDate(LocalDate.now());
        newOrders.setTotalPrice(cartDto.getTotalCost());
        newOrders.setSessionId(sessionId);
        orderRepository.save(newOrders);

        for (CartItemsDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
            OrderItems orderItem = new OrderItems();
            orderItem.setOrderId(newOrders.getId());
            orderItem.setCreatedDate(LocalDate.now());
            orderItem.setBookId(cartItemDto.getBook().getId());
            orderItem.setBookName(cartItemDto.getBook().getBookName());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setPrice(cartItemDto.getBook().getPrice());
            orderItem.setTotalPrice(cartItemDto.getQuantity() * cartItemDto.getBook().getPrice());
            // add to order item list
            orderItemsService.addOrderedBooks(orderItem);
        }
        //
        cartService.deleteUserCartItems(userInfo);
    }

    public List<OrderDto> getUserOrders(int userId) {

        List<Orders> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }
}