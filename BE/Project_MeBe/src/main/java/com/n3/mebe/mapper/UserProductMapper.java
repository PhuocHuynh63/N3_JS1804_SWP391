package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.user.UserProductResponse;
import com.n3.mebe.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserProductMapper {

    private final ModelMapper modelMapper;

    public UserProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserProductResponse toUserProductResponse(Product product) {
        return modelMapper.map(product, UserProductResponse.class);
    }
}
