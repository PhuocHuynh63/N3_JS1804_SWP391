package com.n3.mebe.catalog.service;

import com.n3.mebe.catalog.dto.request.ProductRequest;
import com.n3.mebe.catalog.dto.response.ProductResponse;
import com.n3.mebe.catalog.entity.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {

    List<ProductResponse> getListProduct();

    Product getProductById(int id);


    ProductResponse getProductByIdResponse(int id);

//    Product createProduct(ProductRequest productRequest);

    boolean createProduct(MultipartFile file, ProductRequest request);

    boolean updateProduct(int id,MultipartFile file, ProductRequest request);

    boolean setStatus(int prId, String status);

    void deleteProduct(int id);

    void deleteProductReal(int id);

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
