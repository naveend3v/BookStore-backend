package com.naveend3v.bookshop.service;

import com.naveend3v.bookshop.dto.cart.CartDto;
import com.naveend3v.bookshop.dto.checkout.CheckoutItemDto;
import com.naveend3v.bookshop.dto.order.PlaceOrderDto;
import com.naveend3v.bookshop.entity.Order;
import com.naveend3v.bookshop.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void placeOrder(int userId, String sessionId){
        CartDto cartDto = cartService.listCartItems(userInfoService.findById(userId).get());
        
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setUserId(userId);
        placeOrderDto.setTotalPrice((cartDto.getTotalCost()));
        
        int orderId = saveOrder(placeOrderDto,userId,sessionId);
        
    }

    public int saveOrder(PlaceOrderDto orderDto, int userId, String sessionId) {
        Order order = getOrderFromDto(orderDto,userId,sessionId);
        return orderRepository.save(order).getId();
    }

    public Order getOrderFromDto(PlaceOrderDto orderDto, int userId, String sessionId){
        Order order = new Order(orderDto, userId, sessionId);
        return  order;
    }


    public List<Order> listOrders(int userId){
        List<Order> orderList = orderRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        return orderList;
    }


}