package com.n3.mebe.catalog.service;

import com.n3.mebe.catalog.dto.request.CategoryRequest;
import com.n3.mebe.catalog.dto.response.CategoryResponse;
import com.n3.mebe.catalog.entity.Category;

import java.util.List;

public interface ICategoryService {

    boolean createCategory(CategoryRequest request);

    boolean updateCategory(int cateId, CategoryRequest request);

    void deleteCategory(int cateId);

    List<CategoryResponse> getListCategory();

    CategoryResponse getCategoryBySlug(String slug);

    CategoryResponse getCategoryByIdResponse(int cateId);
}
