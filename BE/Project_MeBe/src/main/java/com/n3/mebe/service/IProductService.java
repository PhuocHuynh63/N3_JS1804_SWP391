package com.n3.mebe.service;

import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.entity.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<ProductResponse> getListProduct();

    Product getProductById(int id);


    ProductResponse getProductByIdResponse(int id);

//    Product createProduct(ProductRequest productRequest);

    boolean createProduct(MultipartFile file, int subCategory, String slug, String name, String description, float price, float salePrice,
                          String status, int totalSold,int quantity, int productView);

    boolean updateProduct(int id, MultipartFile file, int subCategory, String slug, String name, String description,
                          float price, float salePrice, String status, int totalSold,int quantity, int productView);

    boolean setStatus(int prId, String status);

    void deleteProduct(int id);

    List<ProductResponse> getProductResponseList(String cate);

    List<ProductResponse> getListProductByName(String name);

    List<ProductResponse> getListProductCreatedAtDesc();

    List<ProductResponse> getListProductCreatedAtAsc();

    List<ProductResponse> getListProductByPriceDesc();

    List<ProductResponse> getListProductByPriceAcs();

    List<ProductResponse> sortProductByPriceMinToMax(float min, float max);

    List<ProductResponse> sortProductByAToZ();

    List<ProductResponse> sortProductByZToA();

    List<ProductResponse> getProductBestSeller();
}
