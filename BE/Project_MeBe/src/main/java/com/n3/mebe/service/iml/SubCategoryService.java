package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.entity.Category;
import com.n3.mebe.entity.SubCategory;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.ICategoryRepository;
import com.n3.mebe.repository.ISubCategoryRepository;
import com.n3.mebe.service.ICloudinaryService;
import com.n3.mebe.service.ISubCategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    private ISubCategoryRepository subCategoryRepository;

    @Autowired
    private ICategoryRepository icategoryRepository;

    @Autowired
    private ICloudinaryService cloudinaryService;


    // <editor-fold default state="collapsed" desc="get SubCategory By Id">
    @Override
    public SubCategory getSubCategoryById(int subCateId) {
        return subCategoryRepository.findById(subCateId)
                .orElseThrow(() -> new AppException(ErrorCode.SUB_CATEGORY_ID_NO_EXIST));
    }// </editor-fold>

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create SubCategory">
    @Transactional
    @Override
    public boolean createSubCategory(MultipartFile img1, MultipartFile img2, SubCategoryRequest request) {
        boolean check = false;
        Category category = icategoryRepository.findByName(request.getCategoryParentName());
        String folder = "Sub_Category";
        String urlImg1 = cloudinaryService.saveFileToFolder(img1 , folder);
        String urlImg2 = cloudinaryService.saveFileToFolder(img2 , folder);

        if (category != null){
            SubCategory subCategory = new SubCategory();

            subCategory.setCategory(category);
            subCategory.setName(request.getName());

            if(urlImg1 != null ){
                subCategory.setImage(urlImg1);
            }
            if (urlImg2 != null){
                subCategory.setImage2(urlImg2);
            }
            subCategoryRepository.save(subCategory);
            check = true;
        }else {
            throw new AppException(ErrorCode.CATEGORY_NO_EXIST);
        }
        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update SubCategory">
    @Transactional
    @Override
    public boolean updateSubCategory(int id, MultipartFile img1, MultipartFile img2 ,SubCategoryRequest request) {
        boolean check = false;
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUB_CATEGORY_NO_EXIST));

        Category category = icategoryRepository.findByName(request.getCategoryParentName());
        String folder = "Sub_Category";
        String urlImg1 = null;
        String urlImg2 = null;
        if(!img1.isEmpty() ){
            urlImg1 = cloudinaryService.saveFileToFolder(img1 , folder);
        }
        if(!img2.isEmpty()){
            urlImg2 = cloudinaryService.saveFileToFolder(img2 , folder);
        }

        if (category != null){

            subCategory.setCategory(category);
            subCategory.setName(request.getName());

            if(urlImg1 != null ){
                subCategory.setImage(urlImg1);
            }
            if (urlImg2 != null){
                subCategory.setImage2(urlImg2);
            }
            subCategoryRepository.save(subCategory);
            check = true;
        }else {
            throw new AppException(ErrorCode.CATEGORY_NO_EXIST);
        }
        return check;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Delete SubCategory">
    @Override
    public void deleteSubCategory(int subCategoryId) {
        subCategoryRepository.deleteById(subCategoryId);
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


           subCategoryResponse.setSubCategoryId(subCategory.getSubCateId());
           //lấy ra category tên cha
           subCategoryResponse.setCategory_parent(subCategory.getCategory().getName());
           subCategoryResponse.setSlug(subCategory.getSlug());
           subCategoryResponse.setName(subCategory.getName());
           subCategoryResponse.setImage(subCategory.getImage());
           subCategoryResponse.setImage2(subCategory.getImage2());
           subCategoryResponses.add(subCategoryResponse);
       }

        return subCategoryResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get SubCategories Response By cateParent Name">
    @Override
    public List<SubCategoryResponse> getSubCategoriesResponse(String categoryParentName) {

        Category category = icategoryRepository.findByName(categoryParentName);

        List<SubCategory> subCategories = subCategoryRepository.findByCategory(category);

        List<SubCategoryResponse> subCategoryResponses = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {
            SubCategoryResponse subCategoryResponse = new SubCategoryResponse();

            //lấy ra category tên cha
            subCategoryResponse.setCategory_parent(subCategory.getCategory().getName());

            subCategoryResponse.setSlug(subCategory.getSlug());
            subCategoryResponse.setName(subCategory.getName());
            subCategoryResponse.setImage(subCategory.getImage());
            subCategoryResponse.setImage2(subCategory.getImage2());
            subCategoryResponses.add(subCategoryResponse);
        }

        return subCategoryResponses;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get SubCategories Response By Slug">
        @Override
        public List<SubCategoryResponse> getSubCategoriesBySlug(String slug) {
        List<SubCategory> subCategories = subCategoryRepository.findBySlug(slug);

        List<SubCategoryResponse> subCategoryResponses = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {
            SubCategoryResponse subCategoryResponse = new SubCategoryResponse();

            subCategoryResponse.setSubCategoryId(subCategory.getSubCateId());
            //lấy ra category tên cha
            subCategoryResponse.setCategory_parent(subCategory.getCategory().getName());

            subCategoryResponse.setSlug(subCategory.getSlug());
            subCategoryResponse.setName(subCategory.getName());
            subCategoryResponse.setImage(subCategory.getImage());
            subCategoryResponse.setImage2(subCategory.getImage2());
            subCategoryResponses.add(subCategoryResponse);
        }

        return subCategoryResponses;
        }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get SubCategories By Id Response">
    @Override
    public SubCategoryResponse getSubCategoriesByIdResponse(int subCateId) {
        SubCategory subCategory = getSubCategoryById(subCateId);

        SubCategoryResponse subCategoryResponse = new SubCategoryResponse();

        subCategoryResponse.setSubCategoryId(subCategory.getSubCateId());
        //lấy ra category tên cha
        subCategoryResponse.setCategory_parent(subCategory.getCategory().getName());

        subCategoryResponse.setSlug(subCategory.getSlug());
        subCategoryResponse.setName(subCategory.getName());
        subCategoryResponse.setImage(subCategory.getImage());
        subCategoryResponse.setImage2(subCategory.getImage2());

        return subCategoryResponse;
    }// </editor-fold>
}
