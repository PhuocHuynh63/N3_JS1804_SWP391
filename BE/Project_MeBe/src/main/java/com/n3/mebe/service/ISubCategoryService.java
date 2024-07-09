package com.n3.mebe.service;

import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.entity.SubCategory;

import java.util.List;

public interface ISubCategoryService {

    List<SubCategoryResponse> getSubCategoriesResponse();

    List<SubCategoryResponse> getSubCategoriesResponse(String categoryParentName);

    List<SubCategoryResponse> getSubCategoriesBySlug(String slug);
}
