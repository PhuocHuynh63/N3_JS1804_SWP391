package com.n3.mebe.dto.response.user;


import lombok.Data;

@Data
public class UserAddressResponse {
    private int addressId;
    private boolean isDefault;
    private String title;
    private String address;

}
