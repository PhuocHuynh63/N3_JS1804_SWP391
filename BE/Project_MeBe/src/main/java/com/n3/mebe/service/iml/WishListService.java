package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.wishList.WishListRequest;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.dto.response.wishList.WishListResponse;
import com.n3.mebe.dto.response.wishList.WishListUserResponse;
import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.User;
import com.n3.mebe.entity.WishList;
import com.n3.mebe.repository.IWishListRepository;
import com.n3.mebe.service.IProductService;
import com.n3.mebe.service.IUserService;
import com.n3.mebe.service.IWishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService implements IWishListService {


    @Autowired
    private IWishListRepository wishListRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

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
    }


    @Override
    public List<WishListResponse> getWishListResponses() {

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
    }

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
    }



    @Override
    public boolean addWishList(WishListRequest request) {
        boolean check = false;

        if(request != null){
            WishList wishList = new WishList();

            User user = userService.getUserById(request.getUserId());
            wishList.setUser(user);
            Product product = productService.getProductById(request.getProductId());
            wishList.setProduct(product);
            wishList.setStatus(request.getStatus());
            wishList.setQuantity(request.getQuantity());
            wishList.setTotalAmount(request.getTotalAmount());
            wishList.setEstimatedDate(request.getEstimatedDate());
            wishList.setCreatedAt(request.getCreatedAt());
            wishList.setUpdatedAt(request.getUpdatedAt());
            wishListRepository.save(wishList);
            check = true;
        }

        return check;
    }
}
