package com.n3.mebe.controller;


import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateForAdminRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.tracking.UserForTrackingResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String createUser(@RequestBody @Valid UserCreateRequest request) {
        String msg;
        boolean check = userService.createUser(request);
        if (check){
            msg = "User updated successfully";
        }else {
            msg = "User updating failed";
        }
        return msg;
    }

    //Update user by id
    @PutMapping("/update/{user_id}")
    public String updatetUser(@PathVariable("user_id") int user_id, @RequestBody UserUpdateRequest request) {
        String msg;
        boolean check = userService.updateUserById(user_id , request);
        if (check){
            msg = "User updated successfully";
        }else {
            msg = "User updating failed";
        }
        return msg;
    }

    //Update user by id
    @PutMapping("/update_admin/uId={user_id}")
    public String updatetUserForAdmin(@PathVariable("user_id") int user_id, @RequestBody UserUpdateForAdminRequest request) {
        String msg;
        boolean check = userService.updateUserByIdForAdmin(user_id , request);
        if (check){
            msg = "User updated successfully";
        }else {
            msg = "User updating failed";
        }
        return msg;
    }

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
    public UserForTrackingResponse getUserByEmail(@PathVariable("userId") int userId) {
        return userService.getUserTrackingByIdResponse(userId);
    }


}
