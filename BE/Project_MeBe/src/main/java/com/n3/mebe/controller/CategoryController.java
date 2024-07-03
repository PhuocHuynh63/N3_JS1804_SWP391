package com.n3.mebe.controller;


import com.n3.mebe.dto.request.category.CategoryRequest;
import com.n3.mebe.dto.response.category.CategoryResponse;

import com.n3.mebe.entity.Category;
import com.n3.mebe.service.ICategoryService;
import com.n3.mebe.service.iml.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


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
