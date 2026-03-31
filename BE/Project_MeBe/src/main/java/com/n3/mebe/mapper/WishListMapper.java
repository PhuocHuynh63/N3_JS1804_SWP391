package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.dto.response.wishList.WishListResponse;
import com.n3.mebe.dto.response.wishList.WishListUserResponse;
import com.n3.mebe.entity.WishList;
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
