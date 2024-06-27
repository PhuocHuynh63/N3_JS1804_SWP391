package com.n3.mebe.controller;


import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.service.ISubCategoryService;
import com.n3.mebe.service.iml.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/sub_category")
public class SubCategoryController {

    @Autowired
    private ISubCategoryService subCategoryService;


    /**
     *  Request from client
     *
     */
    @GetMapping("/list_all")
    List<SubCategoryResponse> listAll() {
        return subCategoryService.getSubCategoriesResponse();
    }

    @GetMapping("/list/{cate}")
    List<SubCategoryResponse> listByNameCategory(@PathVariable("cate") String cate) {
        return subCategoryService.getSubCategoriesResponse(cate);
    }

    @GetMapping("/{slug}")
    List<SubCategoryResponse> getSubCategoryBySlug(@PathVariable("slug") String slug) {
        return subCategoryService.getSubCategoriesBySlug(slug);
    }

}


