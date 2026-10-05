package com.n3.mebe.wishlist.service;

import com.n3.mebe.wishlist.dto.request.WishListRequest;
import com.n3.mebe.wishlist.dto.response.WishListResponse;

import java.util.List;

public interface IWishListService {

    List<WishListResponse> getWishListResponsesAll();

    List<WishListResponse> getWishListResponse(int userId);

    List<WishListResponse> getWishListResponseByProductID(int productId);

    boolean addWishList(WishListRequest request);

}
