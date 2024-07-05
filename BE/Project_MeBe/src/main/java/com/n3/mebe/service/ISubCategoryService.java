package com.n3.mebe.service;

import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.entity.SubCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISubCategoryService {

    boolean createSubCategory(MultipartFile img1, MultipartFile img2, SubCategoryRequest request);

    boolean updateSubCategory(int id, MultipartFile img1, MultipartFile img2, SubCategoryRequest request);

    void deleteSubCategory(int subCategoryId);


    List<SubCategoryResponse> getSubCategoriesResponse();

    List<SubCategoryResponse> getSubCategoriesResponse(String categoryParentName);

    List<SubCategoryResponse> getSubCategoriesBySlug(String slug);
}
