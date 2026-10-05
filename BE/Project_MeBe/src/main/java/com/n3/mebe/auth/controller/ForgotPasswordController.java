package com.n3.mebe.auth.controller;

import com.n3.mebe.notification.service.ISendMailService;
import com.n3.mebe.notification.service.impl.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/forgot_password")
public class ForgotPasswordController {
    @Autowired
    private SendMailService sendMailService;

    @PostMapping("/email")
    ResponseEntity<Boolean> createSendEmail(@RequestParam String email) {
        return ResponseEntity.ok(sendMailService.createSendEmailForgot(email));
    }
}
