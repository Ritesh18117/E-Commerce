package com.ecommerce.backend.entities;

import com.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
