package com.n3.mebe.controller;


import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.service.iml.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/sub_category")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;


    /**
     *  Request from Client
     *
     */

    @GetMapping("/create_subcategory")
    public SubCategoryResponse createSubCategory() {
        return null;
    }





    /**
     *  Request from client
     *
     */
    @GetMapping("/list_all")
    public List<SubCategoryResponse> listAll() {
        return subCategoryService.getSubCategoriesResponse();
    }

    @GetMapping("/list/{cate}")
    public List<SubCategoryResponse> listByNameCategory(@PathVariable("cate") String cate) {
        return subCategoryService.getSubCategoriesResponse(cate);
    }

    @GetMapping("/{slug}")
    public List<SubCategoryResponse> getSubCategoryBySlug(@PathVariable("slug") String slug) {
        return subCategoryService.getSubCategoriesBySlug(slug);
    }

}


