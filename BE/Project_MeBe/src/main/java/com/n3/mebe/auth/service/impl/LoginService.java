package com.n3.mebe.auth.service.impl;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.user.entity.UserStatus;
import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.user.mapper.UserMapper;
import com.n3.mebe.user.repository.IUserRepository;
import com.n3.mebe.auth.service.ILoginService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor
public class LoginService implements ILoginService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toShallowUserResponse).toList();
    }

    @Override
    public boolean checkLogin(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        if (user.getStatus() == UserStatus.BANNED) {
            throw new AppException(ErrorCode.BAN_ACCOUNT);
        }else {
            //Tham số đầu tiên là chưa được mã hoá, tham số sau đã được mã hoá
            return  passwordEncoder.matches(password, user.getPassword());
        }
    }

    @Override
    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getRole() != null ? user.getRole().getLabel() : null;
        }
        return null;
    }

//
//    @Override
//    public boolean checkLogin(String userName, String password) {
//        if (userName == null || password == null) {
//            return false;
//        }else if (userName.equals("user1") && password.equals("123")){
//            return true;
//        }else{
//            return false;
//        }
//    }

}