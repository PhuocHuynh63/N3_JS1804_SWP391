package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "[order]")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @Column(name = "[status]")
    private String status;

    @Column(name = "delivery_fee")
    private float deliveryFee;

    @Column(name = "total_amount")
    private float totalAmount;

    @Column(name = "deposite_amount")
    private float depositeAmount;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "payment_status")
    private String paymentStatus;

    private String note;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}
