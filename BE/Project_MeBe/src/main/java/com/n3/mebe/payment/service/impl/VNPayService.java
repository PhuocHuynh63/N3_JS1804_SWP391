package com.n3.mebe.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3.mebe.payment.config.VNPayProperties;
import com.n3.mebe.payment.util.VNPayUtils;
import com.n3.mebe.order.dto.request.OrderRequest;
import com.n3.mebe.payment.dto.response.PaymentResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VNPayService {

    private final StringRedisTemplate redisTemplate;

    private final VNPayProperties vnPayProperties;


    // <editor-fold default state="collapsed" desc="createPaymentUrl">
    @Transactional
    public PaymentResponse createPaymentUrl(OrderRequest orderRequest) throws UnsupportedEncodingException, JsonProcessingException {

        String orderType = orderRequest.getOrderType() != null ? orderRequest.getOrderType().getLabel() : null;
        long amount =  (long) orderRequest.getTotalAmount()*100L; // Định dạng của VNPay 100L = 10000
        String bankCode = "NCB";

        String vnp_TxnRef = VNPayUtils.getRandomNumber(8);
        String vnp_IpAddr = VNPayUtils.getIpAddress();

        String vnp_TmnCode = vnPayProperties.tmnCode();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnPayProperties.version());
        vnp_Params.put("vnp_Command", vnPayProperties.command());
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");


        // Lưu OrderRequest vào Redis
        String orderRequestId = UUID.randomUUID().toString();
        String orderRequestKey = "orderRequest:" + orderRequestId;
        ObjectMapper objectMapper = new ObjectMapper();
        redisTemplate.opsForValue().set(orderRequestKey, objectMapper.writeValueAsString(orderRequest), 15, TimeUnit.MINUTES);

        // thêm orderId vào link return để lấy ra bẳng redis
        vnp_Params.put("vnp_ReturnUrl", vnPayProperties.returnUrl() + "?orderRequestId=" + URLEncoder.encode(orderRequestId, StandardCharsets.UTF_8));
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Đặt múi giờ TP. Hồ Chí Minh
        ZoneId hcmZone = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime now = ZonedDateTime.now(hcmZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = now.format(formatter);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        ZonedDateTime expireTime = now.plusMinutes(15);
        String vnp_ExpireDate = expireTime.format(formatter);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayUtils.hmacSHA512(vnPayProperties.secretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;




//        queryUrl += "&orderRequestId=" + URLEncoder.encode(orderRequestId, StandardCharsets.UTF_8);


        // Thay vì thêm orderRequestId vào URL, chúng ta sẽ trả về nó trong response
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus("OK");
        paymentResponse.setMessage("Successfully created payment");
        paymentResponse.setURL(vnPayProperties.payUrl() + "?" + queryUrl);
        paymentResponse.setPaymentID(vnp_TxnRef); // Set Payment ID
        paymentResponse.setOrderRequestId(orderRequestId); // Set Payment ID

        // Lưu thông tin thanh toán vào Redis
        String paymentKey = "payment:" + paymentResponse.getPaymentID();
        redisTemplate.opsForValue().set(paymentKey, "Pending", 15, TimeUnit.MINUTES);


        return  paymentResponse;
    }// </editor-fold>


    public boolean handleVNPayResponse(Map<String, String> vnp_Params) {
        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        StringBuilder hashData = new StringBuilder();
        vnp_Params.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            hashData.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
        });
        if (hashData.length() > 0) {
            hashData.setLength(hashData.length() - 1);
        }


        String responseCode = vnp_Params.get("vnp_ResponseCode");
        if("00".equals(responseCode)){
            return true; // Thanh toán thành công nếu responseCode là "00"
        } else {
            return false; // Xử lý logic khi thanh toán thất bại
        }
    }





}
