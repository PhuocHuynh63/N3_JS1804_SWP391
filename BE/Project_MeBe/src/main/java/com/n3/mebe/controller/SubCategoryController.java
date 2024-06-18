package com.n3.mebe.controller;


import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.service.iml.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.CrossOrigin;
=======
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

<<<<<<< HEAD
@CrossOrigin("*")
=======
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
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
<<<<<<< HEAD

    @GetMapping("/listByNameCategory")
    public List<SubCategoryResponse> listByNameCategory() {
        return subCategoryService.getSubCategoriesResponse();
    }
=======
>>>>>>> a69cb649d3ba6b1025674b237b9b5ba5c3946db0
}


