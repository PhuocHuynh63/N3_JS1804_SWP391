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
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private int voucherId;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private int discountValue;

    @Column(name = "[name]")
    private String name;

    @Column(name = "cost")
    private float cost;

    private float quantity;

    @Column(name = "minimum_apply")
    private float minimumApply;

    @Column(name = "max_discount")
    private float maxDiscount;


    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @OneToMany(mappedBy = "voucher")
    private Set<Order> orders;
}
