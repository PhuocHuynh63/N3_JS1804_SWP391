package com.n3.mebe.service;

import com.n3.mebe.dto.request.wishList.WishListRequest;
import com.n3.mebe.dto.response.wishList.WishListResponse;

import java.util.List;

public interface IWishListService {

    List<WishListResponse> getWishListResponsesAll();

    List<WishListResponse> getWishListResponse(int userId);

    List<WishListResponse> getWishListResponseByWLID(int productId);

    boolean addWishList(WishListRequest request);

}
