package com.n3.mebe.service.iml.mail;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.repository.IUserRepository;
import com.n3.mebe.service.ISendMailService;
import com.n3.mebe.service.iml.UserService;
import com.n3.mebe.util.ConstEmail;
import com.n3.mebe.util.DataUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SendMailService implements ISendMailService {
    @Autowired
    private MailService mailService;

    @Autowired
    UserService userService;

    @Autowired
    IUserRepository IUserRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    // <editor-fold default state="collapsed" desc="create Send Email Forgot">
    @Override
    public Boolean createSendEmailForgot(String email) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            User user = userService.getUserByEmail(email);
            response.setTo(user.getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            String password = DataUtils.generateTempPwd(6);
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);
            IUserRepository.save(user);



            Map<String, Object> props = new HashMap<>();
            props.put("firstName", user.getFirstName());
            props.put("lastName", user.getLastName());
            props.put("username", user.getUsername());
            props.put("password", password);
            response.setProps(props);

            // Lưu thông tin thanh toán vào Redis
            String passwordKey = "password:" + password;
            stringRedisTemplate.opsForValue().set(passwordKey, "password", 15, TimeUnit.MINUTES);

            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>
}
