package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.address.AddressResponse;
import com.n3.mebe.dto.response.address.AddressUserResponse;
import com.n3.mebe.dto.response.user.UserAddressResponse;
import com.n3.mebe.entity.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressMapper {

    public UserAddressResponse toUserAddressResponse(Address address) {
        UserAddressResponse response = new UserAddressResponse();
        response.setAddressId(address.getAddressId());
        response.setDefault(address.isDefault());
        response.setTitle(address.getTitle());
        response.setAddress(address.getAddress());
        return response;
    }

    public List<UserAddressResponse> toUserAddressResponseList(List<Address> addresses) {
        return addresses.stream().map(this::toUserAddressResponse).toList();
    }

    public AddressResponse toAddressResponse(Address address) {
        AddressResponse addressResponse = new AddressResponse();
        AddressUserResponse addressUserResponse = new AddressUserResponse();
        addressUserResponse.setFirstName(address.getUser().getFirstName());
        addressUserResponse.setLastName(address.getUser().getLastName());
        addressUserResponse.setPhoneNumber(address.getUser().getPhoneNumber());
        addressUserResponse.setAvatar(address.getUser().getAvatar());
        addressUserResponse.setEmail(address.getUser().getEmail());
        addressResponse.setUser(addressUserResponse);
        addressResponse.setAddressId(address.getAddressId());
        addressResponse.setDefault(address.isDefault());
        addressResponse.setTitle(address.getTitle());
        addressResponse.setAddress(address.getAddress());
        return addressResponse;
    }

    public List<AddressResponse> toAddressResponseList(List<Address> addresses) {
        return addresses.stream().map(this::toAddressResponse).toList();
    }
}
