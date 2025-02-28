package com.carrito.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_items")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @EqualsAndHashCode.Include
    @Column(name = "product_id", nullable = false)
    private String productId;

    @EqualsAndHashCode.Include
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @EqualsAndHashCode.Include
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}

