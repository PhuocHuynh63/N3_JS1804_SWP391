package com.n3.mebe.dto.response.order;

import com.n3.mebe.dto.response.user.UserAddressResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderUserResponse {

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
    private Date createAt;
    private Date updateAt;
    private Date deleteAt;
    private List<UserAddressResponse> listAddress;
}
