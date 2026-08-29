package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "[order]")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    String email;

    @Column(name = "phone")
    String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    Voucher voucher;

    @Column(name = "order_code")
    String orderCode;

    @Column(name = "shipping_address")
    String shipAddress;

    @Column(name = "[status]")
    String status;

    @Column(name = "total_amount")
    float totalAmount;

    @Column(name = "order_type")
    String orderType;

    @Column(name = "payment_status")
    String paymentStatus;

    String note;

    @Column(name = "created_at")
    Date createdAt;

    @Column(name = "updated_at")
    Date updatedAt;

    @OneToMany(mappedBy = "order" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;
}
