package com.n3.mebe.dto.response.user;


import lombok.Data;


@Data
public class UserProductResponse {

    private int productId;
    private String slug;
    private String name;
    private String images;

}
