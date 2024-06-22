package com.n3.mebe.controller;


import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.entity.Product;
import com.n3.mebe.service.IFileService;
import com.n3.mebe.service.iml.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private IFileService fileService;

    /**
     * Request from Client
     *
     */

    //Create a product
    @PostMapping("/create_product")
    public ResponseEntity<?> createProduct(@RequestParam MultipartFile file,
                          @RequestParam int subCategory,
                          @RequestParam String slug,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam float price,
                          @RequestParam float salePrice,
                          @RequestParam String status,
                          @RequestParam int totalSold,
                          @RequestParam int productView) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = productService.createProduct(file, subCategory, slug, name, description, price, salePrice, status, totalSold, productView);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //Update a product by id
    @PutMapping("/update_product={id}")
    boolean updateProduct(@PathVariable("id") int id,
                          @RequestParam MultipartFile file,
                          @RequestParam int subCategory,
                          @RequestParam String slug,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam float price,
                          @RequestParam float salePrice,
                          @RequestParam String status,
                          @RequestParam int totalSold,
                          @RequestParam int productView) {
        return productService.updateProduct(id ,file, subCategory, slug, name, description, price, salePrice, status, totalSold, productView);
    }


    //Delete product by id
    @DeleteMapping("/delete/product_id={id}")
    String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "Product deleted";
    }

    /**
     * Response to Client
     *
     */

    //Response list product
    @GetMapping("/list")
    List<ProductResponse> listProduct() {
        return productService.getListProduct();
    }

    //Response a product by id
    @GetMapping("/{id}")
    ProductResponse productById(@PathVariable("id") int id) {
        return productService.getProductByIdResponse(id);
    }

    //Response product_subcategory
    @GetMapping("/list_subcate={slug}")
    List<ProductResponse> listProductBySubCate(@PathVariable("slug") String slug) {
        return productService.getProductResponseList(slug);
    }

    //Response list a product by id or name
    @GetMapping("/search")
    List<ProductResponse> searchProduct(@RequestParam String name) {
        return productService.getListProductByName(name);
    }

    //Response list a product sort by create at asc
    @GetMapping("/list/create_at_acs")
    List<ProductResponse> sortCreatAtAsc() {
        return productService.getListProductCreatedAtAsc();
    }

    //Response list a product sort by create at desc
    @GetMapping("/list/create_at_desc")
    List<ProductResponse> sortCreatAtDesc() {
        return productService.getListProductCreatedAtDesc();
    }

    @GetMapping("/list/price_desc")
    List<ProductResponse> sortProductByPriceDesc() {
        return productService.getListProductCreatedAtDesc();
    }

    @GetMapping("/list/price_asc")
    List<ProductResponse> sortProductByPriceAsc() {
        return productService.getListProductCreatedAtAsc();
    }

    @GetMapping("/list/sort_min_max")
    List<ProductResponse> sortProductByPriceAsc(@RequestParam float min, @RequestParam float max) {
        return productService.sortProductByPriceMinToMax(min, max);
    }





}
