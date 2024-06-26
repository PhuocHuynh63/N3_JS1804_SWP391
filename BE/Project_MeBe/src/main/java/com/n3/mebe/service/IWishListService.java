package com.n3.mebe.service;

import com.n3.mebe.dto.request.wishList.WishListRequest;
import com.n3.mebe.dto.response.wishList.WishListResponse;

import java.util.List;

public interface IWishListService {

    List<WishListResponse> getWishListResponses();

    List<WishListResponse> getWishListResponse(int userId);

    boolean addWishList(WishListRequest request);

}
