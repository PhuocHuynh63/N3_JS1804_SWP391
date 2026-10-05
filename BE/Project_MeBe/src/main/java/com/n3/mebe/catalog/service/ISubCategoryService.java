package com.n3.mebe.catalog.service;

import com.n3.mebe.catalog.dto.request.SubCategoryRequest;
import com.n3.mebe.catalog.dto.response.SubCategoryResponse;
import com.n3.mebe.catalog.entity.SubCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISubCategoryService {

    SubCategory getSubCategoryById(int subCateId);

    boolean createSubCategory(MultipartFile img1, MultipartFile img2, SubCategoryRequest request);

    boolean updateSubCategory(int id, MultipartFile img1, MultipartFile img2, SubCategoryRequest request);

    void deleteSubCategory(int subCategoryId);

    List<SubCategoryResponse> getSubCategoriesResponse();

    List<SubCategoryResponse> getSubCategoriesResponse(String categoryParentName);

    List<SubCategoryResponse> getSubCategoriesBySlug(String slug);

    SubCategoryResponse getSubCategoriesByIdResponse(int subCateId);
}
