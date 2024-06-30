package com.n3.mebe.service;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import com.n3.mebe.entity.Order;
import jakarta.mail.MessagingException;

public interface ISendMailService {

    Boolean createSendEmailForgot(String email);


    Boolean createSendEmailVerifyOrder(String email, Order order);

}
