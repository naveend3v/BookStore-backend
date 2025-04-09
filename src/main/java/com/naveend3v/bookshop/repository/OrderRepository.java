package com.naveend3v.bookshop.repository;

import com.naveend3v.bookshop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findAllByUserIdOrderByCreatedDateDesc(Integer userid);
    List<Orders> findAllByUserId(Integer userid);
}
