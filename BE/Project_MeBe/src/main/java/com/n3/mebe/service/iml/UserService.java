package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.user.*;
import com.n3.mebe.dto.response.user.*;
import com.n3.mebe.dto.response.user.tracking.UserForTrackingResponse;
import com.n3.mebe.dto.response.user.tracking.UserOrderDetailsResponse;
import com.n3.mebe.dto.response.user.tracking.UserOrderForTrackingResponse;
import com.n3.mebe.entity.*;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.*;
import com.n3.mebe.service.ICloudinaryService;
import com.n3.mebe.service.IUserService;
import com.n3.mebe.service.iml.mail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IAddressRepository iAddressRepository;

    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private IOrderDetailsRepository iOrderDetailsRepository;

    @Autowired
    private ICloudinaryService cloudinaryService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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
            throw new AppException(ErrorCode.EMAIL_NO_EXIST);
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

    // <editor-fold default state="collapsed" desc="Get List Orders For Tracking By UserID">
    private List<UserOrderForTrackingResponse> getOrdersForTrackingList(int userId) {
        List<Order> list = iOrderRepository.findByUserUserId(userId);
        List<UserOrderForTrackingResponse> orderResponseList = new ArrayList<>();

        for (Order order : list) {
            UserOrderForTrackingResponse response = new UserOrderForTrackingResponse();
            response.setOrderId(order.getOrderId());
            response.setStatus(order.getStatus());
            response.setCreatedAt(order.getCreatedAt());
            response.setItems(getOrderDetailsForTrackingList(order.getOrderId()));
            orderResponseList.add(response);
        }

        return orderResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get List OrderDetails For Tracking By OrderId">
    private List<UserOrderDetailsResponse> getOrderDetailsForTrackingList(int orderId) {
        List<OrderDetail> list = iOrderDetailsRepository.findByOrderOrderId(orderId);
        List<UserOrderDetailsResponse> orderResponseList = new ArrayList<>();

        for (OrderDetail orderDetail : list) {
            UserOrderDetailsResponse response = new UserOrderDetailsResponse();

            response.setOdId(orderDetail.getOdId());
            response.setProduct(getUserProductResponseForTrackingList(orderDetail.getProduct()));
            response.setQuantity(orderDetail.getQuantity());
            response.setPrice(orderDetail.getPrice());
            response.setSalePrice(orderDetail.getSalePrice());
            orderResponseList.add(response);
        }

        return orderResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Product Response For Tracking By OrderId">
    private UserProductResponse getUserProductResponseForTrackingList(Product product) {

        UserProductResponse response = new UserProductResponse();

        response.setProductId(product.getProductId());
        response.setSlug(product.getSlug());
        response.setName(product.getName());
        response.setImages(product.getImages());
        return response;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Check Password Redis">
    private boolean checkPasswordRedis(String password) {
        String passwordKey = "password:" + password;
        String storedOtp = stringRedisTemplate.opsForValue().get(passwordKey);

        // check xem mật khẩu trên redis còn tồn tại không và có metch với mật khẩu đã nhập
        if (storedOtp != null && storedOtp.equals(password)) {
            //nếu có thì xóa redis
            invalidatePassword(password);
            return true;
        } else {
            return false;
        }
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Invalidate Password">
    private void invalidatePassword(String password) {
        String passwordKey = "password:" + password;
        stringRedisTemplate.delete(passwordKey);
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

        String avatar = "https://i.pinimg.com/564x/ed/da/65/edda65c3e3f12f2c75500c4296d3fced.jpg";
        int point = 0;
        String status = "active";

        if (iUserRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }else if (iUserRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USERNAME_EXIST);
        }else if(iUserRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXIST);
        } else {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            user.setEmail(request.getEmail());

            user.setUsername(request.getUsername());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user.setRole(role);
            user.setBirthOfDate(request.getBirthOfDate());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAvatar(avatar);
            user.setPoint(point);
            user.setStatus(status);

            Date now = new Date();// lấy thời gian hiện tại

            user.setCreateAt(now);
            user.setUpdateAt(now);
            user.setDeleteAt(null);

            iUserRepository.save(user);
            check = true;
        }

        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Create User Google">
    @Override
    public boolean createUserGoogle(GoogleUser request) {
        boolean check = false;
        if(request != null){

            User user = iUserRepository.findByEmail(request.getEmail());
            if(user != null){
                return true;
            }else {

                user = new User();
                String role = "member";

                int point = 0;
                String status = "active";

                user.setFirstName(request.getName());
                user.setLastName(request.getFamilyName());

                user.setUsername(request.getEmail());
                user.setEmail(request.getEmail());

                user.setRole(role);

                user.setAvatar(request.getPicture());
                user.setPoint(point);
                user.setStatus(status);



                Date now = new Date();// lấy thời gian hiện tại

                user.setCreateAt(now);
                user.setUpdateAt(now);
                user.setDeleteAt(null);

                iUserRepository.save(user);
                check = true;
            }
        }
        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Create User For Admin">
    @Override
    public boolean createUserForAdmin(UserCreateForAdminRequest request) {
        boolean check = false;
        User user = new User();
        String role = "member";

        String avatar = "https://i.pinimg.com/564x/ed/da/65/edda65c3e3f12f2c75500c4296d3fced.jpg";
        int point = 0;
        String status = "active";

        if (iUserRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }else if (iUserRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USERNAME_EXIST);
        }else if(iUserRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXIST);
        } else {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            user.setEmail(request.getEmail());

            user.setUsername(request.getUsername());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            if(!request.getRole().isEmpty()){
                user.setRole(request.getRole());
            }else {
                user.setRole(role);
            }

            user.setBirthOfDate(request.getBirthOfDate());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAvatar(avatar);

            if(request.getPoint() != 0){
                user.setPoint(request.getPoint());
            }else {
                user.setPoint(point);
            }

            if(request.getStatus().isEmpty()){
                user.setStatus(request.getStatus());
            }else {
                user.setStatus(status);
            }

            Date now = new Date();// lấy thời gian hiện tại

            user.setCreateAt(now);
            user.setUpdateAt(now);
            user.setDeleteAt(null);

            iUserRepository.save(user);
            check = true;
        }

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

    // <editor-fold default state="collapsed" desc="Set Avatar">
    @Override
    public boolean setAvatar(int id, MultipartFile file) {
        boolean check = false;
        String folder = "Avatar User";

        String urlAvatar = cloudinaryService.saveFileToFolder(file , folder);
        if(urlAvatar != null){
            User user = getUserById(id);

            user.setAvatar(urlAvatar);
            iUserRepository.save(user);
            check = true;
        }

        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Guest To User">
    @Override
    public boolean updateGuestToUser(UserCreateRequest request){
        boolean check = false;
        User user = new User();
        String role = "member";

        String avatar = "https://i.pinimg.com/564x/ed/da/65/edda65c3e3f12f2c75500c4296d3fced.jpg";
        int point = 0;
        String status = "active";
        //check xem Username da ton tai chua
        if (iUserRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXIST);
        }else
            if (!iUserRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    //da check thanh cong khong co Username bi trung
                    user.setUsername(request.getUsername());
                    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                    user.setPassword(passwordEncoder.encode(request.getPassword()));

                    user.setRole(role);
                    user.setBirthOfDate(request.getBirthOfDate());
                    user.setPhoneNumber(request.getPhoneNumber());
                    user.setPoint(point);
                    user.setAvatar(avatar);
                    user.setStatus(status);

                    Date now = new Date();// lấy thời gian hiện tại

                    user.setCreateAt(now);
                    user.setUpdateAt(now);
                    user.setDeleteAt(null);
                    iUserRepository.save(user);
                    check = true;
            }else {
                throw new AppException(ErrorCode.PHONE_NUMBER_EXIST);
            }

        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update User By Id For Admin">
    @Override
    public boolean updateUserByIdForAdmin(int id, UserUpdateForAdminRequest request){
        boolean check = false;

        User user = getUserById(id);
        if(user != null){
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());

            user.setBirthOfDate(request.getBirthOfDate());
            user.setPhoneNumber(request.getPhoneNumber());

            if(!request.getPassword().equals(user.getPassword())){
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            user.setRole(request.getRole());
            user.setPoint(request.getPoint());

            Date now = new Date();
            user.setUpdateAt(now);
            iUserRepository.save(user);
            check = true;
        }

        return  check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update User Role By Id For Admin">
    @Override
    public boolean updateRoleForAdmin(int id, String role){
        boolean check = false;

        User user = getUserById(id);
        String admin = "admin";

        if(user != null && user.getRole().equalsIgnoreCase(admin)){
            user.setRole(role);

            Date now = new Date();
            user.setUpdateAt(now);
            iUserRepository.save(user);
            check = true;
        }

        return  check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Set Status User For Admin">
    @Override
    public boolean setStatusUserForAdmin(int id, String status) {
        boolean check = false;
        String admin = "admin";
        User user = getUserById(id);
        if(user != null && !user.getRole().equalsIgnoreCase(admin)){
            user.setStatus(status);
            Date now = new Date();
            user.setUpdateAt(now);
            iUserRepository.save(user);
            check = true;
        }
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

        String status = "forgot";
        String active = "active";
        // Nếu trạng thái là quên thì mới vào
        if (user.getStatus().equals(status)){
            boolean checkRedis = checkPasswordRedis(oldPassword);
            //check xem redis mật khẩu còn hạn không
            if(!checkRedis){
                throw new AppException(ErrorCode.PASSWORD_TIME_OUT);
            }else {

                //nếu còn hạn thì save mật khẩu mới và set lại status là active
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setStatus(active);
                iUserRepository.save(user);
                return "Thay đổi mật khẩu thành công";
            }
        }

        // Nếu không là forgot thì xuống đây update mật khẩu
        if (check) {
            user.setPassword(passwordEncoder.encode(newPassword));
            iUserRepository.save(user);
            msg = "Thay đổi mật khẩu thành công";
        }else{
            msg = "Mật khẩu cũ không khớp";
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
            userResponse.setStatus(user.getStatus());

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
        userResponse.setStatus(user.getStatus());

        List<UserAddressResponse> addressResponses = getUserAddresses(user.getUserId());
        userResponse.setListAddress(addressResponses);

        List<UserOrderResponse> orderResponses = getOrdersList(user.getUserId());
        userResponse.setOrder(orderResponses);

        userResponse.setCreateAt(user.getCreateAt());
        userResponse.setUpdateAt(user.getUpdateAt());
        userResponse.setDeleteAt(user.getDeleteAt());

        return userResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get User For Tracking By Id Response">
    @Override
    public UserForTrackingResponse getUserTrackingByIdResponse(int id) {

        UserForTrackingResponse userResponse = new UserForTrackingResponse();
        userResponse.setUserId(id);
        userResponse.setOrder(getOrdersForTrackingList(id));
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
        userResponse.setStatus(user.getStatus());

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
        //lấy ra user by email
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
        response.setStatus(user.getStatus());

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

    // <editor-fold default state="collapsed" desc="get Guest By Email Response">
    @Override
    public GuestResponse getGuestByEmailResponse(String email) {
        //lấy ra user by email
        Order order = iOrderRepository.findFirstByEmailOrderByCreatedAtAsc(email);

        if(order == null){
            throw new AppException(ErrorCode.EMAIL_NO_EXIST);
        }
        GuestResponse response =  new GuestResponse();

        response.setFirstName(order.getFirstName());
        response.setLastName(order.getLastName());
        response.setEmail(order.getEmail());
        response.setPhoneNumber(order.getPhoneNumber());

        return response;
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="search User By Name For Admin">
    @Override
    public List<UserResponse> searchUserByNameForAdmin(String name) {
        List<User> users = iUserRepository.findByName(name);
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
            userResponse.setStatus(user.getStatus());

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



}