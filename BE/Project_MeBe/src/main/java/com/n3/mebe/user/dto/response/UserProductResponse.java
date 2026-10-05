package com.n3.mebe.user.dto.response;


import lombok.Data;


@Data
public class UserProductResponse {

    private int productId;
    private String slug;
    private String name;
    private String images;

}
