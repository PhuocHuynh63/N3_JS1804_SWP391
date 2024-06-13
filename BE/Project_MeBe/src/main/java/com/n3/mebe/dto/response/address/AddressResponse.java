package com.n3.mebe.dto.response.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private int addressId;
    private AddressUserResponse user;
    private boolean isDefault;
    private String title;
    private String address;
    private String city;
    private String district;
    private String ward;
}
