package com.n3.mebe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.ResponseData;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.service.IProductService;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;



    /**
     * Request from Client
     *
     */

    // Create a product
    @PostMapping(value = "/create_product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestPart("file") MultipartFile file, @RequestPart("product") String productJson) {

        // Convert JSON string to ProductRequest object
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest request;
        try {
            request = objectMapper.readValue(productJson, ProductRequest.class);
        } catch (IOException e) {
            return new ResponseEntity<>("Invalid product JSON", HttpStatus.BAD_REQUEST);
        }

        ResponseData responseData = new ResponseData();
        boolean isSuccess = productService.createProduct(file, request);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // Update a product by id
    @PutMapping("/update_product={id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id,
                          @RequestPart("file") MultipartFile file,
                          @RequestPart("product") String productJson) {

        // Convert JSON string to ProductRequest object
        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequest request;
        try {
            request = objectMapper.readValue(productJson, ProductRequest.class);
        } catch (IOException e) {
            return new ResponseEntity<>("Invalid product JSON", HttpStatus.BAD_REQUEST);
        }
        ResponseData responseData = new ResponseData();
        boolean isSuccess = productService.updateProduct(id ,file, request);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update_status/product_id={prId}")
    boolean updateStatus(@PathVariable("prId") int prId, @RequestParam String status) {
        return productService.setStatus(prId, status);
    }

    // Delete product by id
    @DeleteMapping("/delete/product_id={id}")
    String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return "Product deleted";
    }

    /**
     * Response to Client
     *
     */

    // Response list product
    @GetMapping("/list")
    List<ProductResponse> listProduct() {
        return productService.getListProduct();
    }

    // Response a product by id
    @GetMapping("/{id}")
    ProductResponse productById(@PathVariable("id") int id) {
        return productService.getProductByIdResponse(id);
    }

    // Response product_subcategory
    @GetMapping("/list_subcate={slug}")
    List<ProductResponse> listProductBySubCate(@PathVariable("slug") String slug) {
        return productService.getProductResponseList(slug);
    }

    // Response list a product by id or name
    @GetMapping("/search")
    List<ProductResponse> searchProduct(@RequestParam String name) {
        return productService.getListProductByName(name);
    }

    // Response list a product sort by create at asc
    @GetMapping("/list/create_at_asc")
    List<ProductResponse> sortCreatAtAsc() {
        return productService.getListProductCreatedAtAsc();
    }

    // Response list a product sort by create at desc
    @GetMapping("/list/create_at_desc")
    List<ProductResponse> sortCreatAtDesc() {
        return productService.getListProductCreatedAtDesc();
    }

    @GetMapping("/list/price_desc")
    List<ProductResponse> sortProductByPriceDesc() {
        return productService.getListProductByPriceDesc();
    }

    @GetMapping("/list/price_asc")
    List<ProductResponse> sortProductByPriceAsc() {
        return productService.getListProductByPriceAcs();
    }

    // chua can
    @GetMapping("/list/sort_min_max")
    List<ProductResponse> sortProductByPriceAsc(@RequestParam float min, @RequestParam float max) {
        return productService.sortProductByPriceMinToMax(min, max);
    }

    @GetMapping("/list/acs_name")
    List<ProductResponse> sortProductAToZ() {
        return productService.sortProductByAToZ();
    }

    @GetMapping("/list/desc_name")
    List<ProductResponse> sortProductZToA() {
        return productService.sortProductByZToA();
    }

    @GetMapping("/list/best_seller")
    List<ProductResponse> getProductBestSeller() {
        return productService.getProductBestSeller();
    }

}
