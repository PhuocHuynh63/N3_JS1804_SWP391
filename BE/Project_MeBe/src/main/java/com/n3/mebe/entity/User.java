package com.n3.mebe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Data
@Table(name = "[user]")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[user_id]")
    int userId;

     String avatar;
    @Column(name = "first_name")
     String firstName;

    @Column(name = "last_name")
     String lastName;

     String username;
     String email;

     String password;

    @Column(name = "[birth_date]")
     Date birthOfDate;

     String role;

    @Column(name = "phone")
     String phoneNumber;

     int point;

    @Column(name = "[status]")
     String status;

    @Column(name = "created_at")
     Date createAt;

    @Column(name = "updated_at")
     Date updateAt;

    @Column(name = "deleted_at")
     Date deleteAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Address> listAddress = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Review> reviewsUser = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Order> orders = new ArrayList<>();
}
