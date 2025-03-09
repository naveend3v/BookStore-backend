package com.naveend3v.bookshop.dto.order;

import javax.validation.constraints.NotNull;

public class OrderItemsDto {

    private @NotNull double price;
    private @NotNull Integer quantity;
    private @NotNull Integer bookId;
    private @NotNull Integer productId;

    public OrderItemsDto(){}

    public OrderItemsDto(@NotNull double price,@NotNull Integer quantity,@NotNull Integer bookId,@NotNull Integer productId) {
        this.price = price;
        this.quantity = quantity;
        this.bookId = bookId;
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
