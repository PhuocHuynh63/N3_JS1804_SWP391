package com.n3.mebe.order.entity;

import java.math.BigDecimal;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.payment.entity.PaymentMethod;
import com.n3.mebe.payment.entity.PaymentStatus;
import com.n3.mebe.voucher.entity.Voucher;

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
    @Convert(converter = OrderStatus.JpaConverter.class)
    OrderStatus status;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Column(name = "order_type")
    @Convert(converter = PaymentMethod.JpaConverter.class)
    PaymentMethod orderType;

    @Column(name = "payment_status")
    @Convert(converter = PaymentStatus.JpaConverter.class)
    PaymentStatus paymentStatus;

    String note;

    @Column(name = "created_at")
    Date createdAt;

    @Column(name = "updated_at")
    Date updatedAt;

    @OneToMany(mappedBy = "order" ,fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;
}
