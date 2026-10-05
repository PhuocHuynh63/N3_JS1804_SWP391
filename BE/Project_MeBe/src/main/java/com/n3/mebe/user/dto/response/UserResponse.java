package com.n3.mebe.user.dto.response;

import com.n3.mebe.user.entity.UserRole;
import com.n3.mebe.user.entity.UserStatus;


import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
import java.util.List;



@Data
public class UserResponse {

    private int id;
    private String avatar;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private Date birthOfDate;
    private String phoneNumber;
    private int point;
    private UserStatus status;

    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private List<UserAddressResponse> listAddress;
    private List<UserOrderResponse> order;

}
