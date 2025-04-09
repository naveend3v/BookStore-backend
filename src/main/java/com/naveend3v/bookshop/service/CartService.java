package com.naveend3v.bookshop.service;

import com.naveend3v.bookshop.exceptions.CustomException;
import com.naveend3v.bookshop.dto.cart.AddToCartDto;
import com.naveend3v.bookshop.dto.cart.CartDto;
import com.naveend3v.bookshop.dto.cart.CartItemsDto;
import com.naveend3v.bookshop.model.Book;
import com.naveend3v.bookshop.model.Cart;
import com.naveend3v.bookshop.model.UserInfo;
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
        // Fetch the book from the database
        Optional<Book> book = booksService.findByBook(addToCartDto.getProduct_id());
        if (!book.isPresent()) {
            throw new CustomException("Book with id not found : " + addToCartDto.getProduct_id());
        }

        // Check if the user exists
        if (!userRepository.existsById(userInfo.getId())) {
            throw new CustomException("User with id not found : " + userInfo.getId());
        }

        // Fetch all cart items for the user
        List<Cart> listOfCartForUser = cartRepository.findAllByUserInfo(userInfo);

        // Flag to track if the book is already in the cart
        boolean isBookInCart = false;

        // If the user's cart is not empty, check if the book is already in the cart
        if (!listOfCartForUser.isEmpty()) {
            for (Cart cart : listOfCartForUser) {
                // If the book is already in the cart, update the quantity
                if (cart.getBook().getId() == addToCartDto.getProduct_id()) {
                    isBookInCart = true; // Book is found in the cart
                    Integer cartQuantity = cart.getQuantity() + addToCartDto.getQuantity();
                    cart.setQuantity(cartQuantity);
                    cartRepository.save(cart);
                    break; // Exit the loop after updating the quantity
                }
            }
        }

        // If the book is not in the cart, add it to the cart
        if (!isBookInCart) {
            Cart newCart = new Cart();
            newCart.setUserInfo(userInfo);
            newCart.setBook(book.get());
            newCart.setQuantity(addToCartDto.getQuantity());
            cartRepository.save(newCart);
        }
    }

    public CartDto listCartItems(UserInfo currentUserInfo) {
        List<Cart> cartList = cartRepository.findAllByUserInfo(currentUserInfo);
        List<CartItemsDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for(Cart cart : cartList){
            CartItemsDto cartItemsDto = new CartItemsDto(cart);
            totalCost += cartItemsDto.getQuantity() * cart.getBook().getPrice();
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

    public void deleteCartItem(int id,int userId) throws CustomException {
        if (!cartRepository.existsById(id))
            throw new CustomException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);
    }

    public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }

    public void deleteUserCartItems(UserInfo userInfo) {
        cartRepository.deleteByUserInfo(userInfo);
    }
}
