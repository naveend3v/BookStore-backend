package com.naveend3v.bookshop.repository;

import com.naveend3v.bookshop.dto.cart.CartDto;
import com.naveend3v.bookshop.entity.Cart;
import com.naveend3v.bookshop.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findAllByUserInfo(UserInfo userInfo);
}
