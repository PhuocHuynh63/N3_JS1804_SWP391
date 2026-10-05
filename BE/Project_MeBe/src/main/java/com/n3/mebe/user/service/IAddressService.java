package com.n3.mebe.user.service;



import com.n3.mebe.user.dto.request.CreateAddressRequest;
import com.n3.mebe.user.dto.request.UpdateAddressRequest;
import com.n3.mebe.user.dto.response.AddressResponse;
import com.n3.mebe.user.entity.Address;

import java.util.List;

public interface IAddressService {

    List<AddressResponse> getAddressesUser(int userId);

    boolean createAddress(int userId,CreateAddressRequest request);

    boolean updateAddress(int addressId, UpdateAddressRequest address);

    boolean updateDefault(int addressId, boolean defaultValue);

    void deleteAddress(int addressId);


}

