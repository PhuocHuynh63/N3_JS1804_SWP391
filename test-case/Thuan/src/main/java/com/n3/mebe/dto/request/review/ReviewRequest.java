package com.n3.mebe.dto.request.review;

import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.User;
import lombok.Data;

import java.util.Date;


@Data
public class ReviewRequest {

    private int reviewId;
    private int userId;
    private int productId;
    private String rate;
    private String comment;

}
