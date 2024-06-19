package com.n3.mebe.service;

import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.User;

import java.util.List;

public interface IUserService {

    User createUser(UserCreateRequest request);
    List<UserResponse> getAllUser();
    User getUserById(int id);
    UserResponse getUserByIdResponse(int id);
    User updateUserById(int id, UserUpdateRequest request);
    void deleteUserById(int id);

}
