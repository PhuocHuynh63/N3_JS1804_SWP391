package com.n3.mebe.controller;

import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.service.iml.LoginService;
import com.n3.mebe.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    ILoginService loginServiceImp;

    @Autowired
    JwtUtilHelper jwtUtilHelper;

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