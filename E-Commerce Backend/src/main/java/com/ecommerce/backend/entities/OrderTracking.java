package com.ecommerce.backend.entities;

import com.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Order_Tracking")
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderTracking_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id",nullable = false)
    private Seller seller;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus status;
    @Column(nullable = true)
    private Date statusChangedAt;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;
    @Column(nullable = true)
    private String alert;
    public OrderTracking(){
    }
    public OrderTracking(Long id, Order order, Seller seller, OrderStatus status, Date statusChangedAt,
            Date createdAt, Date updatedAt,String alert) {
        this.id = id;
        this.order = order;
        this.seller = seller;
        this.status = status;
        this.statusChangedAt = statusChangedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.alert = alert;
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
    public Date getStatusChangedAt() {
        return statusChangedAt;
    }
    public void setStatusChangedAt(Date statusChangedAt) {
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
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getAlert() {
        return alert;
    }
    public void setAlert(String alert) {
        this.alert = alert;
    }
}
