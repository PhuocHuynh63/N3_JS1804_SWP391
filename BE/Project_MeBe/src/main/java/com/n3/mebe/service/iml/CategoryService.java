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
    public Category createCategory(CategoryRequest request) {

        if (icategoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXIST);
        }

        Category category = new Category();

        category.setName(request.getName());
        category.setSlug(request.getSlug());

        return icategoryRepository.save(category);
    }//</editor-fold>

    // <editor-fold default state="collapsed" desc="Update Category">
    @Override
    public Category updateCategory(int cateId, CategoryRequest request) {

        Category category = getCategoryById(cateId);
        category.setName(request.getName());
        category.setSlug(request.getSlug());

        return icategoryRepository.save(category);
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
}
