package com.naveend3v.bookshop.controller;

import com.naveend3v.bookshop.dto.cart.AddToCartDto;
import com.naveend3v.bookshop.dto.cart.CartDto;
import com.naveend3v.bookshop.dto.checkout.CheckoutItemDto;
import com.naveend3v.bookshop.dto.request.JwtAuthRequest;
import com.naveend3v.bookshop.dto.response.AuthResponse;
import com.naveend3v.bookshop.dto.response.BookResponse;
import com.naveend3v.bookshop.dto.response.ErrorResponse;
import com.naveend3v.bookshop.dto.response.SuccessResponse;
import com.naveend3v.bookshop.entity.*;
import com.naveend3v.bookshop.exceptions.CustomException;
import com.naveend3v.bookshop.exceptions.ProductNotExistException;
import com.naveend3v.bookshop.service.BooksService;
import com.naveend3v.bookshop.jwt.JwtService;
import com.naveend3v.bookshop.service.UserInfoService;
import com.naveend3v.bookshop.service.CartService;
import com.naveend3v.bookshop.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.naveend3v.bookshop.dto.checkout.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public UserInfo getAuthenticatedtUser() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException("User is not authenticated");
        }
        UserInfo userInfo = userInfoService.findByName(authentication.getName()).orElse(null);
        System.out.println("Auth user: " + authentication.getName());
        if (userInfo == null) {
            throw new CustomException("User not found");
        }
        return  userInfo;
    }

    @PostMapping("/signup")
    public ResponseEntity addUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRoles("ROLE_USER");
        UserInfo addedUser = userInfoService.saveUser(userInfo);
        if(addedUser == null){
            return new ResponseEntity("Error adding the user " + userInfo.getName(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity("User " + userInfo.getName()+ " registered successfully.",HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity authenticateAndGetToken(@RequestBody JwtAuthRequest JwtAuthRequest){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JwtAuthRequest.getUsername(),JwtAuthRequest.getPassword()));

        if(auth.isAuthenticated()){
            return AuthResponse.generateResp(jwtService.generateToken(JwtAuthRequest.getUsername()),HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("User " + JwtAuthRequest.getUsername() + " not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books")
    public ResponseEntity getAllBooks() {
        return SuccessResponse.generateResp(booksService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksByID(@PathVariable Integer id){
        Integer booksSize = booksService.findAll().size();
        Optional<Book> book = Optional.empty();

        System.out.println("Book size : " + booksSize);
        try {
            return BookResponse.generateResp(booksService.findByBook(id),HttpStatus.OK);
        } catch (Exception error){
            return ErrorResponse.generateResp(error.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto){
        cartService.addToCart(addToCartDto,getAuthenticatedtUser());
        return SuccessResponse.generateResp("Added to Cart",HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<?> getCartItems(){
        CartDto cartDto = cartService.listCartItems(getAuthenticatedtUser());
        return SuccessResponse.generateResp(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/cart/delete/{cartItemID}")
    public ResponseEntity<?> DeleteCartItem(@PathVariable Integer cartItemID){
        cartService.deleteCart(cartItemID,getAuthenticatedtUser());
        return SuccessResponse.generateResp("Cart deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/cart/update/{cartItemID}")
    public ResponseEntity<?> updateCartItem(@PathVariable Integer cartItemID, @RequestBody AddToCartDto cartDto){
        cartService.updateCartItem(getAuthenticatedtUser(),cartItemID,cartDto);
        return SuccessResponse.generateResp("Cart updated successfully!",HttpStatus.OK);
    }

    @PostMapping("/order/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDto) throws StripeException {
        Session session = orderService.createSession(checkoutItemDto);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return ResponseEntity.ok(stripeResponse);
    }

    @PostMapping("/order/add")
    public ResponseEntity<?> placeOrder(@RequestParam("sessionId") String sessionId) throws ProductNotExistException, CustomException {
        UserInfo userInfo = getAuthenticatedtUser();
        orderService.placeOrder(userInfo.getId(),sessionId);
        return SuccessResponse.generateResp("Order has been placed!",HttpStatus.CREATED);
    }

    @GetMapping("/order/allOrders")
    public ResponseEntity<?> getAllOrders(){
        UserInfo userInfo = getAuthenticatedtUser();
        List<Order> orderDtoList = orderService.listOrders(userInfo.getId());
        return SuccessResponse.generateResp(orderDtoList.toString(),HttpStatus.OK);
    }
}
