package com.n3.mebe.catalog.service;

import com.n3.mebe.catalog.dto.request.ReviewRequest;
import com.n3.mebe.catalog.dto.response.ReviewResponse;
import com.n3.mebe.catalog.entity.Review;

import java.util.List;

public interface IReviewService {


    Review getReview(int id);

    ReviewResponse getReviewResponse(int id);

    List<ReviewResponse> getReviewResponseByUserId(int userId);

    List<ReviewResponse> getReviewResponseByProductId(int prId);

    Review addReview(ReviewRequest review);

    Review updateReview(int id, ReviewRequest review);

    void deleteReview(int id);
}
