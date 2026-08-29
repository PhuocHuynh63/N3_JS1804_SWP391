package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.review.ReviewResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.Review;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    private final ModelMapper modelMapper;

    public ReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ReviewResponse toResponse(Review review, UserResponse userResponse) {
        ReviewResponse response = modelMapper.map(review, ReviewResponse.class);
        response.setUser(userResponse);
        response.setProduct(review.getProduct());
        return response;
    }
}
