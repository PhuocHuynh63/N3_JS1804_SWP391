package com.n3.mebe.user.dto.response;

import com.n3.mebe.catalog.dto.response.ProductSummaryResponse;
import lombok.Data;

import java.util.Date;


@Data
public class UserReviewResponse {

    private int reviewId;
    private ProductSummaryResponse product;
    private String rate;
    private String comment;
    private Date createAt;
    private Date updateAt;
}
