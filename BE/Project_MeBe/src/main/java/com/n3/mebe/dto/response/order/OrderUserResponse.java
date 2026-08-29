package com.n3.mebe.dto.response.order;

import com.n3.mebe.dto.response.user.UserAddressResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUserResponse {

    int id;
    String avatar;
    String firstName;
    String lastName;
    String username;
    String email;
    String password;
    String role;
    Date birthOfDate;
    String phoneNumber;
    int point;
    Date createAt;
    Date updateAt;
    Date deleteAt;

    List<UserAddressResponse> listAddress;
}

