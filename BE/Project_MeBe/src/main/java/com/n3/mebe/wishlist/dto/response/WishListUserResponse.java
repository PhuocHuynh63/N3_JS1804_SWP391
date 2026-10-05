package com.n3.mebe.wishlist.dto.response;


import lombok.Data;

import java.util.Date;


@Data
public class WishListUserResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Date birthOfDate;
    private String phoneNumber;
    private int point;


}
