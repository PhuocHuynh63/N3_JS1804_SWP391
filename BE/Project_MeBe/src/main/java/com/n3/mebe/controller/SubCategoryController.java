package com.n3.mebe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3.mebe.dto.request.category.CategoryRequest;
import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.request.subcategory.SubCategoryRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.subcategory.SubCategoryResponse;
import com.n3.mebe.service.ISubCategoryService;
import com.n3.mebe.service.iml.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/sub_category")
public class SubCategoryController  {

    @Autowired
    private ISubCategoryService subCategoryService;


    @PostMapping(value = "/create_sub_cate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCategory(
            @RequestPart("img1") MultipartFile img1,
            @RequestPart("img2") MultipartFile img2,
            @RequestBody String requestJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        SubCategoryRequest subCategoryRequest;
        try {
            subCategoryRequest = objectMapper.readValue(requestJson, SubCategoryRequest.class);
        } catch (IOException e) {
            return new ResponseEntity<>("Invalid SubCate JSON", HttpStatus.BAD_REQUEST);
        }
        boolean check = subCategoryService.createSubCategory(img1, img2, subCategoryRequest);
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

    @PutMapping(value = "/update_sub_cate/subId={id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCategory(
            @PathVariable("id") int subId,
            @RequestPart("img1") MultipartFile img1,
            @RequestPart("img2") MultipartFile img2,
            @RequestBody String requestJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        SubCategoryRequest subCategoryRequest;
        try {
            subCategoryRequest = objectMapper.readValue(requestJson, SubCategoryRequest.class);
        } catch (IOException e) {
            return new ResponseEntity<>("Invalid SubCate JSON", HttpStatus.BAD_REQUEST);
        }
        boolean check = subCategoryService.updateSubCategory(subId,img1, img2, subCategoryRequest);
        ResponseData responseData = new ResponseData();
        if (check) {
            responseData.setDescription("Sửa thành công");
            responseData.setSuccess(true);
            responseData.setStatus(200);
        } else {
            responseData.setDescription("Sửa thất bại");
            responseData.setSuccess(false);
            responseData.setStatus(400);
        }
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }

    @DeleteMapping("/delete/subId={id}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable("subId") int id){
        subCategoryService.deleteSubCategory(id);
        ResponseData  responseData  = new ResponseData();
        responseData.setDescription("Xóa thành công");
        responseData.setSuccess(true);
        responseData.setStatus(200);
        return ResponseEntity.status(responseData.getStatus()).body(responseData);
    }


    /**
     * Response from client
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
