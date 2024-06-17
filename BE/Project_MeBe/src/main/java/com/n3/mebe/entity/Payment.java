package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
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

    private float amount;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String status;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "created_at")
    private Date createAt;

    @Column(name = "updated_at")
    private Date updateAt;


}
