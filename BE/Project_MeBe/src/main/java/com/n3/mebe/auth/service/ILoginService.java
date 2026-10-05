package com.n3.mebe.auth.service;

import com.n3.mebe.user.dto.response.UserResponse;

import java.util.List;

public interface ILoginService {

    List<UserResponse> getAllUser();
    boolean checkLogin(String username, String password);
    String getUserRole(String username);
}