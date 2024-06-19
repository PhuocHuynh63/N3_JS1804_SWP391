package com.n3.mebe.service;

import com.n3.mebe.dto.request.product.ProductAttributeRequest;
import com.n3.mebe.dto.response.product.ProductAttributeResponse;
import com.n3.mebe.entity.ProductAttribute;

import java.util.List;

public interface IProductAttributeService {

    List<ProductAttributeResponse> getProductAttributesAll();

    ProductAttribute createProductAttribute(ProductAttributeRequest productAttributeRequest);

    ProductAttribute updateProductAttribute(int id, ProductAttributeRequest productAttributeRequest);

    void deleteProductAttribute(int id);

    ProductAttributeResponse getProductAttributesById(int id);
}
