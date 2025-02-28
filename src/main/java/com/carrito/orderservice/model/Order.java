package com.carrito.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.carrito.orderservice.enums.OrderStatus;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    @EqualsAndHashCode.Include
    private String userId;

    @Column(name = "total_amount", nullable = false)
    @EqualsAndHashCode.Include
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @EqualsAndHashCode.Include
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @EqualsAndHashCode.Include
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
