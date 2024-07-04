package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.wishList.WishListRequest;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.dto.response.wishList.WishListResponse;
import com.n3.mebe.dto.response.wishList.WishListUserResponse;
import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.User;
import com.n3.mebe.entity.WishList;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IWishListRepository;
import com.n3.mebe.service.IProductService;
import com.n3.mebe.service.IUserService;
import com.n3.mebe.service.IWishListService;
import com.n3.mebe.service.iml.mail.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WishListService implements IWishListService {


    @Autowired
    private IWishListRepository wishListRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @Autowired
    private SendMailService sendMailService; ;

    // <editor-fold default state="collapsed" desc="get WishList User Responses All">
    public WishListUserResponse getWishListUser(User user) {
        WishListUserResponse response = new WishListUserResponse();

        response.setId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setBirthOfDate(user.getBirthOfDate());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setPoint(user.getPoint());

        return response;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get WishList Responses All">
    @Override
    public List<WishListResponse> getWishListResponsesAll() {

        List<WishList> list = wishListRepository.findAll();

        List<WishListResponse> wishListResponses = new ArrayList<>();
        for (WishList wishList : list) {
            WishListResponse response = new WishListResponse();

            response.setUser(getWishListUser(wishList.getUser()));

            ProductResponse productResponse = productService.getProductByIdResponse(wishList.getProduct().getProductId());
            response.setProduct(productResponse);

            response.setStatus(wishList.getStatus());
            response.setQuantity(wishList.getQuantity());
            response.setTotalAmount(wishList.getTotalAmount());
            response.setEstimatedDate(wishList.getEstimatedDate());
            response.setCreatedAt(wishList.getCreatedAt());
            response.setUpdatedAt(wishList.getUpdatedAt());
            wishListResponses.add(response);
        }
        return wishListResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get WishList Response">
    @Override
    public List<WishListResponse> getWishListResponse(int userId) {

        List<WishList> list = wishListRepository.findByUserUserId(userId);

        List<WishListResponse> wishListResponses = new ArrayList<>();
        for (WishList wishList : list) {
            WishListResponse response = new WishListResponse();

            response.setUser(getWishListUser(wishList.getUser()));

            ProductResponse productResponse = productService.getProductByIdResponse(wishList.getProduct().getProductId());
            response.setProduct(productResponse);

            response.setStatus(wishList.getStatus());
            response.setQuantity(wishList.getQuantity());
            response.setTotalAmount(wishList.getTotalAmount());
            response.setEstimatedDate(wishList.getEstimatedDate());
            response.setCreatedAt(wishList.getCreatedAt());
            response.setUpdatedAt(wishList.getUpdatedAt());
            wishListResponses.add(response);
        }
        return wishListResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="add WishList">
    @Override
    public boolean addWishList(WishListRequest request) {
        boolean check = false;
        String status = "Chờ thông báo";
        if (request != null) {
            WishList wishList = new WishList();

            User user = userService.getUserById(request.getUserId());
            wishList.setUser(user);
            Product product = productService.getProductById(request.getProductId());
            wishList.setProduct(product);
            wishList.setStatus(status);
            wishList.setQuantity(request.getQuantity());
            wishList.setTotalAmount(request.getTotalAmount());

            // Kiểm tra trạng thái của sản phẩm
            if ("Hết hàng".equalsIgnoreCase(product.getStatus())) {
                wishList.setEstimatedDate(calculateEstimatedDate()); // Tính toán thời gian dự kiến
            }else {
                throw new AppException(ErrorCode.PRODUCT_QUANTITY_NOT_OUT);
            }

            wishList.setCreatedAt(new Date());
            wishList.setUpdatedAt(new Date());
            wishListRepository.save(wishList);
            sendMailService.createSendEmailWishListConfirmation(user, wishList);
            check = true;
        }

        return check;
    }
// </editor-fold>


    private Date calculateEstimatedDate() {
        // Tính toán thời gian dự kiến dựa trên thông tin sản phẩm và các yếu tố khác
        // Ví dụ, giả sử thời gian trung bình để có hàng là 7 ngày
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
//      calendar.add(Calendar.DATE, 7);// Thêm 7 ngày
        calendar.add(Calendar.MINUTE, 5); // Thêm 5 phút
        return calendar.getTime();
    }



    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh")// Chạy hàng ngày vào lúc nửa đêm
    public void updateWishListStatus() {
        Date currentDate = new Date();
        List<WishList> wishLists = wishListRepository.findWishListsByEstimatedDate(currentDate);
        for (WishList wishList : wishLists) {
            if(wishList.getProduct().getQuantity() < wishList.getQuantity()){
                wishList.setEstimatedDate(calculateEstimatedDate());
                wishList.setUpdatedAt(new Date());
            }
            wishList.setStatus("Đã có hàng");
            //gửi mail thông báo đã có hàng
            wishList.setUpdatedAt(new Date());
            wishListRepository.save(wishList);
            sendMailService.createSendEmailWishListNotifications(wishList);
        }
    }


}
