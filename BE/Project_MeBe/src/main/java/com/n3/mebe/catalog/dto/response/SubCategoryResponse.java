package com.n3.mebe.catalog.dto.response;

import com.n3.mebe.catalog.entity.Category;
import com.n3.mebe.catalog.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponse {

    private int subCategoryId;
    private String category_parent;
    private String slug;
    private String name;
    private String image;
    private String image2;
}
