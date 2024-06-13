package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.entity.Category;
import com.n3.mebe.entity.SubCategory;
import com.n3.mebe.repository.ICategoryRepository;
import com.n3.mebe.repository.ISubCategoryRepository;
import com.n3.mebe.service.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    private ISubCategoryRepository subCategoryRepository;

    @Autowired
    private ICategoryRepository icategoryRepository;

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create SubCategory">
    @Override
    public SubCategory createSubCategory(SubCategoryRequest request) {
        Category category = icategoryRepository.findByName(request.getCategoryParentName());
        SubCategory subCategory = new SubCategory();

        subCategory.setCategory(category);
        subCategory.setName(request.getName());
        subCategory.setSlug(request.getSlug());
        return subCategoryRepository.save(subCategory);
    }// </editor-fold>


    // <editor-fold default state="collapsed" desc="Create User">
    @Override
    public SubCategory updateSubCategory(SubCategoryRequest request) {
        return null;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Create User">
    @Override
    public void deleteSubCategory(int subCategoryId) {

    }// </editor-fold>

    /**
     *  Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get SubCategories Response">
    @Override
    public List<SubCategoryResponse> getSubCategoriesResponse() {
       List<SubCategory> subCategories = subCategoryRepository.findAll();
       List<SubCategoryResponse> subCategoryResponses = new ArrayList<>();

       for (SubCategory subCategory : subCategories) {
           SubCategoryResponse subCategoryResponse = new SubCategoryResponse();

           //lấy ra category tên cha
           subCategoryResponse.setCategory_parent(subCategory.getCategory().getName());

           subCategoryResponse.setName(subCategory.getName());
           subCategoryResponse.setSlug(subCategory.getSlug());
           subCategoryResponses.add(subCategoryResponse);
       }

        return subCategoryResponses;
    }// </editor-fold>
}
