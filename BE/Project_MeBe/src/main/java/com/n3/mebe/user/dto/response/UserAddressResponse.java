package com.n3.mebe.user.dto.response;


import lombok.Data;

@Data
public class UserAddressResponse {
    private int addressId;
    private boolean isDefault;
    private String title;
    private String address;

}
