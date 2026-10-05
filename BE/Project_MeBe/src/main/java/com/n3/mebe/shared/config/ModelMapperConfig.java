package com.n3.mebe.shared.config;

import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.catalog.dto.response.ProductResponse;
import com.n3.mebe.order.dto.response.OrderResponse;
import com.n3.mebe.order.dto.response.OrderUserResponse;
import com.n3.mebe.catalog.dto.response.ReviewResponse;
import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.wishlist.dto.response.WishListResponse;
import com.n3.mebe.wishlist.dto.response.WishListUserResponse;
import com.n3.mebe.order.entity.Order;
import com.n3.mebe.catalog.entity.Review;
import com.n3.mebe.user.entity.User;
import com.n3.mebe.wishlist.entity.WishList;
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

        modelMapper.typeMap(Product.class, ProductResponse.class).addMappings(m -> m.skip(ProductResponse::setSubCategory));
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
