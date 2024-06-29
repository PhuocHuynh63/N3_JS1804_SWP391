package com.n3.mebe.service;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import jakarta.mail.MessagingException;

public interface IMailService {
    void sendHtmlMail(GmailSendResponse response, String templateName) throws MessagingException;
}
