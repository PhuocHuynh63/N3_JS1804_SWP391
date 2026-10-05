package com.n3.mebe.payment.repository;

import com.n3.mebe.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findByOrderOrderId(int ordId);

}
