package com.n3.mebe.user.service.impl;

import com.n3.mebe.user.dto.request.CreateAddressRequest;
import com.n3.mebe.user.dto.request.UpdateAddressRequest;
import com.n3.mebe.user.dto.response.AddressResponse;
import com.n3.mebe.user.entity.Address;
import com.n3.mebe.user.entity.User;

import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.user.mapper.AddressMapper;
import com.n3.mebe.user.repository.IAddressRepository;

import com.n3.mebe.user.repository.IUserRepository;
import com.n3.mebe.user.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressService implements IAddressService {


    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Address">
    @Override
    public boolean createAddress(int id,CreateAddressRequest request) {

        // lấy user dựa vào id của user
        User user = userRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.NO_USER_EXIST));
        List<Address> addresses = user.getListAddress();
        for (Address address : addresses) {
            if (address.getAddress().equals(request.getAddress())) {
                throw new AppException(ErrorCode.Address_EXIST);
            }
        }


        Address address = new Address();

        address.setUser(user);
        address.setDefault(request.isDefault());
        address.setTitle(request.getTitle());
        address.setAddress(request.getAddress());
        addressRepository.save(address);

        return true;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Address">
    @Override
    public boolean updateAddress(int addressId, UpdateAddressRequest request) {
        boolean result = false;
        // Lấy ra địa chỉ dựa vào Id của địa chỉ
        Address address = addressRepository.findById(addressId)
                .orElseThrow( () -> new AppException(ErrorCode.Address_NO_EXIST));


        List<Address> addresses = address.getUser().getListAddress();
        for (Address address1 : addresses) {
            if (address.getAddress().equals(request.getAddress())) {
                throw new AppException(ErrorCode.Address_EXIST);
            }
        }
        if (!request.getAddress().equals(address.getAddress())){
            address.setTitle(request.getTitle());
            address.setAddress(request.getAddress());
            addressRepository.save(address);
            result = true;
        }
        return result;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Default">
    @Override
    public boolean updateDefault(int addressId, boolean defaultValue) {
        boolean result = false;
        // Lấy ra địa chỉ dựa vào Id của địa chỉ
        Address address = addressRepository.findById(addressId)
                .orElseThrow( () -> new AppException(ErrorCode.Address_NO_EXIST));
        if (address.isDefault() != defaultValue){
            address.setDefault(defaultValue);
            addressRepository.save(address);
            result = true;
        }
        return result;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Address">
    @Override
    public void deleteAddress(int addressId) {
        addressRepository.deleteAddressById(addressId);
    }  // </editor-fold>

    /**
     * Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get List Addresses of User">
    @Override
    public List<AddressResponse> getAddressesUser(int userId) {
        return addressMapper.toAddressResponseList(addressRepository.findByUserUserId(userId));
    }// </editor-fold>

}
