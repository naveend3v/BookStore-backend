package com.naveend3v.bookshop.dto.cart;

import java.util.List;

public class CartDto {

    private List<CartItemsDto> cartItemsDto;
    private double totalCost;

    public CartDto(){}

    public List<CartItemsDto> getCartItemsDto() {
        return cartItemsDto;
    }

    public void setCartItemsDto(List<CartItemsDto> cartItemsDto) {
        this.cartItemsDto = cartItemsDto;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
