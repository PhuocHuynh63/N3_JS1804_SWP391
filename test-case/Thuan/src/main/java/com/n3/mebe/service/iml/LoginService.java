package com.n3.mebe.service.iml;

import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.repository.IUserRepository;
import com.n3.mebe.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class LoginService implements ILoginService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userDTOList = new ArrayList<>();

        for (User user : userList) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getUserId());
            userResponse.setAvatar(user.getAvatar());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setPassword(user.getPassword());
            userResponse.setRole(user.getRole());
            userResponse.setBirthOfDate(user.getBirthOfDate());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setPoint(user.getPoint());
            userResponse.setCreateAt(user.getCreateAt());
            userResponse.setUpdateAt(user.getUpdateAt());
            userResponse.setDeleteAt(user.getDeleteAt());
            userDTOList.add(userResponse);
        }
        return userDTOList;
    }

    @Override
    public boolean checkLogin(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        if(user != null){
            //Tham số đầu tiên là chưa được mã hoá, tham số sau đã được mã hoá
            return passwordEncoder.matches(password, user.getPassword());

        }
        return false;
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