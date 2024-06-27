package com.n3.mebe.service.iml.paymentOrder;

import com.n3.mebe.config.Config;
import com.n3.mebe.dto.request.payment.PaymentRequest;
import com.n3.mebe.dto.response.payment.PaymentResponse;
import com.n3.mebe.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class VNPayService {

    @Autowired
    private IOrderService orderService;


    @Autowired
    private StringRedisTemplate redisTemplate;

//        // <editor-fold default state="collapsed" desc="createPaymentUrl">
//    public PaymentResponse createPaymentUrl(PaymentRequest request) throws UnsupportedEncodingException {
//        String orderType = request.getType();
//        long amount = request.getAmount() * 100L; // Định dạng của VNPay 100L = 10000
//        String bankCode = request.getBankCode();
//        String vnp_TxnRef = Config.getRandomNumber(8);
//        String vnp_IpAddr = Config.getIpAddress();
//        String vnp_TmnCode = Config.vnp_TmnCode;
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", Config.vnp_Version);
//        vnp_Params.put("vnp_Command", Config.vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.valueOf(amount));
//        vnp_Params.put("vnp_CurrCode", "VND");
//
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bankCode);
//        }
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//        vnp_Params.put("vnp_OrderType", orderType);
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//
////        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
////        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
////        formatter.setTimeZone(TimeZone.getTimeZone("Etc/GMT+7")); // Set time zone for formatter
////        String vnp_CreateDate = formatter.format(cld.getTime());
////        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
////
////        cld.add(Calendar.MINUTE, 15);
////        String vnp_ExpireDate = formatter.format(cld.getTime());
////        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//
//        // Ghi log thời gian tạo và hết hạn
//        System.out.println("vnp_CreateDate: " + vnp_CreateDate);
//        System.out.println("vnp_ExpireDate: " + vnp_ExpireDate);
//
//        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        for (String fieldName : fieldNames) {
//            String fieldValue = vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
//                query.append('&');
//                hashData.append('&');
//            }
//        }
//
//        String queryUrl = query.substring(0, query.length() - 1);
//        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.substring(0, hashData.length() - 1));
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//
//        // Ghi log URL thanh toán
//        System.out.println("Payment URL: " + paymentUrl);
//
//        PaymentResponse paymentResponse = new PaymentResponse();
//        paymentResponse.setStatus("OK");
//        paymentResponse.setMessage("Successfully created payment");
//        paymentResponse.setURL(paymentUrl);
//        paymentResponse.setPaymentID(vnp_TxnRef); // Set Payment ID
//
//        // Lưu thông tin thanh toán vào Redis
//        String paymentKey = "payment:" + paymentResponse.getPaymentID();
//        System.out.println("Saving to Redis with key: " + paymentKey + " and value: Pending");
//        redisTemplate.opsForValue().set(paymentKey, "Pending", 15, TimeUnit.MINUTES);
//
//        // Kiểm tra giá trị đã lưu trong Redis
//        String redisValue = redisTemplate.opsForValue().get(paymentKey);
//        System.out.println("Value in Redis for key " + paymentKey + ": " + redisValue);
//
//        return paymentResponse;
//    }// </editor-fold>


    // <editor-fold default state="collapsed" desc="createPaymentUrl">
    public PaymentResponse createPaymentUrl(PaymentRequest request) throws UnsupportedEncodingException {

        String orderType = request.getType();
        long amount =  request.getAmount()*100L; // Định dạng của VNPay 100L = 10000
        String bankCode = request.getBankCode();

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress();

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
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

        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
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
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;


        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus("OK");
        paymentResponse.setMessage("Successfully created payment");
        paymentResponse.setURL(paymentUrl);
        paymentResponse.setPaymentID(vnp_TxnRef); // Set Payment ID

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
