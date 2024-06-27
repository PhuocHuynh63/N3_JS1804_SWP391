package com.n3.mebe.service;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import jakarta.mail.MessagingException;

public interface ISendMailService {

    Boolean createSendEmailForgot(String email);

}
