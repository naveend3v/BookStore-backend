package com.naveend3v.bookshop.service;

import com.naveend3v.bookshop.exceptions.CustomException;
import com.naveend3v.bookshop.dto.cart.AddToCartDto;
import com.naveend3v.bookshop.dto.cart.CartDto;
import com.naveend3v.bookshop.dto.cart.CartItemsDto;
import com.naveend3v.bookshop.entity.Book;
import com.naveend3v.bookshop.entity.Cart;
import com.naveend3v.bookshop.entity.UserInfo;
import com.naveend3v.bookshop.repository.CartRepository;
import com.naveend3v.bookshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private BooksService booksService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    public void addToCart(AddToCartDto addToCartDto, UserInfo userInfo) {
        Optional<Book> book = booksService.findByBook(addToCartDto.getProduct_id());
        try {
            if (book.isPresent()){
            Cart cart = new Cart();
            cart.setUserInfo(userInfo);
            cart.setUserInfo(userInfo);
            cart.setBook(book.get());
            cart.setQuantity(addToCartDto.getQuantity());
            cartRepository.save(cart);
            } else {
                throw new CustomException("Book with id not found : " + addToCartDto.getProduct_id());
            }
        } catch (Exception e){
            throw new CustomException("Book with id not found : " + addToCartDto.getProduct_id());
        }

    }

    public CartDto listCartItems(UserInfo currentUserInfo) {
        List<Cart> cartList = cartRepository.findAllByUserInfo(currentUserInfo);
        List<CartItemsDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for(Cart cart : cartList){
            CartItemsDto cartItemsDto = new CartItemsDto(cart);
            totalCost+=cartItemsDto.getQuantity() * cart.getBook().getPrice();
            cartItems.add(cartItemsDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItemsDto(cartItems);
        return cartDto;
    }

    public void deleteCart(Integer cartItemID, UserInfo currentUser){
        Optional<Cart> cartToDelete = cartRepository.findById(cartItemID);
        if(cartToDelete.isPresent()){
            Cart cart = cartToDelete.get();
            if(cart.getUserInfo() != currentUser){
                throw new CustomException("Cart item doesn't belong to user with cart id : " + cartItemID);
            }
            cartRepository.delete(cart);
        } else {
            throw new CustomException("Cart item id is Invalid! : " + cartItemID);
        }
    }

    public void updateCartItem(UserInfo userInfo,Integer cartItemID, AddToCartDto addToCartDto){
        Cart existingCart = cartRepository.findById(cartItemID).get();
        Cart cartToUpdate = new Cart();
        if((existingCart.getUserInfo()==userInfo)){
            cartToUpdate.setId(cartItemID);
            cartToUpdate.setBook(booksService.findByBook(addToCartDto.getProduct_id()).get());
            cartToUpdate.setUserInfo(userInfo);
            cartToUpdate.setQuantity(addToCartDto.getQuantity());
            cartRepository.save(cartToUpdate);
        } else{
            throw new CustomException("Cart doesn't belong to user with cart id : " + cartItemID);
        }
    }
}
