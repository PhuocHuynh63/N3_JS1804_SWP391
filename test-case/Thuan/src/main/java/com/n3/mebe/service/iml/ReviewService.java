package com.n3.mebe.service.iml;

import com.n3.mebe.dto.request.review.ReviewRequest;
import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.review.ReviewResponse;
import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.dto.response.user.UserReviewResponse;
import com.n3.mebe.entity.Category;
import com.n3.mebe.entity.Review;
import com.n3.mebe.entity.SubCategory;
import com.n3.mebe.entity.User;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IReviewRepository;
import com.n3.mebe.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;


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

        ReviewResponse reviewResponse = new ReviewResponse();

        reviewResponse.setReviewId(review.getReviewId());

        UserResponse userResponse = userService.getUserByIdResponse(review.getUser().getUserId());
        reviewResponse.setUser(userResponse);
        reviewResponse.setProduct(review.getProduct());
        reviewResponse.setRate(review.getRate());
        reviewResponse.setComment(review.getComment());
        reviewResponse.setCreateAt(review.getCreateAt());
        reviewResponse.setUpdateAt(review.getUpdateAt());

        return reviewResponse;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get Review By UserID">
    @Override
    public List<ReviewResponse> getReviewResponseByUserId(int userId) {
        List<Review> list = reviewRepository.findByUserUserId(userId);

        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : list) {
            ReviewResponse reviewResponse = new ReviewResponse();

            reviewResponse.setReviewId(review.getReviewId());

            UserResponse response = userService.getUserByIdResponse(userId);
            reviewResponse.setUser(response);

            reviewResponse.setProduct(review.getProduct());

            reviewResponse.setRate(review.getRate());
            reviewResponse.setComment(review.getComment());
            reviewResponse.setCreateAt(review.getCreateAt());
            reviewResponse.setUpdateAt(review.getUpdateAt());
            reviewResponseList.add(reviewResponse);
        }

        return reviewResponseList;
    }// </editor-fold>


    // <editor-fold default state="collapsed" desc="Get Review By UserID">
    @Override
    public List<ReviewResponse> getReviewResponseByProductId(int prId) {
        List<Review> list = reviewRepository.findByProductProductId(prId);

        List<ReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : list) {
            ReviewResponse reviewResponse = new ReviewResponse();

            reviewResponse.setReviewId(review.getReviewId());

            UserResponse response = userService.getUserByIdResponse(review.getUser().getUserId());
            reviewResponse.setUser(response);

            reviewResponse.setProduct(review.getProduct());

            reviewResponse.setRate(review.getRate());
            reviewResponse.setComment(review.getComment());
            reviewResponse.setCreateAt(review.getCreateAt());
            reviewResponse.setUpdateAt(review.getUpdateAt());
            reviewResponseList.add(reviewResponse);
        }

        return reviewResponseList;
    }// </editor-fold>
}
