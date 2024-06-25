package com.n3.mebe.dto.response.user;

import com.n3.mebe.entity.Product;
import lombok.Data;

import java.util.Date;


@Data
public class UserReviewResponse {

    private int reviewId;
    private Product product;
    private String rate;
    private String comment;
    private Date createAt;
    private Date updateAt;
}
