package com.n3.mebe.controller;


import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.service.iml.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}


