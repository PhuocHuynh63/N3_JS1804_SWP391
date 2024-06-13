package com.n3.mebe.controller;




import com.n3.mebe.dto.request.product.ProductAttributeRequest;
import com.n3.mebe.dto.response.product.ProductAttributeResponse;
import com.n3.mebe.entity.ProductAttribute;
import com.n3.mebe.service.iml.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product_attribute")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

    /**
     * Request from Client
     *
     */

    //Create a product
    @PostMapping("/create")
    ProductAttribute createProduct(@RequestBody ProductAttributeRequest request) {
        return  productAttributeService.createProductAttribute(request);
    }

    //Update a product by id
    @PutMapping("/update_product_attribute={id}")
    ProductAttribute updateProduct(@PathVariable("id") int id, @RequestBody ProductAttributeRequest request) {
        return productAttributeService.updateProductAttribute(id, request);
    }

    //Delete product by id
    @DeleteMapping("/delete/product_attribute={id}")
    String deleteProduct(@PathVariable("id") int id) {
        productAttributeService.deleteProductAttribute(id);
        return "Product deleted";
    }

    /**
     * Response to Client
     *
     */

    //Response list product
    @GetMapping("/list")
    List<ProductAttributeResponse> listProductAttribute() {
        return productAttributeService.getProductAttributesAll();
    }

    //Response a product by id
    @GetMapping("/product_attribute={id}")
    ProductAttributeResponse productAttributeById(@PathVariable("id") int id) {
        return productAttributeService.getProductAttributesById(id);
    }


}
