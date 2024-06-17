package com.n3.mebe.service;

import com.n3.mebe.dto.response.user.UserResponse;

import java.util.List;

public interface ILoginService {

    List<UserResponse> getAllUser();
    boolean checkLogin(String username, String password);
}
