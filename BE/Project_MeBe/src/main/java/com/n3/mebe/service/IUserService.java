package com.n3.mebe.service;

import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateForAdminRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.tracking.UserForTrackingResponse;
import com.n3.mebe.entity.User;

import java.util.List;

public interface IUserService {

    boolean createUser(UserCreateRequest request);
    List<UserResponse> getAllUser();

    User getUserById(int id);

    UserResponse getUserByIdResponse(int id);

    UserForTrackingResponse getUserTrackingByIdResponse(int id);

    boolean updateUserById(int id, UserUpdateRequest request);

    boolean updateGuestToUser(int id, UserCreateRequest request);

    boolean updateUserByIdForAdmin(int id, UserUpdateForAdminRequest request);

    boolean banAccountByIdForAdmin(int id, String request);

    void deleteUserById(int id);

    String changePassword(int id, String oldPassword, String newPassword);

    User getUserByEmail(String email);

    UserResponse getUserByEmailResponse(String email);

    UserResponse getUserByUserNameResponse(String username);
}
