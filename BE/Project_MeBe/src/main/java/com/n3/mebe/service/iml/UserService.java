package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IUserRepository;
import com.n3.mebe.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create User">
    @Override
    public User createUser(UserCreateRequest request){
        User user = new User();
        String role = "member";
        int point = 0;
        String status = "active";

        if (iUserRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.USER_EXIST);
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(role);
        user.setBirthOfDate(request.getBirthOfDate());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPoint(point);
        user.setStatus(status);

        Date now = new Date();// lấy thời gian hiện tại

        user.setCreateAt(now);
        user.setUpdateAt(now);
        user.setDeleteAt(null);

        return iUserRepository.save(user);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update User By Id">
    @Override
    public User updateUserById(int id, UserUpdateRequest request){

        User user = getUserById(id);

        user.setAvatar(request.getAvatar());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setBirthOfDate(request.getBirthOfDate());
        user.setPhoneNumber(request.getPhoneNumber());

        Date now = new Date();
        user.setUpdateAt(now);


        return  iUserRepository.save(user);
    }// </editor-fold>


    public String checkPassword(String password, String confirmPassword){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        passwordEncoder.matches(password, confirmPassword);

        return passwordEncoder.encode(password);
    }




    // <editor-fold default state="collapsed" desc="Delete User By Id">
    @Override
    public void deleteUserById(int id){
        iUserRepository.deleteById(id);
    }// </editor-fold>

    /**
     *  Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get All User">
    @Override
    public List<UserResponse> getAllUser(){
        List<User> users = iUserRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users){

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
            userResponses.add(userResponse);
        }
        return userResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get User By Id">
    @Override
    public User getUserById(int id){
        return iUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_USER_EXIST));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get User By Id Response">
    @Override
    public UserResponse getUserByIdResponse(int id){

        UserResponse userResponse = new UserResponse();

        User user = getUserById(id);

        userResponse.setId(userResponse.getId());
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

        return userResponse;
    }// </editor-fold>

}