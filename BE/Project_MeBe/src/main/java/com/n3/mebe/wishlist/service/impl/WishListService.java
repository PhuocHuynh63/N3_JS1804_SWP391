package com.n3.mebe.wishlist.service.impl;

import lombok.RequiredArgsConstructor;


import com.n3.mebe.wishlist.dto.request.WishListRequest;
import com.n3.mebe.wishlist.dto.response.WishListResponse;
import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.wishlist.entity.WishList;
import com.n3.mebe.wishlist.entity.WishListStatus;
import com.n3.mebe.catalog.entity.ProductStatus;
import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.catalog.mapper.ProductMapper;
import com.n3.mebe.user.mapper.UserMapper;
import com.n3.mebe.wishlist.mapper.WishListMapper;
import com.n3.mebe.wishlist.repository.IWishListRepository;
import com.n3.mebe.catalog.service.IProductService;
import com.n3.mebe.user.service.IUserService;
import com.n3.mebe.wishlist.service.IWishListService;
import com.n3.mebe.notification.service.impl.SendMailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService implements IWishListService {


    private final IWishListRepository wishListRepository;

    private final IUserService userService;

    private final IProductService productService;

    private final SendMailService sendMailService;

    private final WishListMapper wishListMapper;

    private final UserMapper userMapper;

    private final ProductMapper productMapper;

    private WishListResponse toWishListResponse(WishList wishList) {
        return wishListMapper.toResponse(
                wishList,
                userMapper.toWishListUserResponse(wishList.getUser()),
                productMapper.toResponse(wishList.getProduct()));
    }

    // <editor-fold default state="collapsed" desc="get WishList Responses All">
    @Override
    public List<WishListResponse> getWishListResponsesAll() {

        List<WishList> list = wishListRepository.findAll();

        List<WishListResponse> wishListResponses = new ArrayList<>();
        for (WishList wishList : list) {
            wishListResponses.add(toWishListResponse(wishList));
        }
        return wishListResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get WishList Response By UserId">
    @Override
    public List<WishListResponse> getWishListResponse(int userId) {

        List<WishList> list = wishListRepository.findByUserUserId(userId);

        List<WishListResponse> wishListResponses = new ArrayList<>();
        for (WishList wishList : list) {
            wishListResponses.add(toWishListResponse(wishList));
        }
        return wishListResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get WishList Response By productId and status Chờ thông báo">
    @Override
    public List<WishListResponse> getWishListResponseByProductID(int productId) {
        List<WishList> list = wishListRepository.findWishListsByProduct(productId, WishListStatus.WAITING);

        List<WishListResponse> wishListResponses = new ArrayList<>();
        for (WishList wishList : list) {
            wishListResponses.add(toWishListResponse(wishList));
        }
        return wishListResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="add WishList">
    @Override
    public boolean addWishList(WishListRequest request) {
        boolean check = false;
        if (request != null) {
            WishList wishList = new WishList();

            User user = userService.getUserById(request.getUserId());
            wishList.setUser(user);
            Product product = productService.getProductById(request.getProductId());
            wishList.setProduct(product);
            wishList.setStatus(WishListStatus.WAITING);
            wishList.setQuantity(request.getQuantity());
            wishList.setTotalAmount(request.getTotalAmount());

            // Kiểm tra trạng thái của sản phẩm
            if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
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
        calendar.add(Calendar.DATE, 1); // Thêm 5 phút
        return calendar.getTime();
    }


    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Ho_Chi_Minh")// Chạy hàng ngày vào lúc nửa đêm
    public void updateWishListStatus() {
        Date currentDate = new Date();
        List<WishList> wishLists = wishListRepository.findWishListsByEstimatedDate(currentDate, WishListStatus.WAITING);
        for (WishList wishList : wishLists) {
            if(wishList.getProduct().getQuantity() < wishList.getQuantity()){
                wishList.setEstimatedDate(calculateEstimatedDate());
                wishList.setUpdatedAt(new Date());
            }
            wishList.setStatus(WishListStatus.AVAILABLE);
            //gửi mail thông báo đã có hàng
            wishList.setUpdatedAt(new Date());
            wishListRepository.save(wishList);
            sendMailService.createSendEmailWishListNotifications(wishList);
        }
    }



}
