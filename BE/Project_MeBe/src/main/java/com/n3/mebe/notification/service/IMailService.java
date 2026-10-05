package com.n3.mebe.notification.service;

import com.n3.mebe.notification.dto.GmailSendResponse;
import jakarta.mail.MessagingException;

public interface IMailService {
    void sendHtmlMail(GmailSendResponse response, String templateName) throws MessagingException;
}
