package com.n3.mebe.controller;

import com.n3.mebe.service.ISendMailService;
import com.n3.mebe.service.iml.mail.SendMailService;
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
