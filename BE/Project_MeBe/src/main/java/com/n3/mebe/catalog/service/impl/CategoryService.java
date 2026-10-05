package com.n3.mebe.catalog.service.impl;

import lombok.RequiredArgsConstructor;



import com.n3.mebe.catalog.dto.request.CategoryRequest;
import com.n3.mebe.catalog.dto.response.CategoryResponse;

import com.n3.mebe.catalog.entity.Category;

import com.n3.mebe.shared.exception.AppException;
import com.n3.mebe.shared.exception.ErrorCode;
import com.n3.mebe.catalog.mapper.CategoryMapper;
import com.n3.mebe.catalog.repository.ICategoryRepository;

import com.n3.mebe.catalog.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository icategoryRepository;

    private final CategoryMapper categoryMapper;

    // <editor-fold default state="collapsed" desc="Get Category By Id">
    Category getCategoryById (int id) throws AppException {
        return icategoryRepository.findById(id).orElseThrow( () -> new AppException(ErrorCode.CATEGORY_NO_EXIST));
    }//</editor-fold>

    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="Create Category">
    @Override
    public boolean createCategory(CategoryRequest request) {
        boolean check = icategoryRepository.existsByName(request.getName());

        if (check){
            throw new AppException(ErrorCode.CATEGORY_EXIST);
        }else {
            Category category = new Category();
            category.setName(request.getName());
            category.setSlug(request.getSlug());
            icategoryRepository.save(category);
            check = true;
        }
        return check ;
    }//</editor-fold>

    // <editor-fold default state="collapsed" desc="Update Category">
    @Override
    public boolean updateCategory(int cateId, CategoryRequest request) {
        boolean check = false;
        Category category = getCategoryById(cateId);
       if(category != null){
           category.setName(request.getName());
           category.setSlug(request.getSlug());
           icategoryRepository.save(category);
           check = true;
       }
        return check;
    }//</editor-fold>

    // <editor-fold default state="collapsed" desc="Delete Category">
    @Override
    public void deleteCategory(int cateId) {
        icategoryRepository.deleteById(cateId);
    }//</editor-fold>


    /**
     *  Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get List Category Response">
    @Override
    public List<CategoryResponse> getListCategory() {
        return categoryMapper.toResponseList(icategoryRepository.findAll());
    } //</editor-fold>

    // <editor-fold default state="collapsed" desc="Get Category By Slug">
    @Override
    public CategoryResponse getCategoryBySlug(String slug) {
        Category category = icategoryRepository.findBySlug(slug);
        return categoryMapper.toResponse(category);
    } //</editor-fold>

    // <editor-fold default state="collapsed" desc="Get Category By ID Response">
    @Override
    public CategoryResponse getCategoryByIdResponse(int cateId) {
        return categoryMapper.toResponse(getCategoryById(cateId));
    } //</editor-fold>

}
