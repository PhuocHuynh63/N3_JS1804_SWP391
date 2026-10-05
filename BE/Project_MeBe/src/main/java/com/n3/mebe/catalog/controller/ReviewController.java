package com.n3.mebe.catalog.controller;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.catalog.dto.request.ReviewRequest;
import com.n3.mebe.catalog.dto.response.ReviewResponse;
import com.n3.mebe.catalog.entity.Review;
import com.n3.mebe.catalog.service.IReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

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
