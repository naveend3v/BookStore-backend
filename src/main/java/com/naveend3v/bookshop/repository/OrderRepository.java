package com.naveend3v.bookshop.repository;

import com.naveend3v.bookshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUserIdOrderByCreatedDateDesc(Integer userid);
}
