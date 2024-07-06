package com.n3.mebe.repository;


import com.n3.mebe.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoucherRepository extends JpaRepository<Voucher, Integer> {


    Voucher findByVoucherCode(String code);

    boolean existsByVoucherCode(String code);

    @Query("SELECT v FROM Voucher v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Voucher> searchByName(@Param("name")String name);


}
