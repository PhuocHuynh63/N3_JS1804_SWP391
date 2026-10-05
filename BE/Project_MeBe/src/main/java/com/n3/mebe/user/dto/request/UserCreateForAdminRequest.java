package com.n3.mebe.user.dto.request;

import com.n3.mebe.user.entity.UserRole;
import com.n3.mebe.user.entity.UserStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateForAdminRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date birthOfDate;
    private String phoneNumber;
    private UserRole role;
    private int point;
    private UserStatus status;


}
