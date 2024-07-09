package com.n3.mebe.dto.response.user;


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
    private String role;
    private Date birthOfDate;
    private String phoneNumber;
    private int point;
    private String status;

    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private List<UserAddressResponse> listAddress;
    private List<UserOrderResponse> order;

}
