package com.n3.mebe.notification.service.impl;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.notification.dto.GmailSendResponse;
import com.n3.mebe.notification.service.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendHtmlMail(GmailSendResponse response, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        Context context = new Context();
        context.setVariables(response.getProps());

        String html = templateEngine.process(templateName, context);

        helper.setTo(response.getTo());
        helper.setSubject(response.getSubject());
        helper.setText(html, true);

        mailSender.send(message);
    }
}
