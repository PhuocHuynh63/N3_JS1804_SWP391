package com.n3.mebe.service;

import com.n3.mebe.dto.request.category.CategoryRequest;
import com.n3.mebe.dto.response.category.CategoryResponse;
import com.n3.mebe.entity.Category;

import java.util.List;

public interface ICategoryService {

    List<CategoryResponse> getListCategory();

    Category createCategory(CategoryRequest request);

    Category updateCategory(int cateId, CategoryRequest request);

    void deleteCategory(int cateId);

}
