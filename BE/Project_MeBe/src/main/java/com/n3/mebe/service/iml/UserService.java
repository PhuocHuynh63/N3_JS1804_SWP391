package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.user.UserCreateRequest;
import com.n3.mebe.dto.request.user.UserUpdateForAdminRequest;
import com.n3.mebe.dto.request.user.UserUpdateRequest;
import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.dto.response.user.UserOrderResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.UserAddressResponse;
import com.n3.mebe.dto.response.user.UserReviewResponse;
import com.n3.mebe.entity.Address;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.Review;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IAddressRepository;
import com.n3.mebe.repository.IOrderRepository;
import com.n3.mebe.repository.IReviewRepository;
import com.n3.mebe.repository.IUserRepository;
import com.n3.mebe.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IAddressRepository iAddressRepository;

    @Autowired
    private IReviewRepository iReviewRepository;

    @Autowired
    private IOrderRepository iOrderRepository;



    // <editor-fold default state="collapsed" desc="Get User By Id">
    @Override
    public User getUserById(int id){
        return iUserRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NO_USER_EXIST));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get User By Email">
    @Override
    public User getUserByEmail(String email) {
        if(iUserRepository.existsByEmail(email)){
            return iUserRepository.findByEmail(email);
        }else{
            throw new AppException(ErrorCode.NO_USER_EXIST);
        }
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get User By Username">
    public User getUserByUserName(String username){
        User user = iUserRepository.findByUsername(username);
        if(user == null){
            throw new AppException(ErrorCode.NO_USER_EXIST);
        }
        return user;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Addresses of User Response">
    public List<UserAddressResponse> getUserAddresses(int userId) {

        List<Address> addresses = iAddressRepository.findByUserUserId(userId);

        List<UserAddressResponse> addressResponsesList = new ArrayList<>();

        for (Address address : addresses) {
            UserAddressResponse response = new UserAddressResponse();

            //add địa chỉ của user dể response
            response.setAddressId(address.getAddressId());
            response.setDefault(address.isDefault());
            response.setTitle(address.getTitle());
            response.setAddress(address.getAddress());


            addressResponsesList.add(response);
        }

        return addressResponsesList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List Orders By UserID">
    private List<UserOrderResponse> getOrdersList(int userId) {
        List<Order> list = iOrderRepository.findByUserUserId(userId);
        List<UserOrderResponse> orderResponseList = new ArrayList<>();

        for (Order order : list) {
            UserOrderResponse response = new UserOrderResponse();

            response.setOrderId(order.getOrderId());
            response.setVoucher(order.getVoucher());
            response.setStatus(order.getStatus());

            response.setTotalAmount(order.getTotalAmount());

            response.setOrderType(order.getOrderType());
            response.setPaymentStatus(order.getPaymentStatus());
            response.setNote(order.getNote());
            response.setCreatedAt(order.getCreatedAt());
            response.setUpdatedAt(order.getUpdatedAt());

            orderResponseList.add(response);
        }

        return orderResponseList;
    }// </editor-fold>


    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create User">
    @Override
    public boolean createUser(UserCreateRequest request){
        boolean check = false;
        User user = new User();
        String role = "member";

        int point = 0;
        String status = "active";

        if (iUserRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.USER_EXIST);
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        user.setEmail(request.getEmail());

        user.setUsername(request.getUsername());
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

        iUserRepository.save(user);
        check = true;

        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update User By Id">
    @Override
    public boolean updateUserById(int id, UserUpdateRequest request){
        boolean check = false;
        User user = getUserById(id);

        user.setAvatar(request.getAvatar());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        user.setBirthOfDate(request.getBirthOfDate());
        user.setPhoneNumber(request.getPhoneNumber());

        Date now = new Date();
        user.setUpdateAt(now);

        iUserRepository.save(user);
        check = true;


        return check ;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update User By Id For Admin">
    @Override
    public boolean updateUserByIdForAdmin(int id, UserUpdateForAdminRequest request){
        boolean check = false;

        User user = getUserById(id);
        user.setAvatar(request.getAvatar());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        user.setBirthOfDate(request.getBirthOfDate());
        user.setPhoneNumber(request.getPhoneNumber());

        Date now = new Date();
        user.setUpdateAt(now);
        iUserRepository.save(user);
        check = true;

        return  check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete User By Id">
    @Override
    public void deleteUserById(int id){
        iUserRepository.deleteById(id);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Change Password">
    @Override
    public String changePassword(int id, String oldPassword, String newPassword) {
        User user = getUserById(id);
        String msg;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean check = passwordEncoder.matches(oldPassword, user.getPassword());

        if (check) {
            user.setPassword(passwordEncoder.encode(newPassword));
            iUserRepository.save(user);
            msg = "Change successfully";
        }else{
            msg = "Password old does not match";
        }
        return msg;
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
            userResponse.setUsername(user.getUsername());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setEmail(user.getEmail());
            userResponse.setPassword(user.getPassword());
            userResponse.setRole(user.getRole());
            userResponse.setBirthOfDate(user.getBirthOfDate());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setPoint(user.getPoint());

            List<UserAddressResponse> addressResponses = getUserAddresses(user.getUserId());
            userResponse.setListAddress(addressResponses);

            List<UserOrderResponse> orderResponses = getOrdersList(user.getUserId());
            userResponse.setOrder(orderResponses);

            userResponse.setCreateAt(user.getCreateAt());
            userResponse.setUpdateAt(user.getUpdateAt());
            userResponse.setDeleteAt(user.getDeleteAt());
            userResponses.add(userResponse);
        }
        return userResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get User By Id Response">
    @Override
    public UserResponse getUserByIdResponse(int id){

        UserResponse userResponse = new UserResponse();

        User user = getUserById(id);

        userResponse.setId(user.getUserId());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(user.getRole());
        userResponse.setBirthOfDate(user.getBirthOfDate());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setPoint(user.getPoint());

        List<UserAddressResponse> addressResponses = getUserAddresses(user.getUserId());
        userResponse.setListAddress(addressResponses);

        List<UserOrderResponse> orderResponses = getOrdersList(user.getUserId());
        userResponse.setOrder(orderResponses);

        userResponse.setCreateAt(user.getCreateAt());
        userResponse.setUpdateAt(user.getUpdateAt());
        userResponse.setDeleteAt(user.getDeleteAt());

        return userResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get User By Username Response">
    @Override
    public UserResponse getUserByUserNameResponse(String username){
        UserResponse userResponse = new UserResponse();

        User user = getUserByUserName(username);

        userResponse.setId(user.getUserId());
        userResponse.setAvatar(user.getAvatar());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setRole(user.getRole());
        userResponse.setBirthOfDate(user.getBirthOfDate());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setPoint(user.getPoint());

        List<UserAddressResponse> addressResponses = getUserAddresses(user.getUserId());
        userResponse.setListAddress(addressResponses);

        List<UserOrderResponse> orderResponses = getOrdersList(user.getUserId());
        userResponse.setOrder(orderResponses);

        userResponse.setCreateAt(user.getCreateAt());
        userResponse.setUpdateAt(user.getUpdateAt());
        userResponse.setDeleteAt(user.getDeleteAt());

        return userResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get User By Email Response">
    @Override
    public UserResponse getUserByEmailResponse(String email) {
        User user = getUserByEmail(email);
        UserResponse response =  new UserResponse();

        response.setId(user.getUserId());
        response.setAvatar(user.getAvatar());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setRole(user.getRole());
        response.setBirthOfDate(user.getBirthOfDate());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setPoint(user.getPoint());

        List<UserAddressResponse> addressResponses = getUserAddresses(user.getUserId());
        response.setListAddress(addressResponses);

        List<UserOrderResponse> orderResponses = getOrdersList(user.getUserId());
        response.setOrder(orderResponses);

        response.setCreateAt(user.getCreateAt());
        response.setUpdateAt(user.getUpdateAt());
        response.setDeleteAt(user.getDeleteAt());

        return response;
    }
    // </editor-fold>

}