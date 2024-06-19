package com.n3.mebe.service;

import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<Product> getListProduct();

    Product getProductById(int id);


    ProductResponse getProductByIdResponse(int id);

    List<Product> getListProductBySubCateName(String cate);

//    Product createProduct(ProductRequest productRequest);

    boolean createProduct(MultipartFile file, int subCategory, String slug, String name, String description, float price, float salePrice, String status, int totalSold, int productView);

    Product updateProduct(int id, ProductRequest productRequest);

    void deleteProduct(int id);

    List<ProductResponse> getListProductByIdOrName(int id, String name);
}
