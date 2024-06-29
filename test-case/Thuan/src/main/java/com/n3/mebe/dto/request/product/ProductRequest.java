package com.n3.mebe.dto.request.product;

import com.n3.mebe.entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ProductRequest {

    private int subCategoryId;
    private String slug;
    private String name;
    private String description;
    private float price;
    private float salePrice;
    private String status;
    private int totalSold;
    private int quantity;
    private int productView;

}