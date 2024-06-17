package com.n3.mebe.dto.response.product;

import com.n3.mebe.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private SubCategory subCategory;
    private String slug;
    private String name;
    private String images;
    private String description;
    private float price;
    private float salePrice;
    private String status;
    private int totalSold;
    private int productView;
    private Date createAt;
    private Date updateAt;
}