package com.n3.mebe.repository;


import com.n3.mebe.entity.Address;
import com.n3.mebe.entity.Product;
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
    @Query(value = "DELETE FROM [address] WHERE address_id = :address_id", nativeQuery = true)
    void deleteAddressById(@Param("address_id") int addressId);

}


