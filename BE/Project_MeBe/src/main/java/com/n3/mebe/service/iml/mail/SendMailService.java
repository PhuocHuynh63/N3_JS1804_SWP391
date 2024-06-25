package com.n3.mebe.service.iml.mail;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.service.ISendMailService;
import com.n3.mebe.service.iml.UserService;
import com.n3.mebe.util.ConstEmail;
import com.n3.mebe.util.DataUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SendMailService implements ISendMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    UserService userService;

    @Override
    public Boolean createSendEmail(String email) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            User user = userService.getUserByEmail(email);
            response.setTo(user.getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            Map<String, Object> props = new HashMap<>();
            props.put("firstName", user.getFirstName());
            props.put("lastName", user.getLastName());
            props.put("username", user.getUsername());
            props.put("password", DataUtils.generateTempPwd(6));
            response.setProps(props);

            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }
}
