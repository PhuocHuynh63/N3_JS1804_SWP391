package com.n3.mebe.controller;


import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateForAdminRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.tracking.UserForTrackingResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

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
            responseData.setDescription("Đăng ký tài khoản thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Đăng ký thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    //Update guest to user
    @PutMapping("/signup_guest/{user_id}")
    public ResponseEntity<ResponseData> updateGuestToUser(@PathVariable("user_id") int user_id, @RequestBody @Valid UserCreateRequest request) {
        boolean check = userService.updateGuestToUser(user_id , request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Đăng ký tài khoản thành công");
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
            msg = "Cập nhập người dùng thành công";
        }else {
            msg = "Cập nhập người dùng thất bại";
        }
        return msg;
    }


    //Update user by id
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

    //Update status
    @PutMapping("/update_admin/ban_account/uId={user_id}")
    public String updateUserForAdminBanAccount(@PathVariable("user_id") int user_id, @RequestParam String status) {
        String msg = "";
        boolean check = userService.banAccountByIdForAdmin(user_id , status);
        if(status.equals("ban")){
            if (check){
                msg = "Khóa tài khoản thành công";
            }else {
                msg = "Khóa tài khoản thất bại";
            }
        }else if(status.equals("active")) {
            if (check){
                msg = "Mở tài khoản thành công";
            }else {
                msg = "Mở tài khoản thất bại";
            }
        }
        return msg;
    }

    //change for user
    @PutMapping("/change_password/{user_id}")
    public String changePasswordUser(@PathVariable("user_id") int user_id,
                                     @RequestParam String passwordOld,
                                     @RequestParam String passwordNew) {
        return userService.changePassword(user_id, passwordOld, passwordNew);
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


    @GetMapping("/check_email")
    public UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmailResponse(email);
    }

    @GetMapping("/tracking/{userId}")
    public UserForTrackingResponse getUserForTrackingResponse(@PathVariable("userId") int userId) {
        return userService.getUserTrackingByIdResponse(userId);
    }


}
