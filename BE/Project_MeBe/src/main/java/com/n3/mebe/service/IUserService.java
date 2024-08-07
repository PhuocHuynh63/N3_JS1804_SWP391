package com.n3.mebe.service;

import com.n3.mebe.dto.request.user.*;
import com.n3.mebe.dto.response.user.GuestResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.tracking.UserForTrackingResponse;
import com.n3.mebe.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    boolean createUser(UserCreateRequest request);

    boolean createUserGoogle(GoogleUser request);

    boolean createUserForAdmin(UserCreateForAdminRequest request);

    List<UserResponse> getAllUser();

    User getUserById(int id);

    UserResponse getUserByIdResponse(int id);

    UserForTrackingResponse getUserTrackingByIdResponse(int id);

    boolean updateUserById(int id, UserUpdateRequest request);

    boolean setAvatar(int id, MultipartFile file);

    boolean updateGuestToUser(UserCreateRequest request);

    boolean updateUserByIdForAdmin(int id, UserUpdateForAdminRequest request);

    boolean updateRoleForAdmin(int id, String role);

    boolean setStatusUserForAdmin(int id, String status);

    void deleteUserById(int id);

    String changePassword(int id, String oldPassword, String newPassword);

    User getUserByEmail(String email);

    UserResponse getUserByEmailResponse(String email);

    GuestResponse getGuestByEmailResponse(String email);

    UserResponse getUserByUserNameResponse(String username);

    List<UserResponse> searchUserByNameForAdmin(String username);
}
