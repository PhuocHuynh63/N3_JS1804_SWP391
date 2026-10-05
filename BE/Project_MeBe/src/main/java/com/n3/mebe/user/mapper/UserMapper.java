package com.n3.mebe.user.mapper;

import com.n3.mebe.order.dto.response.OrderUserResponse;
import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.wishlist.dto.response.WishListUserResponse;
import com.n3.mebe.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
// Collection (listAddress, orders) là LAZY; nhờ hibernate.default_batch_fetch_size,
// map nhiều user chỉ tốn vài câu IN (...) thay vì 2 query cho mỗi user.
public class UserMapper {

    private final ModelMapper modelMapper;
    private final AddressMapper addressMapper;
    private final UserOrderMapper userOrderMapper;

    public UserMapper(ModelMapper modelMapper,
                      AddressMapper addressMapper,
                      UserOrderMapper userOrderMapper) {
        this.modelMapper = modelMapper;
        this.addressMapper = addressMapper;
        this.userOrderMapper = userOrderMapper;
    }

    public OrderUserResponse toOrderUserResponse(User user) {
        if (user == null) {
            return null;
        }
        OrderUserResponse response = modelMapper.map(user, OrderUserResponse.class);
        response.setListAddress(addressMapper.toUserAddressResponseList(user.getListAddress()));
        return response;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse response = modelMapper.map(user, UserResponse.class);
        response.setListAddress(addressMapper.toUserAddressResponseList(user.getListAddress()));
        response.setOrder(userOrderMapper.toUserOrderResponseList(user.getOrders()));
        return response;
    }

    public WishListUserResponse toWishListUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, WishListUserResponse.class);
    }

    /** Scalar fields only; {@code listAddress} and {@code order} stay null. */
    public UserResponse toShallowUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
