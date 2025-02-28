package com.carrito.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.orderservice.model.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    List<OrderItem> findByOrderId(String orderId);
    
}
