package com.n3.mebe.controller;


import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateForAdminRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.tracking.UserForTrackingResponse;

import com.n3.mebe.service.ISendMailService;
import com.n3.mebe.service.IUserService;
import com.n3.mebe.service.iml.mail.SendMailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISendMailService sendMailService;

    /**
     *  Request from Client
     *
     */

    //sign up user
    @PostMapping("/signup")
    public ResponseEntity<ResponseData> createUser(@RequestBody @Valid UserCreateRequest request) {
        boolean check = userService.createUser(request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Đăng ký thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Đăng ký thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PostMapping("/send_otp_mail")
    public ResponseEntity<ResponseData> sendOtpEmailExist(@RequestParam String email) {
        boolean check = sendMailService.sendOtpCheckEmailExist(email);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Gửi mã xác minh thành công!");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Email không tồn tại trong hệ thống!");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    // API to check OTP
    @PostMapping("/check_otp")
    public ResponseEntity<String> checkOtp(@RequestParam String identifier, @RequestParam String otp) {
        boolean isValid = sendMailService.checkOtp(identifier, otp);
        if (isValid) {
            sendMailService.invalidateOtp(identifier);
            return ResponseEntity.ok("Xác minh thành công");
        } else {
            return ResponseEntity.status(400).body("Mã xác minh không đúng!");
        }
    }

    //Update guest to user
    @PutMapping("/signup_guest/{user_id}")
    public ResponseEntity<ResponseData> updateGuestToUser(@PathVariable("user_id") int user_id, @RequestBody @Valid UserCreateRequest request) {
        boolean check = userService.updateGuestToUser(user_id , request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Đăng ký thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Đăng ký thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }


    //Update user by id
    @PutMapping("/update/{user_id}")
    public String updateUserById(@PathVariable("user_id") int user_id, @RequestBody UserUpdateRequest request) {
        String msg;
        boolean check = userService.updateUserById(user_id , request);
        if (check){
            msg = "Cập nhập thành công";
        }else {
            msg = "Cập nhập thất bại";
        }
        return msg;
    }

    //Update user by id
    @PutMapping(value = "/update_avatar/{user_id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String setAvatar(@PathVariable("user_id") int user_id, @RequestPart("file") MultipartFile file) {
        String msg;
        boolean check = userService.setAvatar(user_id , file);
        if (check){
            msg = "Cập nhập ảnh đại diện thành công";
        }else {
            msg = "Cập nhập ảnh đại diện thất bại";
        }
        return msg;
    }


    //Update user by id for admin
    @PutMapping("/update_admin/uId={user_id}")
    public String updateUserForAdmin(@PathVariable("user_id") int user_id, @RequestBody UserUpdateForAdminRequest request) {
        String msg;
        boolean check = userService.updateUserByIdForAdmin(user_id , request);
        if (check){
            msg = "Cập nhập người dùng thành công";
        }else {
            msg = "Cập nhập người dùng thất bại";
        }
        return msg;
    }

    @PutMapping("/update_role/uId={user_id}")
    public String updateRoleForAdmin(@PathVariable("user_id") int user_id, @RequestParam String role) {
        String msg;
        boolean check = userService.updateRoleForAdmin(user_id , role);
        if (check){
            msg = "Cập nhập role thành công";
        }else {
            msg = "Cập nhập role thất bại";
        }
        return msg;
    }

    @PutMapping("/change_password/uId={user_id}")
    public String changePasswordUser(@PathVariable("user_id") int user_id,
                                     @RequestParam String passwordOld,
                                     @RequestParam String passwordNew) {
        return userService.changePassword(user_id, passwordOld, passwordNew);
    }

    @PutMapping("/set_status/uId={user_id}")
    public String setStatusForAdmin(@PathVariable("user_id") int user_id, @RequestParam String status) {
        String msg= "";

        boolean check = userService.setStatusUserForAdmin(user_id , status);

        switch (status) {
            case "ban":
                if (check){
                    msg = "Khóa tài khoản thành công";
                }else {
                    msg = "Khóa tài khoản thất bại";
                }
                break;
            case "active":
                if (check){
                    msg = "Mở khóa tài khoản thành công";
                }else {
                    msg = "Mở khóa tài khoản thất bại";
                }
                break;
        }
        return msg;
    }

    //Delete user by id
    @DeleteMapping("/delete/{user_id}")
    public String deleteUser(@PathVariable("user_id") int user_id) {
        userService.deleteUserById(user_id);
        return "User have been deleted";
    }

    /**
     *  Response to Client
     *
     */

    //Response list user
    @GetMapping("/list")
    public List<UserResponse> getUserList() {
        return userService.getAllUser();
    }

    //Response a user by id
    @GetMapping("/{user_id}")
    public UserResponse getUser(@PathVariable("user_id") int user_id) {

        return userService.getUserByIdResponse(user_id);
    }

    @GetMapping("/username={user_name}")
    public UserResponse getUserByUserName(@PathVariable("user_name") String user_name) {

        return userService.getUserByUserNameResponse(user_name);
    }

    // check email lấy ra user bằng email
    //nếu email không có trên hệ thống thông báo email không tồn tại
    @GetMapping("/check_email")
    public UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmailResponse(email);
    }

    @GetMapping("/tracking/{userId}")
    public UserForTrackingResponse getUserForTrackingResponse(@PathVariable("userId") int userId) {
        return userService.getUserTrackingByIdResponse(userId);
    }


    @GetMapping("/search_name")
    public List<UserResponse> searchByNameForAdmin(@RequestParam String name) {
        return userService.searchUserByNameForAdmin(name);
    }

}
