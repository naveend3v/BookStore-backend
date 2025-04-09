package com.naveend3v.bookshop.repository;

import com.naveend3v.bookshop.model.Cart;
import com.naveend3v.bookshop.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findAllByUserInfo(UserInfo userInfo);
    void deleteByUserInfo(UserInfo userInfo);
}
