package com.n3.mebe.service;

import com.n3.mebe.dto.request.category.CategoryRequest;
import com.n3.mebe.dto.response.category.CategoryResponse;
import com.n3.mebe.entity.Category;

import java.util.List;

public interface ICategoryService {

    boolean createCategory(CategoryRequest request);

    boolean updateCategory(int cateId, CategoryRequest request);

    void deleteCategory(int cateId);

    List<CategoryResponse> getListCategory();

    CategoryResponse getCategoryBySlug(String slug);

    CategoryResponse getCategoryByIdResponse(int cateId);
}
