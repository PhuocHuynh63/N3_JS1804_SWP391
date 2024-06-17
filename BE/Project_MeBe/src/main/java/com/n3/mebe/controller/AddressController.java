package com.n3.mebe.controller;

import com.n3.mebe.dto.request.address.CreateAddressRequest;
import com.n3.mebe.dto.request.address.UpdateAddressRequest;
import com.n3.mebe.dto.response.address.AddressResponse;
import com.n3.mebe.entity.Address;
import com.n3.mebe.service.iml.AddressSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressSerivce  addressSerivce;

    /**
     *  Request from client
     *
     */

    @PostMapping("/create_address/user_id={userId}")
    Address CreateAddress(@RequestBody CreateAddressRequest request, @PathVariable("userId") int userId) {
        return addressSerivce.createAddress(userId, request);
    }


    @PutMapping("/update_address/address_id={id}")
    Address CreateAddress(@RequestBody UpdateAddressRequest request, @PathVariable("id") int addressId) {
        return addressSerivce.updateAddress(addressId, request);
    }


    /**
     *  Response to client
     *
     */

    //Response address by user ID
    @GetMapping("/user_id={id}")
    List<AddressResponse> getAddressByUserId(@PathVariable int id) {
        return addressSerivce.getAddressesUser(id);
    }
}
