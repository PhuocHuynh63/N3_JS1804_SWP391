package com.n3.mebe.controller;

import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private JwtUtilHelper jwtUtilHelper;

    @PostMapping("/signin")
    public ResponseEntity<ResponseData> signin(@RequestParam String username, @RequestParam String password) {
        boolean success = loginService.checkLogin(username, password);
        ResponseData responseData = new ResponseData();
        if (success) {
            String token = jwtUtilHelper.genarateToken(username);
            responseData.setDescription("Login successful");
            responseData.setData(token);
        } else {
            responseData.setStatus(401);
            responseData.setSuccess(false);
            responseData.setDescription("Invalid username or password");
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }
}
