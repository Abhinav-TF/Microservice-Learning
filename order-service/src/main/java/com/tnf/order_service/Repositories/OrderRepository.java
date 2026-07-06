package com.tnf.order_service.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tnf.order_service.Entities.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerEmail(String email);
    List<Order> findByStatus(String status);
    List<Order> findByProductId(String productId);
    List<Order> findByCustomerEmailAndStatus(String email, String status);
    /* 
        We already have these methods:
            - save()
            - findAll()
            - findById()
            - deleteById()
            - count()
    */
}
