package com.n3.mebe.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private User user;

    @Column(name = "is_default")
    private boolean isDefault;

    private String title;

    @Column(name = "[address]")
    private String address;


}
