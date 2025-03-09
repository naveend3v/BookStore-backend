package com.naveend3v.bookshop.dto.cart;

import com.naveend3v.bookshop.entity.Book;
import com.naveend3v.bookshop.entity.Cart;

public class CartItemsDto {
    private Integer id;
    private Integer quantity;
    private Book book;

    public CartItemsDto() {
    }

    public CartItemsDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setBook(cart.getBook());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
