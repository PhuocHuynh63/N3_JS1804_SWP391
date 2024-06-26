package com.n3.mebe.repository;


import com.n3.mebe.entity.WishList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findByUserUserId(int userId);
}
