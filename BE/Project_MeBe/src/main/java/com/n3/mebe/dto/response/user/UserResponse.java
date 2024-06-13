package com.n3.mebe.dto.response.user;

import lombok.Data;

import java.util.Date;


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
    private Date createAt;
    private Date updateAt;
    private Date deleteAt;

}
