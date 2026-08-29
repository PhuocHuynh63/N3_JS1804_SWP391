package com.n3.mebe.mapper;

import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.entity.SubCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubCategoryMapper {

    public SubCategoryResponse toResponse(SubCategory subCategory) {
        SubCategoryResponse response = new SubCategoryResponse();
        response.setSubCategoryId(subCategory.getSubCateId());
        if (subCategory.getCategory() != null) {
            response.setCategory_parent(subCategory.getCategory().getName());
        }
        response.setSlug(subCategory.getSlug());
        response.setName(subCategory.getName());
        response.setImage(subCategory.getImage());
        response.setImage2(subCategory.getImage2());
        return response;
    }

    public List<SubCategoryResponse> toResponseList(List<SubCategory> subCategories) {
        return subCategories.stream().map(this::toResponse).toList();
    }
}
