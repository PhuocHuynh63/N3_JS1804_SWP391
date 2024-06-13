package com.n3.mebe.controller;


import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.service.iml.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  Request from Client
     *
     */

    //sign up user
    @PostMapping("/signup")
    public User createUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.createUser(request);
    }

    //Update user by id
    @PutMapping("/{user_id}")
    public User updatetUser(@PathVariable("user_id") int user_id, @RequestBody UserUpdateRequest request) {
        return userService.updateUserById(user_id , request);
    }

    //Delete user by id
    @DeleteMapping("/{user_id}")
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
    @GetMapping("/userId={user_id}")
    public UserResponse getUser(@PathVariable("user_id") int user_id) {

        return userService.getUserByIdResponse(user_id);
    }

}
