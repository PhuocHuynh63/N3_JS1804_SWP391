package com.n3.mebe.wishlist.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import com.n3.mebe.wishlist.entity.WishList;
import com.n3.mebe.wishlist.entity.WishListStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishList, Integer> {

    @Override

    @EntityGraph(attributePaths = {"user", "product", "product.subCategory", "product.subCategory.category"})

    List<WishList> findAll();


    @EntityGraph(attributePaths = {"user", "product", "product.subCategory", "product.subCategory.category"})

    List<WishList> findByUserUserId(int userId);

    @Query("SELECT w FROM WishList w WHERE w.estimatedDate <= :currentDate and w.status = :status")
    List<WishList> findWishListsByEstimatedDate(@Param("currentDate") Date currentDate, @Param("status") WishListStatus status);


    @Query("SELECT w FROM WishList w WHERE w.product.productId = :productId and w.status = :status")
    List<WishList> findWishListsByProduct(@Param("productId") int productId, @Param("status") WishListStatus status);

}
