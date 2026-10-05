package com.n3.mebe.catalog.dto.request;

import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.user.entity.User;
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
