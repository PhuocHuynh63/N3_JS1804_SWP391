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

<<<<<<< HEAD
    // <editor-fold default state="collapsed" desc="Get Category By Name">
    Category getCategoryByName(String name) //</editor-fold>
    ;
=======
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
}
