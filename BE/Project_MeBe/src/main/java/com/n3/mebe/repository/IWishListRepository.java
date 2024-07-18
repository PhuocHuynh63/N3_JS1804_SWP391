package com.n3.mebe.repository;


import com.n3.mebe.entity.WishList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findByUserUserId(int userId);

    @Query("SELECT w FROM WishList w WHERE w.estimatedDate <= :currentDate and w.status ='Chờ thông báo'")
    List<WishList> findWishListsByEstimatedDate(@Param("currentDate") Date currentDate);


    @Query("SELECT w FROM WishList w WHERE w.product.productId = :productId and w.status = :status")
    List<WishList> findWishListsByProduct(@Param("productId") int productId, @Param("status") String status);

}
