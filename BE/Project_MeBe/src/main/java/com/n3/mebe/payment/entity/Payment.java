package com.n3.mebe.payment.entity;

import java.math.BigDecimal;
import com.n3.mebe.order.entity.Order;

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
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal amount;

    @Column(name = "payment_type")
    @Convert(converter = PaymentMethod.JpaConverter.class)
    private PaymentMethod paymentType;

    @Column(name = "payment_status")
    @Convert(converter = PaymentStatus.JpaConverter.class)
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "created_at")
    private Date createAt;

    @Column(name = "updated_at")
    private Date updateAt;


}
