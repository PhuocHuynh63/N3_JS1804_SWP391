package com.n3.mebe.wishlist.mapper;

import com.n3.mebe.catalog.dto.response.ProductResponse;
import com.n3.mebe.wishlist.dto.response.WishListResponse;
import com.n3.mebe.wishlist.dto.response.WishListUserResponse;
import com.n3.mebe.wishlist.entity.WishList;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WishListMapper {

    private final ModelMapper modelMapper;

    public WishListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WishListResponse toResponse(WishList wishList, WishListUserResponse user, ProductResponse product) {
        WishListResponse response = modelMapper.map(wishList, WishListResponse.class);
        response.setUser(user);
        response.setProduct(product);
        return response;
    }
}
