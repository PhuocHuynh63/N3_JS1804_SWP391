package com.n3.mebe.repository;


import com.n3.mebe.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {

    List<Address> findByUserUserId(int userId);


}


