package com.n3.mebe.auth.controller;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.notification.service.ISendMailService;
import com.n3.mebe.notification.service.impl.SendMailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot_password")
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final SendMailService sendMailService;

    @PostMapping("/email")
    ResponseEntity<Boolean> createSendEmail(@RequestParam String email) {
        return ResponseEntity.ok(sendMailService.createSendEmailForgot(email));
    }
}
