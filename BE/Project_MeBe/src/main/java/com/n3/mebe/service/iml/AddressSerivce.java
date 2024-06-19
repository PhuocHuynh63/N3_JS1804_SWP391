package com.n3.mebe.service.iml;

import com.n3.mebe.dto.request.address.CreateAddressRequest;
import com.n3.mebe.dto.request.address.UpdateAddressRequest;
import com.n3.mebe.dto.response.address.AddressResponse;
import com.n3.mebe.dto.response.address.AddressUserResponse;
import com.n3.mebe.entity.Address;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.ApiRespones;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IAddressRepository;

import com.n3.mebe.repository.IUserRepository;
import com.n3.mebe.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AddressSerivce implements IAddressService {


    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private IUserRepository userRepository;

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Address">
    @Override
    public Address createAddress(int id,CreateAddressRequest request) {

        // lấy user dựa vào id của user
        User user = userRepository.findById(id)
                .orElseThrow( () -> new AppException(ErrorCode.NO_USER_EXIST));

        Address address = new Address();

        address.setUser(user);
        address.setDefault(request.isDefault());
        address.setTitle(request.getTitle());
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setWard(request.getWard());

        return addressRepository.save(address);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Address">
    @Override
    public Address updateAddress(int addressId, UpdateAddressRequest request) {

        // Lấy ra địa chỉ dựa vào Id của địa chỉ
        Address address = addressRepository.findById(addressId)
                .orElseThrow( () -> new AppException(ErrorCode.Address_NO_EXIST));

        address.setDefault(request.isDefault());
        address.setTitle(request.getTitle());
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setWard(request.getWard());


        return addressRepository.save(address);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Address">
    @Override
    public void deleteAddress(int addressId) {
        addressRepository.deleteById(addressId) ;
    }  // </editor-fold>

    /**
     * Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get List Addresses of User">
    @Override
    public List<AddressResponse> getAddressesUser(int userId) {

        List<Address> addresses = addressRepository.findByUserUserId(userId);

        List<AddressResponse> addressResponsesList = new ArrayList<>();

        for (Address address : addresses) {
            AddressResponse addressResponse = new AddressResponse();

            //tạo đối tượng response
            AddressUserResponse addressUserResponse = new AddressUserResponse();

            //Add những thứ của user để response địa chỉ
            addressUserResponse.setFirstName(address.getUser().getFirstName());
            addressUserResponse.setLastName(address.getUser().getLastName());
            addressUserResponse.setPhoneNumber(address.getUser().getPhoneNumber());
            addressUserResponse.setAvatar(address.getUser().getAvatar());

            //add user Response ở trên vào
            addressResponse.setUser(addressUserResponse);

            //add địa chỉ của user dể response
            addressResponse.setAddressId(address.getAddressId());
            addressResponse.setDefault(address.isDefault());
            addressResponse.setTitle(address.getTitle());
            addressResponse.setAddress(address.getAddress());
            addressResponse.setCity(address.getCity());
            addressResponse.setDistrict(address.getDistrict());
            addressResponse.setWard(address.getWard());

            addressResponsesList.add(addressResponse);
        }

        return addressResponsesList;
    }// </editor-fold>

}
