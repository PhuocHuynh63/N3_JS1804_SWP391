package com.n3.mebe.controller;

import com.n3.mebe.dto.request.address.CreateAddressRequest;
import com.n3.mebe.dto.request.address.UpdateAddressRequest;
import com.n3.mebe.dto.response.address.AddressResponse;
import com.n3.mebe.entity.Address;
import com.n3.mebe.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressSerivce;

    /**
     *  Request from client
     *
     */

    @PostMapping("/create/{userId}")
    Address createAddress(@RequestBody CreateAddressRequest request, @PathVariable("userId") int userId) {
        return addressSerivce.createAddress(userId, request);
    }


    @PutMapping("/update/{adrId}")
    Address updateAddress(@RequestBody UpdateAddressRequest request, @PathVariable("adrId") int addressId) {
        return addressSerivce.updateAddress(addressId, request);
    }

    @DeleteMapping("/delete/{adrId}")
    String deteleAddress(@PathVariable("adrId") int addressId) {
        addressSerivce.deleteAddress(addressId);
        return "Deleted Address successfully";
    }


    /**
     *  Response to client
     *
     */

    //Response address by user ID
    @GetMapping("/list/{userId}")
    List<AddressResponse> getAddressByUserId(@PathVariable("userId") int id) {
        return addressSerivce.getAddressesUser(id);
    }
}
