package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.entity.Category;
import com.n3.mebe.entity.SubCategory;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.mapper.SubCategoryMapper;
import com.n3.mebe.repository.ICategoryRepository;
import com.n3.mebe.repository.ISubCategoryRepository;
import com.n3.mebe.service.ICloudinaryService;
import com.n3.mebe.service.ISubCategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    private ISubCategoryRepository subCategoryRepository;

    @Autowired
    private ICategoryRepository icategoryRepository;

    @Autowired
    private ICloudinaryService cloudinaryService;

    @Autowired
    private SubCategoryMapper subCategoryMapper;


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
        String urlImg1 = null;

        String urlImg2 = null;

        if(img1 != null){
             urlImg1 = cloudinaryService.saveFileToFolder(img1 , folder);
        }else {
            urlImg1 = "https://newhorizonindia.edu/nhengineering/innovation/wp-content/uploads/2020/01/default-placeholder.png";
        }

        if(img2 != null){
            urlImg2 = cloudinaryService.saveFileToFolder(img2 , folder);
        }else {
            urlImg2 = "https://newhorizonindia.edu/nhengineering/innovation/wp-content/uploads/2020/01/default-placeholder.png";
        }


        if (category != null){
            SubCategory subCategory = new SubCategory();

            subCategory.setCategory(category);
            if(subCategoryRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.SUB_CATEGORY_NAME_EXIST);
            }else {
                subCategory.setName(request.getName());
            }

            if(subCategoryRepository.existsBySlug(request.getSlug())){
                throw new AppException(ErrorCode.SUB_CATEGORY_SLUG_EXIST);
            }else {
                subCategory.setSlug(request.getSlug());
            }

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

        if(img1 != null){
            urlImg1 = cloudinaryService.saveFileToFolder(img1 , folder);
        }

        if(img2 != null){
            urlImg2 = cloudinaryService.saveFileToFolder(img2 , folder);
        }

        if (category != null){

            subCategory.setCategory(category);
            if(subCategoryRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.SUB_CATEGORY_NAME_EXIST);
            }else {
                subCategory.setName(request.getName());
            }

            if(subCategoryRepository.existsBySlug(request.getSlug())){
                throw new AppException(ErrorCode.SUB_CATEGORY_SLUG_EXIST);
            }else {
                subCategory.setSlug(request.getSlug());
            }

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
        return subCategoryMapper.toResponseList(subCategoryRepository.findAll());
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get SubCategories Response By cateParent Name">
    @Override
    public List<SubCategoryResponse> getSubCategoriesResponse(String categoryParentName) {

        Category category = icategoryRepository.findByName(categoryParentName);

        List<SubCategory> subCategories = subCategoryRepository.findByCategory(category);
        return subCategoryMapper.toResponseList(subCategories);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get SubCategories Response By Slug">
        @Override
        public List<SubCategoryResponse> getSubCategoriesBySlug(String slug) {
        List<SubCategory> subCategories = subCategoryRepository.findBySlug(slug);
        return subCategoryMapper.toResponseList(subCategories);
        }// </editor-fold>

    // <editor-fold default state="collapsed" desc="get SubCategories By Id Response">
    @Override
    public SubCategoryResponse getSubCategoriesByIdResponse(int subCateId) {
        return subCategoryMapper.toResponse(getSubCategoryById(subCateId));
    }// </editor-fold>
}
