package com.n3.mebe.dto.response.review;


import com.n3.mebe.dto.response.user.UserResponse;
import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.User;
import lombok.Data;

import java.util.Date;


@Data
public class ReviewResponse {

    private int reviewId;
    private UserResponse user;
    private Product product;

    private String rate;
    private String comment;
    private Date createAt;
    private Date updateAt;
}
