package com.n3.mebe.user.service;

import com.n3.mebe.user.dto.request.*;
import com.n3.mebe.user.dto.response.GuestResponse;
import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.user.dto.response.UserForTrackingResponse;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.user.entity.UserRole;
import com.n3.mebe.user.entity.UserStatus;
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

    boolean updateRoleForAdmin(int id, UserRole role);

    boolean setStatusUserForAdmin(int id, UserStatus status);

    void deleteUserById(int id);

    String changePassword(int id, String oldPassword, String newPassword);

    User getUserByEmail(String email);

    UserResponse getUserByEmailResponse(String email);

    GuestResponse getGuestByEmailResponse(String email);

    UserResponse getUserByUserNameResponse(String username);

    List<UserResponse> searchUserByNameForAdmin(String username);
}
