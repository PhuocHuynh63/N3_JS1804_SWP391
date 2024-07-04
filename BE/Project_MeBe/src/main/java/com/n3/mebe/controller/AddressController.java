package com.n3.mebe.controller;

import com.n3.mebe.dto.request.address.CreateAddressRequest;
import com.n3.mebe.dto.request.address.UpdateAddressRequest;

import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.address.AddressResponse;

import com.n3.mebe.service.IAddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<ResponseData> createAddress(@RequestBody CreateAddressRequest request, @PathVariable("userId") int userId) {
        boolean check = addressSerivce.createAddress(userId, request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Address created successfully");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Address creation failed");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }


    @PutMapping("/update/{id}")
    ResponseEntity<ResponseData> updateAddress(@RequestBody UpdateAddressRequest request, @PathVariable("id") int addressId) {
        boolean check = addressSerivce.updateAddress(addressId, request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Address update successfully");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Address update failed");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PutMapping("/update/default/{id}")
    ResponseEntity<ResponseData> updateDef(@PathVariable("id") int addressId, @RequestParam boolean defaultValue) {
        boolean check = addressSerivce.updateDefault(addressId, defaultValue);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Set default successfully");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Set default failed");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }






    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseData> deleteAddress(@PathVariable("id") int addressId) {
        addressSerivce.deleteAddress(addressId);
        ResponseData responseData = new ResponseData();
        responseData.setDescription("Address delete successfully");
        responseData.setSuccess(true);
        responseData.setStatus(200);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
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
