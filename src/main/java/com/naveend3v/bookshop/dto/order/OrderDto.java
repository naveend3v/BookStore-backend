package com.naveend3v.bookshop.dto.order;

import com.naveend3v.bookshop.entity.Order;

import javax.validation.constraints.NotNull;

public class OrderDto {
    private Integer id;
    private @NotNull Integer userId;

    public OrderDto(Order order) {
        this.setId(order.getId());
        this.setUserId((order.getUserId()));
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
}
