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
<<<<<<< HEAD
        subCategory.setImage(request.getImage());
=======
        subCategory.setSlug(request.getSlug());
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
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
<<<<<<< HEAD
           subCategoryResponse.setImage(subCategory.getImage());
=======
           subCategoryResponse.setSlug(subCategory.getSlug());
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
           subCategoryResponses.add(subCategoryResponse);
       }

        return subCategoryResponses;
    }// </editor-fold>
<<<<<<< HEAD


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
            subCategoryResponse.setImage(subCategory.getImage());
            subCategoryResponses.add(subCategoryResponse);
        }

        return subCategoryResponses;
    // </editor-fold>
=======
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
}
