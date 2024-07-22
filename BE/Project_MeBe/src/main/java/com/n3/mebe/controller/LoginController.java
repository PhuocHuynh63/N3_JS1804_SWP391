package com.n3.mebe.controller;

import com.n3.mebe.dto.request.user.GoogleUser;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.service.IUserService;

import com.n3.mebe.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginServiceImp;

    @Autowired
    private JwtUtilHelper jwtUtilHelper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IUserService userService;

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

            // Lưu OTP vào Redis với thời gian tồn tại (TTL) là 1 days
            stringRedisTemplate.opsForValue().set(tokenKey, token, 1, TimeUnit.DAYS);
            String role = loginServiceImp.getUserRole(username);
            responseData.setData(token);
            responseData.setRole(role);
        } else {
            responseData.setData("");
            responseData.setDescription("Tên đăng nhập và password không đúng!");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    // Endpoint kiểm tra sự tồn tại của TOKEN
    @GetMapping("/check-token")
    public ResponseEntity<Map<String, String>> checkToken(@RequestParam String token) {
        Map<String, String> response = new HashMap<>();
        String tokenKey = "TOKEN:" + token;
        try {
            Boolean hasKey = stringRedisTemplate.hasKey(tokenKey);
            if (Boolean.TRUE.equals(hasKey)) {
                response.put("status", "Token exists");
            } else {
                response.put("status", "Token does not exist");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "Error checking token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


//    @GetMapping("/signingoogle")
//    public ResponseEntity<?> signinGoogle(OAuth2AuthenticationToken oauth2AuthenticationToken) {
//        ResponseData responseData = new ResponseData();
//
//        if (oauth2AuthenticationToken == null) {
//            return new ResponseEntity<>(Collections.singletonMap("error", "User is not authenticated"), HttpStatus.UNAUTHORIZED);
//        }
//
//        Map<String, Object> attributes = oauth2AuthenticationToken.getPrincipal().getAttributes();
//        GoogleUser googleUser = new GoogleUser();
//
//        googleUser.setSub((String) attributes.get("sub"));
//        googleUser.setName((String) attributes.get("name"));
//        googleUser.setGivenName((String) attributes.get("given_name"));
//        googleUser.setFamilyName((String) attributes.get("family_name"));
//        googleUser.setPicture((String) attributes.get("picture"));
//        googleUser.setEmail((String) attributes.get("email"));
//        googleUser.setEmailVerified((Boolean) attributes.get("email_verified"));
//
//
//        boolean googleUserCheck = userService.createUserGoogle(googleUser);
//        if (!googleUserCheck) {
//            return new ResponseEntity<>(Collections.singletonMap("error", "Failed to create user"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }else {
//            String token = jwtUtilHelper.genarateToken(googleUser.getEmail());
//
//            // Tạo khóa Redis để lưu trữ token
//            String tokenKey = "TOKEN:" + token;
//
//            // Lưu OTP vào Redis với thời gian tồn tại (TTL) là 1 days
//            stringRedisTemplate.opsForValue().set(tokenKey, token, 1, TimeUnit.DAYS);
//            String role = loginServiceImp.getUserRole(googleUser.getEmail());
//            responseData.setData(token);
//            responseData.setRole(role);
//        }
//
//        return new ResponseEntity<>(responseData, HttpStatus.OK);
//    }


}