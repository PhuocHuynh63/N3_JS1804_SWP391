package com.n3.mebe.repository;

import com.n3.mebe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUserId(int userId);

    List<Order> findByUserEmailOrUserPhoneNumber(String email, String phoneNumber);

    boolean existsByVoucherVoucherCodeAndUserUserId(String code, int userId);

}
