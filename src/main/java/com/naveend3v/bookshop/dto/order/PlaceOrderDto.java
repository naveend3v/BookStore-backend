package com.naveend3v.bookshop.dto.order;

import javax.validation.constraints.NotNull;

public class PlaceOrderDto {
    private Integer id;
    private @NotNull Integer userId;
    private @NotNull Double totalPrice;

    public PlaceOrderDto(){}

    public Double getTotalPrice() {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
