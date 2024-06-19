package com.n3.mebe.service.iml;


import com.n3.mebe.dto.request.product.ProductAttributeRequest;
import com.n3.mebe.dto.response.product.ProductAttributeResponse;
import com.n3.mebe.entity.ProductAttribute;
import com.n3.mebe.exception.AppException;
import com.n3.mebe.exception.ErrorCode;
import com.n3.mebe.repository.IProductAttributeRepository;
import com.n3.mebe.service.IProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAttributeService implements IProductAttributeService {

    @Autowired
    private IProductAttributeRepository productAttributeRepository;


    /**
     *  Request from Client
     *
     */

    // <editor-fold default state="collapsed" desc="get Product Attribute By Id">
    public ProductAttribute getProductAttributeById(int id) {
        return productAttributeRepository.findById(id)
                .orElseThrow( ()-> new AppException(ErrorCode.PRODUCT_ATTRIBUTE_NO_EXIST));
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Create Product Attribute">
    @Override
    public ProductAttribute createProductAttribute(ProductAttributeRequest request) {

        //check xem có tồn tại Product attribute với type và value giống không
        if (productAttributeRepository.existsByTypeAndValue(request.getType(), request.getValue()) ){
            throw new AppException(ErrorCode.PRODUCT_ATTRIBUTE_NO_EXIST);
        }

        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setType(request.getType());
        productAttribute.setValue(request.getValue());

        return productAttributeRepository.save(productAttribute);
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Update Product Attribute">
    @Override
    public ProductAttribute updateProductAttribute(int id, ProductAttributeRequest request) {

        // lấy product attribute ra nêu không có thì thông báo lỗi
        ProductAttribute productAttribute = getProductAttributeById(id);

        productAttribute.setType(request.getType());
        productAttribute.setValue(request.getValue());

        return productAttributeRepository.save(productAttribute);
    }// </editor-fold>


    // <editor-fold default state="collapsed" desc="Delete Product Attribute">
    @Override
    public void deleteProductAttribute(int id) {
        productAttributeRepository.deleteById(id);
    }// </editor-fold>

    /**
     * Response to Client
     *
     */

    // <editor-fold default state="collapsed" desc="Get All Product Attributes All">
    @Override
    public List<ProductAttributeResponse> getProductAttributesAll() {
        List<ProductAttribute> productAttributes = productAttributeRepository.findAll();
        List<ProductAttributeResponse> productAttributeResponseList = new ArrayList<>();

        for (ProductAttribute productAttribute : productAttributes) {
            ProductAttributeResponse productAttributeResponse = new ProductAttributeResponse();

            productAttributeResponse.setPaId(productAttribute.getPaId());
            productAttributeResponse.setType(productAttribute.getType());
            productAttributeResponse.setValue(productAttribute.getValue());
            productAttributeResponseList.add(productAttributeResponse);
        }
        return productAttributeResponseList;
    }// </editor-fold>

    // <editor-fold default state="collapsed" desc="Get All Product Attributes All">
    @Override
    public ProductAttributeResponse getProductAttributesById(int id) {
        ProductAttribute productAttribute = getProductAttributeById(id);
        ProductAttributeResponse productAttributeResponse = new ProductAttributeResponse();

        productAttributeResponse.setPaId(productAttribute.getPaId());
        productAttributeResponse.setType(productAttribute.getType());
        productAttributeResponse.setValue(productAttribute.getValue());

        return productAttributeResponse;
    }// </editor-fold>



}
