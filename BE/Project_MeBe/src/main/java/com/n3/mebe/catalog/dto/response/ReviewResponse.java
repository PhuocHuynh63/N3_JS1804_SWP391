package com.n3.mebe.catalog.dto.response;


import com.n3.mebe.user.dto.response.UserResponse;
import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.user.entity.User;
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
