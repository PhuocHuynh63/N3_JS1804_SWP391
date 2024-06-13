package com.n3.mebe.dto.response.address;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUserResponse {
    private String avatar;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
