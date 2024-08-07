package com.n3.mebe.service.iml.mail;

import com.n3.mebe.dto.response.gmail.GmailSendResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.User;
import com.n3.mebe.entity.WishList;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IOrderDetailsRepository;
import com.n3.mebe.repository.IOrderRepository;
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
    private IUserRepository iUserRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private IUserRepository IUserRepository;

    @Autowired
    private IOrderDetailsRepository IOrderDetailsRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IOrderRepository orderRepository;

    // <editor-fold default state="collapsed" desc="create Send Email Forgot">
    @Override
    public boolean createSendEmailForgot(String email) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            User user = userService.getUserByEmail(email);
            response.setTo(user.getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.CLIENT_REGISTER);

            String password = DataUtils.generateTempPwd(6);
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);

            String status = "forgot";
            user.setStatus(status);
            IUserRepository.save(user);

            Map<String, Object> props = new HashMap<>();
            props.put("firstName", user.getFirstName());
            props.put("lastName", user.getLastName());
            props.put("username", user.getUsername());
            props.put("password", password);
            response.setProps(props);

            // Lưu thông tin thanh toán vào Redis
            String passwordKey = "password:" + password;
            stringRedisTemplate.opsForValue().set(passwordKey, password, 15, TimeUnit.MINUTES);


            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="create Send Email Verify Order">
    @Override
    public boolean createSendEmailVerifyOrder(Order order) {
        try {
            GmailSendResponse response = new GmailSendResponse();


            response.setTo(order.getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.VERIFY_ORDER);


            Map<String, Object> props = new HashMap<>();
            props.put("firstName", order.getFirstName());
            props.put("lastName", order.getLastName());
            props.put("orderCode", order.getOrderCode());
            props.put("shipAddress", order.getShipAddress());

            props.put("orderDetails", IOrderDetailsRepository.findByOrderOrderId(order.getOrderId()));
            props.put("totalAmount",  order.getTotalAmount());
            response.setProps(props);

            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.VERIFY_ORDER);
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="create Send Email WishLish Confirmation">
    @Override
    public boolean createSendEmailWishListConfirmation(User user, WishList wishList) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            response.setTo(user.getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.COMFIRMED_WISHLIST);

            Map<String, Object> props = new HashMap<>();
            props.put("firstName", user.getFirstName());
            props.put("lastName", user.getLastName());
            props.put("email", user.getEmail());
            props.put("status", wishList.getStatus());


            props.put("wishListId", wishList.getWishlistId());
            props.put("estimatedDate", wishList.getEstimatedDate());

            props.put("name",  wishList.getProduct().getName());
            props.put("img" , wishList.getProduct().getImages());

            props.put("quantity", wishList.getQuantity());

            props.put("totalAmount", wishList.getTotalAmount());
            response.setProps(props);

            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.COMFIRMED_WISHLIST);
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="create Send Email WishLish Notifications">
    @Override
    public boolean createSendEmailWishListNotifications(WishList wishList) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            response.setTo(wishList.getUser().getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.NOTIFICATION_WISHLIST);

            Map<String, Object> props = new HashMap<>();
            props.put("firstName", wishList.getUser().getFirstName());
            props.put("lastName", wishList.getUser().getLastName());
            props.put("email", wishList.getUser().getEmail());
            props.put("status", wishList.getStatus());

            props.put("wishListId", wishList.getWishlistId());

            props.put("name",  wishList.getProduct().getName());
            props.put("img" , wishList.getProduct().getImages());
            props.put("price", wishList.getProduct().getPrice());
            props.put("quantity", wishList.getQuantity());

            props.put("totalAmount", wishList.getTotalAmount());
            response.setProps(props);

            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.NOTIFICATION_WISHLIST);
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="send Otp Check Email Exist">
    @Override
    public boolean sendOtpCheckEmailExist(String email) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            // ở đây là guest
            Order order = orderRepository.findFirstByEmailOrderByCreatedAtAsc(email);

            if(iUserRepository.existsByEmail(email)){
                throw new AppException(ErrorCode.EMAIL_HAVE_ACCOUNT);
            }

            // Tạo mật khẩu tạm thời (OTP) gồm 6 ký tự
            String OTP = DataUtils.generateTempPwd(6);

            // Chuẩn bị dòng tiêu đề email OTP
            String OTP_SEND = OTP + " " + ConstEmail.SEND_MAIL_SUBJECT.OTP_SEND;

            // Tạo khóa Redis để lưu trữ OTP
            String OTPKey = "OTP:" + OTP;

            response.setTo(order.getEmail());
            response.setSubject(OTP_SEND);

            Map<String, Object> props = new HashMap<>();
            props.put("OTP", OTP);
            response.setProps(props);

            // Lưu OTP vào Redis với thời gian tồn tại (TTL) là 1 phút
            stringRedisTemplate.opsForValue().set(OTPKey, OTP, 1, TimeUnit.MINUTES);

            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.OTP_SEND);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>


    // Method to check OTP
    @Override
    public boolean checkOtp(String otp) {
        String OTPKey = "OTP:" + otp;
        String storedOtp = stringRedisTemplate.opsForValue().get(OTPKey);

        if (storedOtp != null && storedOtp.equals(otp)) {
            invalidateOtp(otp);
            return true;
        } else {
            return false;
        }
    }

    // Optional: Method to invalidate OTP
    @Override
    public void invalidateOtp(String identifier) {
        String otpKey = "OTP:" + identifier;
        stringRedisTemplate.delete(otpKey);
    }


    // <editor-fold default state="collapsed" desc="send Mail Create Success">
    @Override
    public boolean sendMailCreateSuccess(String email) {
        try {
            GmailSendResponse response = new GmailSendResponse();

            // lấy user đăng ký thành công ra
            User user = userService.getUserByEmail(email);

            if(user == null){
                throw new AppException(ErrorCode.NO_USER_EXIST);
            }
            response.setTo(user.getEmail());
            response.setSubject(ConstEmail.SEND_MAIL_SUBJECT.CREATE_USER_SUCCESS);

            Map<String, Object> props = new HashMap<>();
            props.put("firstName", user.getFirstName());
            props.put("lastName", user.getLastName());
            response.setProps(props);


            mailService.sendHtmlMail(response, ConstEmail.TEMPLATE_FILE_NAME.CREATE_USER_SUCCESS);
            return true;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        return false;
    }// </editor-fold>


}
