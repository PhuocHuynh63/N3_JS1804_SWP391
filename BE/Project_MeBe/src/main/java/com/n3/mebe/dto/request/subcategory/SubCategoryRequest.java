package com.n3.mebe.dto.request.subcategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequest {

    private String categoryParentName;
    private String name;
    private String slug;


}
