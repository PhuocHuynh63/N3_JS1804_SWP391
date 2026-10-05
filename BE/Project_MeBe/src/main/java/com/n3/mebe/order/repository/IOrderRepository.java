package com.n3.mebe.order.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import com.n3.mebe.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    // Danh sách đơn cho admin: load luôn user + voucher (1 query JOIN) thay vì 1 query/đơn
    @Override
    @EntityGraph(attributePaths = {"user", "voucher"})
    List<Order> findAll();

    // Tracking: JOIN FETCH chi tiết đơn + sản phẩm trong 1 câu. DISTINCT vì JOIN collection nhân bản dòng Order
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderDetails od LEFT JOIN FETCH od.product WHERE o.user.userId = :userId")
    List<Order> findWithDetailsByUserId(@Param("userId") int userId);

    List<Order> findByUserUserId(int userId);

    List<Order> findByUserEmail(String email);


    Order findFirstByEmailOrderByCreatedAtAsc(String email);

    List<Order> findByUserPhoneNumber(String phoneNumber);

    boolean existsByVoucherVoucherCodeAndUserUserId(String code, int userId);

    boolean existsByOrderCode(String code);

    Order findByOrderCode(String code);
}
