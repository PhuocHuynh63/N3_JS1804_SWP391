package com.n3.mebe.dto.request.product;

import com.n3.mebe.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private MultipartFile file;
    private SubCategory subCategory;
    private String slug;
    private String name;
    private String description;
    private float price;
    private float salePrice;
    private String status;
    private int totalSold;
    private int productView;

}