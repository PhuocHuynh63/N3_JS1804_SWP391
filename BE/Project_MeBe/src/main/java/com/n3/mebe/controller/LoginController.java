package com.n3.mebe.controller;

import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.service.iml.LoginService;
import com.n3.mebe.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    ILoginService loginServiceImp;

    @Autowired
    JwtUtilHelper jwtUtilHelper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping()
    ResponseEntity<?> signin(@RequestParam String username,
                            @RequestParam String password) {
        ResponseData responseData = new ResponseData();

        /*
         * Lấy key
         * SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
         * String encrypKey = Encoders.BASE64.encode(secretKey.getEncoded());
         * System.out.println(encrypKey);
         */

        if (loginServiceImp.checkLogin(username, password)) {
            String token = jwtUtilHelper.genarateToken(username);

            // Tạo khóa Redis để lưu trữ token
            String tokenKey = "TOKEN:" + token;

            // Lưu OTP vào Redis với thời gian tồn tại (TTL) là 1 phút
            stringRedisTemplate.opsForValue().set(tokenKey, token, 1, TimeUnit.DAYS);
            String role = loginServiceImp.getUserRole(username);
            responseData.setData(token);
            responseData.setRole(role);
        } else {
            responseData.setData("");
            responseData.setDescription("username or password incorrect");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}