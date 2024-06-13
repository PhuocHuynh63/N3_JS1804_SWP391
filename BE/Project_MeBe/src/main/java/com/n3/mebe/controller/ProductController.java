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
    Product updateProduct(@PathVariable("id") int id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
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
    List<Product> listProduct() {
        return productService.getListProduct();
    }

    //Response a product by id
    @GetMapping("/id={id}")
    ProductResponse productById(@PathVariable("id") int id) {
        return productService.getProductByIdResponse(id);
    }

    //Response product_subcategory
    @GetMapping("/list_cate={cate}")
    List<ProductResponse> listProductBySubCate(@PathVariable("cate") String cate) {
        return productService.getProductResponseList(cate);
    }

    //Response list a product by id or name
    @GetMapping("/search_product={id}_name={name}")
    List<ProductResponse> searchProduct(@PathVariable("id") int id, @PathVariable("name") String name) {
        return productService.getListProductByIdOrName(id, name);
    }



}
