package com.n3.mebe.catalog.repository;

import org.springframework.data.jpa.repository.EntityGraph;

import com.n3.mebe.catalog.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Integer> {

    // @EntityGraph: load user + product cùng câu SELECT (JOIN) -> tránh N+1 khi map response
    @EntityGraph(attributePaths = {"user", "product"})
    List<Review> findByUserUserId(int userId);

    @EntityGraph(attributePaths = {"user", "product"})
    List<Review> findByProductProductId(int prId);
}
