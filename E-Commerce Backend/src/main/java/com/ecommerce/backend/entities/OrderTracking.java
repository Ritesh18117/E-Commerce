package com.ecommerce.backend.entities;

import com.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Order_Tracking")
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderTracking_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",nullable = false)
    private Seller seller;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus status;
    @Column(nullable = false)
    private LocalDateTime statusChangedAt;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime updatedAt;
    public OrderTracking(){
    }
    public OrderTracking(Long id, Order order, Seller seller, OrderStatus status, LocalDateTime statusChangedAt,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.order = order;
        this.seller = seller;
        this.status = status;
        this.statusChangedAt = statusChangedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Order getOrder() {
        return order;
    }
    public LocalDateTime getStatusChangedAt() {
        return statusChangedAt;
    }
    public void setStatusChangedAt(LocalDateTime statusChangedAt) {
        this.statusChangedAt = statusChangedAt;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Seller getSeller() {
        return seller;
    }
    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
