package com.n3.mebe.catalog.dto.request;

import java.math.BigDecimal;
import com.n3.mebe.catalog.entity.ProductStatus;

import com.n3.mebe.catalog.entity.SubCategory;
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
    private BigDecimal price;
    private BigDecimal salePrice;
    private ProductStatus status;
    private int totalSold;
    private int quantity;
    private int productView;

}