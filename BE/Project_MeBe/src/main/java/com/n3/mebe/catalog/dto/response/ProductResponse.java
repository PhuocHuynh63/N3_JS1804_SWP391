package com.n3.mebe.catalog.dto.response;

import java.math.BigDecimal;
import com.n3.mebe.catalog.entity.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private int productId;
    private ProductSubCategoryResponse subCategory;
    private String slug;
    private String name;
    private String images;
    private String description;
    private BigDecimal price;
    private BigDecimal salePrice;
    private ProductStatus status;
    private int totalSold;
    private int quantity;
    private int productView;
    private Date createAt;
    private Date updateAt;
}