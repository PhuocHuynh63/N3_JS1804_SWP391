package com.n3.mebe.controller.payment;


import com.n3.mebe.dto.TransactionStatusDTO;
import com.n3.mebe.dto.request.payment.PaymentRequest;

import com.n3.mebe.dto.response.payment.PaymentResponse;

import com.n3.mebe.service.iml.paymentOrder.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/payment")
public class VnpayController {


    @Autowired
    private VNPayService paymentService;

    @PostMapping("/create")
    ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) throws UnsupportedEncodingException {

        PaymentResponse response = paymentService.createPaymentUrl(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/payment_info")
    ResponseEntity<?> transaction(
            @RequestParam Map<String, String> params) {

        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        Map<String, String> vnp_Params = new HashMap<>(params);

        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");

        // Build hash data
        String hashData = vnp_Params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));


        String responseCode = vnp_Params.get("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
                transactionStatusDTO.setStatus("Ok");
                transactionStatusDTO.setMessage("Successfully");
                transactionStatusDTO.setData(""); // Bạn có thể đặt dữ liệu cần thiết vào đây
            } else {
                transactionStatusDTO.setStatus("No");
                transactionStatusDTO.setMessage("Failed");
                transactionStatusDTO.setData(""); // Bạn có thể đặt dữ liệu cần thiết vào đây
            }


        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }




}
