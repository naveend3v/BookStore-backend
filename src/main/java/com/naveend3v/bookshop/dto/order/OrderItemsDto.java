package com.naveend3v.bookshop.dto.order;

import com.naveend3v.bookshop.entity.OrderItems;

import javax.validation.constraints.NotNull;

public class OrderItemsDto {

    private @NotNull Integer bookId;
    private @NotNull String bookName;
    private @NotNull String bookCoverImage;
    private @NotNull Integer quantity;
    private @NotNull double price;
    private @NotNull double totalPrice;


    public OrderItemsDto(){}

    public OrderItemsDto(OrderItems orderItems) {
        this.bookId = orderItems.getBook().getId();
        this.bookName = orderItems.getBook().getBookName();
        this.bookCoverImage = orderItems.getBook().getBookCoverImagePath();
        this.quantity = orderItems.getQuantity();
        this.price = orderItems.getPrice();
        this.totalPrice = orderItems.getTotalPrice();
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookCoverImage() {
        return bookCoverImage;
    }

    public void setBookCoverImage(String bookCoverImage) {
        this.bookCoverImage = bookCoverImage;
    }
}
