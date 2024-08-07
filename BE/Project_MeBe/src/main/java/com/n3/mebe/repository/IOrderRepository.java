package com.n3.mebe.repository;

import com.n3.mebe.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUserId(int userId);

    List<Order> findByUserEmail(String email);


    Order findFirstByEmailOrderByCreatedAtAsc(String email);

    List<Order> findByUserPhoneNumber(String phoneNumber);

    boolean existsByVoucherVoucherCodeAndUserUserId(String code, int userId);

    boolean existsByOrderCode(String code);

    Order findByOrderCode(String code);
}
