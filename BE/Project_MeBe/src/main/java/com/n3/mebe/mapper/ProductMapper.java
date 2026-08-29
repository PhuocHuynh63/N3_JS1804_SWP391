package com.n3.mebe.mapper;

import com.n3.mebe.dto.request.product.ProductRequest;
import com.n3.mebe.dto.response.product.ProductResponse;
import com.n3.mebe.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductResponse toResponse(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    public List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream().map(this::toResponse).toList();
    }

    /**
     * Maps editable fields from request. Does not set subCategory, images, timestamps, or id.
     *
     * @param includeStatus when false, status is left unchanged (needed before wishlist check in update).
     */
    public void applyRequestToProduct(ProductRequest request, Product product, boolean includeStatus) {
        product.setSlug(request.getSlug());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSalePrice(request.getSalePrice());
        product.setTotalSold(request.getTotalSold());
        product.setQuantity(request.getQuantity());
        product.setProductView(request.getProductView());
        if (includeStatus) {
            product.setStatus(request.getStatus());
        }
    }
}
