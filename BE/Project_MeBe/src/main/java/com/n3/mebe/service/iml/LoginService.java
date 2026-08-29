package com.n3.mebe.service.iml;

import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.mapper.UserMapper;
import com.n3.mebe.repository.IUserRepository;
import com.n3.mebe.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LoginService implements ILoginService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toShallowUserResponse).toList();
    }

    @Override
    public boolean checkLogin(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        String ban = "ban";
        if(user.getStatus().equalsIgnoreCase(ban)){
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
            return user.getRole(); // Giả sử User có phương thức getRole để lấy vai trò của người dùng
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