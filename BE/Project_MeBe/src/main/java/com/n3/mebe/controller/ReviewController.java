package com.n3.mebe.controller;


import com.n3.mebe.dto.request.review.ReviewRequest;
import com.n3.mebe.dto.response.review.ReviewResponse;
import com.n3.mebe.entity.Review;
import com.n3.mebe.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @GetMapping("/list")
    public List<ReviewResponse> listReview() {
        return reviewService.getReviewAll();
    }

    @GetMapping("/list{id}")
    public ReviewResponse listReviewById(@PathVariable("id") int id) {
        return reviewService.getReviewResponse(id);
    }

    @PostMapping("/create")
    public Review create(ReviewRequest request) {
        return reviewService.addReview(request);
    }

    @PutMapping("/update/reviewId={id}")
    public Review update(@PathVariable("id") int id, ReviewRequest request) {
        return reviewService.updateReview(id, request);
    }

    @DeleteMapping("/delete/reviewId={id}")
    public String delete(@PathVariable("id") int id) {
        reviewService.deleteReview(id);
        return "Deleted";
    }
}
