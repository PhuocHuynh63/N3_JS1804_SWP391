package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @Column(name = "is_default")
    private boolean isDefault;

    private String title;

    @Column(name = "[address]")
    private String address;

    private String city;

    private String district;
    private String ward;

}
