package com.n3.mebe.catalog.repository;


import com.n3.mebe.catalog.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByUserUserId(int userId);

    List<Review> findByProductProductId(int prId);
}
