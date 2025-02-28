package com.carrito.orderservice.service;

import java.util.List;
import java.util.Optional;

import com.carrito.orderservice.model.Order;

public interface OrderService {
    public Order saveOrder(Order order);

    public List<Order> getOrdersByUserId(String userId);

    public Optional<Order> getOrderById(String orderId);

    public Order updateOrder(String id, Order updatedOrder);

    public void deleteOrder(String id);
}
