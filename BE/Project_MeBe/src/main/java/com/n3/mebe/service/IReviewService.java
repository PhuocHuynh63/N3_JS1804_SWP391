package com.n3.mebe.service;

import com.n3.mebe.dto.request.review.ReviewRequest;
import com.n3.mebe.dto.response.review.ReviewResponse;
import com.n3.mebe.entity.Review;

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
