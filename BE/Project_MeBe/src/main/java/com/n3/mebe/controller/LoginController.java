package com.n3.mebe.controller;

import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private JwtUtilHelper jwtUtilHelper;

    @PostMapping
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
        ResponseData responseData = new ResponseData();

        if (loginService.checkLogin(username, password)) {
            String token = jwtUtilHelper.genarateToken(username);
            responseData.setData(token);
            responseData.setSuccess(true);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setData("Username or password is incorrect");
            responseData.setSuccess(false);
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }
}
