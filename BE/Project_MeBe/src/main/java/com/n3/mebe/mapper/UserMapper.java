package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.order.OrderUserResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.wishList.WishListUserResponse;
import com.n3.mebe.entity.User;
import com.n3.mebe.repository.IAddressRepository;
import com.n3.mebe.repository.IOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final IAddressRepository addressRepository;
    private final IOrderRepository orderRepository;
    private final AddressMapper addressMapper;
    private final UserOrderMapper userOrderMapper;

    public UserMapper(ModelMapper modelMapper,
                      IAddressRepository addressRepository,
                      IOrderRepository orderRepository,
                      AddressMapper addressMapper,
                      UserOrderMapper userOrderMapper) {
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.addressMapper = addressMapper;
        this.userOrderMapper = userOrderMapper;
    }

    public OrderUserResponse toOrderUserResponse(User user) {
        if (user == null) {
            return null;
        }
        OrderUserResponse response = modelMapper.map(user, OrderUserResponse.class);
        response.setListAddress(addressMapper.toUserAddressResponseList(
                addressRepository.findByUserUserId(user.getUserId())));
        return response;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse response = modelMapper.map(user, UserResponse.class);
        response.setListAddress(addressMapper.toUserAddressResponseList(
                addressRepository.findByUserUserId(user.getUserId())));
        response.setOrder(userOrderMapper.toUserOrderResponseList(
                orderRepository.findByUserUserId(user.getUserId())));
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
