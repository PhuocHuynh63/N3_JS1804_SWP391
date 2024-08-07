package com.n3.mebe.repository;

import com.n3.mebe.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByOrderOrderId(int ordId);

}
