package com.n3.mebe.controller;


import com.n3.mebe.dto.request.review.ReviewRequest;
import com.n3.mebe.dto.response.review.ReviewResponse;
import com.n3.mebe.entity.Review;
import com.n3.mebe.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;


    @PostMapping("/create")
    Review create(@RequestBody ReviewRequest request) {
        return reviewService.addReview(request);
    }

    @PutMapping("/update/reviewId={id}")
    Review update(@PathVariable("id") int id, @RequestBody ReviewRequest request) {
        return reviewService.updateReview(id, request);
    }

    @DeleteMapping("/delete/reviewId={id}")
    String delete(@PathVariable("id") int id) {
        reviewService.deleteReview(id);
        return "Deleted";
    }

    @GetMapping("/userId={id}")
    List<ReviewResponse> getReviewByUserId(@PathVariable("id") int id) {
        return reviewService.getReviewResponseByUserId(id);
    }

    @GetMapping("/productId={id}")
    List<ReviewResponse> getReviewByProductId(@PathVariable("id") int id) {
        return reviewService.getReviewResponseByProductId(id);
    }




}
