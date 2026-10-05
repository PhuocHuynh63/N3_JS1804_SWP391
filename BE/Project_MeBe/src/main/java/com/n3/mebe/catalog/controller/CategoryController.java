package com.n3.mebe.catalog.controller;

import lombok.RequiredArgsConstructor;

import com.n3.mebe.catalog.dto.request.CategoryRequest;
import com.n3.mebe.shared.dto.ResponseData;
import com.n3.mebe.catalog.dto.response.CategoryResponse;

import com.n3.mebe.catalog.entity.Category;
import com.n3.mebe.catalog.service.ICategoryService;
import com.n3.mebe.catalog.service.impl.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;


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

    @GetMapping("/cateId={id}")
    CategoryResponse getCategoryByID(@PathVariable("id") int cateId) {
        return categoryService.getCategoryByIdResponse(cateId);
    }
}
