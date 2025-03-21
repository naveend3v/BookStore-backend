package com.naveend3v.bookshop.service;

import com.naveend3v.bookshop.entity.OrderItem;
import com.naveend3v.bookshop.repository.OrderItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderItemsService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public void addOrderedBooks(OrderItem orderItem){
        orderItemsRepository.save(orderItem);
    }
}
