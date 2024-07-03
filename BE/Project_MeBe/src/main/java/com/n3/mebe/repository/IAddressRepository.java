package com.n3.mebe.repository;


import com.n3.mebe.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByUserUserId(int userId);

    boolean existsAddressByAddress(String address);

    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.addressId = :address_id")
    void deleteByAddressId(@Param("address_id") int addressId);
}


