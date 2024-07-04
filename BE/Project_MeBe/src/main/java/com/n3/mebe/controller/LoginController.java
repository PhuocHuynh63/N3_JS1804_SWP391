package com.n3.mebe.controller;

import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.service.ILoginService;
import com.n3.mebe.service.IUserService;
import com.n3.mebe.service.iml.LoginService;
import com.n3.mebe.service.iml.UserService;
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
    private IUserService iUserService;


    @Autowired
    JwtUtilHelper jwtUtilHelper;

    @PostMapping()
    ResponseEntity<?> signin(@RequestParam String username,
                                    @RequestParam String password) {
        ResponseData responseData = new ResponseData();

        /* Lấy key
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encrypKey = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(encrypKey);
        */

        if(loginServiceImp.checkLogin(username, password)) {
            UserResponse userResponse = iUserService.getUserByUserNameResponse(username);
            String status = "active";
            String ban = "ban";
            if(userResponse.getStatus().equalsIgnoreCase(status)){
                String token = jwtUtilHelper.genarateToken(username);
                String role = loginServiceImp.getUserRole(username);
                responseData.setData(token);
                responseData.setRole(role);
            }else if(userResponse.getStatus().equalsIgnoreCase(ban)){
                responseData.setData("");
                responseData.setDescription("Tài khoản của bạn đã bị ban");
                responseData.setSuccess(false);
            }
        } else {
            responseData.setData("");
            responseData.setDescription("username hoặc password không đúng");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}