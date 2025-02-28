package com.carrito.orderservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carrito.orderservice.enums.OrderStatus;
import com.carrito.orderservice.model.Order;
import com.carrito.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    /**
     * Save Order
     * @param Order data to save.
     * @return Order Saved
     */
    @Override
    public Order saveOrder(Order order) {
        BigDecimal totalAmount = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);
        order.getItems().forEach(item -> item.setOrder(order));
        order.setStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }

     /**
     * Search for a order by their User's id.
     *
     * @param User's id in the order.
     * @return List<Order>.
     */
    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Search for a order by their id.
     * @param Order's id in the order.
     * @return Order if it exists, or empty if it is not found.
     */
    @Override
    public Optional<Order> getOrderById(String orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * Update Order
     * @param Order id to Update
     * @param Order data to update.
     * @return Order updated
     */
    @Override
    public Order updateOrder(String id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(updatedOrder.getStatus());
                    order.getItems().clear();
                    updatedOrder.getItems().forEach(item -> {
                        item.setOrder(order);
                        order.getItems().add(item);
                    });
                    BigDecimal totalAmount = updatedOrder.getItems().stream()
                            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    order.setTotalAmount(totalAmount);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    /**
     * Delete order in DB.
     * @param Order's id
     */
    @Override
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
    
}
