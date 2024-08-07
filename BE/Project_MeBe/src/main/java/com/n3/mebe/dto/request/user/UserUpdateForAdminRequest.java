package com.n3.mebe.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateForAdminRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date birthOfDate;
    private String phoneNumber;
    private String role;
    private int point;


}
