package com.n3.mebe.controller;

import com.n3.mebe.dto.request.category.CategoryRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.category.CategoryResponse;

import com.n3.mebe.entity.Category;
import com.n3.mebe.service.ICategoryService;
import com.n3.mebe.service.iml.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    /**
     *  Request from Client
     *
     */

    @PostMapping("/create_cate")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        boolean check = categoryService.createCategory(request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Tạo thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Tao thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @PutMapping("/update_cate/cateId={id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody CategoryRequest request) {
        boolean check = categoryService.updateCategory(id,request);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Cập nhật thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Cập nhật thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @DeleteMapping("/delete_cate/cateId={id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
        return "Xóa thành công";
    }

    /**
     * Response to Client
     *
     */

    @GetMapping("/list")
    List<CategoryResponse> list() {
        return categoryService.getListCategory();
    }

    @GetMapping("/slug={slug}")
    CategoryResponse getCategoryBySlug(@PathVariable("slug") String slug) {
        return categoryService.getCategoryBySlug(slug);
    }
}
