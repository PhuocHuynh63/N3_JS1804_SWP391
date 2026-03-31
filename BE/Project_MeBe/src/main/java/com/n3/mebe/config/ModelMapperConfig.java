package com.n3.mebe.config;

import com.n3.mebe.dto.response.order.OrderResponse;
import com.n3.mebe.dto.response.order.OrderUserResponse;
import com.n3.mebe.dto.response.review.ReviewResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.wishList.WishListResponse;
import com.n3.mebe.dto.response.wishList.WishListUserResponse;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.Review;
import com.n3.mebe.entity.User;
import com.n3.mebe.entity.WishList;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        modelMapper.typeMap(Order.class, OrderResponse.class).addMappings(m -> m.skip(OrderResponse::setUser));

        modelMapper.typeMap(User.class, OrderUserResponse.class).addMappings(m -> {
            m.map(User::getUserId, OrderUserResponse::setId);
            m.skip(OrderUserResponse::setListAddress);
        });

        modelMapper.typeMap(User.class, UserResponse.class).addMappings(m -> {
            m.map(User::getUserId, UserResponse::setId);
            m.skip(UserResponse::setListAddress);
            m.skip(UserResponse::setOrder);
        });

        modelMapper.typeMap(Review.class, ReviewResponse.class).addMappings(m -> {
            m.skip(ReviewResponse::setUser);
            m.skip(ReviewResponse::setProduct);
        });

        modelMapper.typeMap(WishList.class, WishListResponse.class).addMappings(m -> {
            m.skip(WishListResponse::setUser);
            m.skip(WishListResponse::setProduct);
        });

        modelMapper.typeMap(User.class, WishListUserResponse.class).addMappings(m ->
                m.map(User::getUserId, WishListUserResponse::setId));

        return modelMapper;
    }
}
