package com.n3.mebe.service.iml;



import com.n3.mebe.dto.request.category.CategoryRequest;
import com.n3.mebe.dto.response.category.CategoryResponse;

import com.n3.mebe.entity.Category;

import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.ICategoryRepository;

import com.n3.mebe.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository icategoryRepository;

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

        List<Category> categories = icategoryRepository.findAll();

        List<CategoryResponse> categoriesResponse = new ArrayList<>();
        for (Category category : categories) {


            CategoryResponse categoryResponse = new CategoryResponse();

            categoryResponse.setCategoryId(category.getCategoryId());
            categoryResponse.setName(category.getName());
            categoryResponse.setSlug(category.getSlug());
            categoriesResponse.add(categoryResponse);
        }
        return categoriesResponse;
    } //</editor-fold>

    // <editor-fold default state="collapsed" desc="Get Category By Slug">
    @Override
    public CategoryResponse getCategoryBySlug(String slug) {

        Category category = icategoryRepository.findBySlug(slug);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setName(category.getName());
        categoryResponse.setSlug(category.getSlug());

        return categoryResponse;
    } //</editor-fold>

    // <editor-fold default state="collapsed" desc="Get Category By ID Response">
    @Override
    public CategoryResponse getCategoryByIdResponse(int cateId) {
        Category category  = getCategoryById(cateId);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setName(category.getName());
        categoryResponse.setSlug(category.getSlug());
        return categoryResponse;
    } //</editor-fold>

}
