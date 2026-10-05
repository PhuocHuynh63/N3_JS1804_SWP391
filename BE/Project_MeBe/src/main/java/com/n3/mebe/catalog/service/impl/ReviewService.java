package com.n3.mebe.catalog.service.impl;

import com.n3.mebe.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import com.n3.mebe.user.service.impl.UserService;

import com.n3.mebe.catalog.dto.request.ReviewRequest;
import com.n3.mebe.catalog.dto.response.ReviewResponse;
import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.catalog.entity.Review;
import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.catalog.mapper.ReviewMapper;
import com.n3.mebe.catalog.repository.IReviewRepository;
import com.n3.mebe.catalog.service.IReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final IReviewRepository reviewRepository;

    private final UserService userService;

    private final ProductService productService;

    private final ReviewMapper reviewMapper;

    private final UserMapper userMapper;


    @Override
    public Review getReview(int id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
    }

    /**
     *  Request from Client
     *
     */


    // <editor-fold default state="collapsed" desc="Create Review">
    @Override
    public Review addReview(ReviewRequest request) {
        Review review = new Review();

        review.setUser(userService.getUserById(request.getUserId()));
        review.setProduct(productService.getProductById(request.getProductId()));
        review.setRate(request.getRate());
        review.setComment(request.getComment());

        Date now =new Date();
        review.setCreateAt(now);
        review.setUpdateAt(now);

        return reviewRepository.save(review);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Review">
    @Override
    public Review updateReview(int id, ReviewRequest request) {
        Review review = getReview(id);

        review.setRate(request.getRate());
        review.setComment(request.getComment());

        Date now =new Date();

        review.setUpdateAt(now);


        return reviewRepository.save(review);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Review">
    @Override
    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }// </editor-fold>


    /**
     *  Response from Client
     *
     */


    // <editor-fold default state="collapsed" desc="Get Review By ID">
    @Override
    public ReviewResponse getReviewResponse(int id) {
        Review review = getReview(id);
        UserResponse userResponse = userMapper.toUserResponse(review.getUser());
        return reviewMapper.toResponse(review, userResponse);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Review By UserID">
    @Override
    public List<ReviewResponse> getReviewResponseByUserId(int userId) {
        List<Review> list = reviewRepository.findByUserUserId(userId);
        // Cùng một user cho mọi review -> map 1 lần thay vì query lại trong vòng lặp
        UserResponse response = list.isEmpty() ? null : userMapper.toUserResponse(list.get(0).getUser());

        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : list) {
            reviewResponseList.add(reviewMapper.toResponse(review, response));
        }

        return reviewResponseList;
    }// </editor-fold>


    // <editor-fold default state="collapsed" desc="Get Review By UserID">
    @Override
    public List<ReviewResponse> getReviewResponseByProductId(int prId) {
        List<Review> list = reviewRepository.findByProductProductId(prId);

        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : list) {
            UserResponse response = userMapper.toUserResponse(review.getUser());
            reviewResponseList.add(reviewMapper.toResponse(review, response));
        }

        return reviewResponseList;
    }// </editor-fold>
}
