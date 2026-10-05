package com.n3.mebe.catalog.mapper;

import com.n3.mebe.catalog.dto.response.ReviewResponse;
import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.catalog.entity.Review;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    private final ModelMapper modelMapper;


    private final ProductMapper productMapper;


    public ReviewMapper(ModelMapper modelMapper, ProductMapper productMapper) {

        this.modelMapper = modelMapper;

        this.productMapper = productMapper;

    }

    public ReviewResponse toResponse(Review review, UserResponse userResponse) {
        ReviewResponse response = modelMapper.map(review, ReviewResponse.class);
        response.setUser(userResponse);
        response.setProduct(productMapper.toSummary(review.getProduct()));
        return response;
    }
}
