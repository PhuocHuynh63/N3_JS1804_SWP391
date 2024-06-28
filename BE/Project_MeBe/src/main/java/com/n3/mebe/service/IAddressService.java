package com.n3.mebe.service;



import com.n3.mebe.dto.request.address.CreateAddressRequest;
import com.n3.mebe.dto.request.address.UpdateAddressRequest;
import com.n3.mebe.dto.response.address.AddressResponse;
import com.n3.mebe.entity.Address;

import java.util.List;

public interface IAddressService {

    List<AddressResponse> getAddressesUser(int userId);

    boolean createAddress(int userId,CreateAddressRequest request);

    boolean updateAddress(int addressId, UpdateAddressRequest address);

    boolean updateDefault(int addressId, boolean defaultValue);

    void deleteAddress(int addressId);


}

