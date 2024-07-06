package com.n3.mebe.controller;


import com.n3.mebe.dto.request.address.CreateAddressRequest;
import com.n3.mebe.dto.request.voucher.VoucherRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.address.AddressResponse;
import com.n3.mebe.dto.response.voucher.VoucherResponse;
import com.n3.mebe.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/voucher")
public class VoucherController {


    @Autowired
    private IVoucherService iVoucherService;


    /**
     * Request from client
     *
     */

    // Tạo voucher
    @PostMapping("/create")
    ResponseEntity<ResponseData> createVoucher(@RequestBody VoucherRequest request) {
        boolean check = iVoucherService.createVoucher(request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Tạo mã khuyến mãi thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Tạo mã khuyến mãi thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // Update voucher
    @PutMapping("/update/vId={id}")
    ResponseEntity<ResponseData> updateVoucher(@PathVariable("id") int id, @RequestBody VoucherRequest request) {
        boolean check = iVoucherService.updateVoucher(id,request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Chỉnh sửa mã khuyến mãi thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Chỉnh sửa mã khuyến mãi thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    //Update active
    @PutMapping("/update/active/vId={id}")
    ResponseEntity<ResponseData> setActive(@PathVariable("id") int id, @RequestParam boolean status) {
        boolean check = iVoucherService.setActive(id ,status);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Chỉnh sửa mã khuyến mãi thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Chỉnh sửa mã khuyến mãi thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    //Update public
    @PutMapping("/update/public/vId={id}")
    ResponseEntity<ResponseData> setPublic(@PathVariable("id") int id, @RequestParam boolean status) {
        boolean check = iVoucherService.setPublic(id ,status);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Chỉnh sửa mã khuyến mãi thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Chỉnh sửa mã khuyến mãi thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }


    //
    @GetMapping("/check_used")
    ResponseEntity<ResponseData> checkUsedVoucher(@RequestParam String code, @RequestParam int userId) {
        boolean check = iVoucherService.checkUsedVoucher(code ,userId);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Mã khuyến mãi đã được sử dụng");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Mãi khuyến mãi chưa được sử dụng");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }


    @DeleteMapping("/delete/vId={id}")
    ResponseEntity<ResponseData> setPublic(@PathVariable("id") int id) {
        iVoucherService.deleteVoucher(id);
        ResponseData responseData = new ResponseData();

        responseData.setDescription("Xóa mã khuyến mãi thành công");
        responseData.setSuccess(true);
        responseData.setStatus(200);


        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    /**
     * Response to client
     *
     */

    // Response All voucher
    @GetMapping("/list")
    List<VoucherResponse> getAllVoucher() {
        return  iVoucherService.getListVoucherResponseAll();
    }

    // Response voucher by ID
    @GetMapping("/vId={id}")
    VoucherResponse getVoucherById(@PathVariable("id") int vId) {
        return  iVoucherService.getVoucherByIdResponse(vId);
    }

    // Response voucher by Code
    @GetMapping("/code")
    VoucherResponse getVoucherById(@RequestParam String code) {
        return  iVoucherService.getVoucherByCodeResponse(code);
    }

    // Response voucher by Code
    @GetMapping("/search")
    List<VoucherResponse> searchVoucherByName(@RequestParam String name) {
        return  iVoucherService.searchVoucherByName(name);
    }

}
