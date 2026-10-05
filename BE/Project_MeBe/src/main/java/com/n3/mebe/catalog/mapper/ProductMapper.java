package com.n3.mebe.catalog.mapper;

import com.n3.mebe.catalog.dto.response.ProductSubCategoryResponse;
import com.n3.mebe.catalog.dto.response.ProductSummaryResponse;
import com.n3.mebe.catalog.entity.Category;
import com.n3.mebe.catalog.entity.SubCategory;
import com.n3.mebe.catalog.dto.request.ProductRequest;
import com.n3.mebe.catalog.dto.response.ProductResponse;
import com.n3.mebe.catalog.entity.Product;
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
        ProductResponse response = modelMapper.map(product, ProductResponse.class);
        response.setSubCategory(toSubCategoryResponse(product.getSubCategory()));
        return response;
    }

    public ProductSummaryResponse toSummary(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductSummaryResponse(product.getProductId(), product.getName(), product.getSlug(),
                product.getImages(), product.getPrice(), product.getSalePrice(), product.getStatus());
    }

    private ProductSubCategoryResponse toSubCategoryResponse(SubCategory subCategory) {
        if (subCategory == null) {
            return null;
        }
        Category category = subCategory.getCategory();
        ProductSubCategoryResponse.Category categoryResponse = category == null ? null
                : new ProductSubCategoryResponse.Category(category.getCategoryId(), category.getName(), category.getSlug());
        return new ProductSubCategoryResponse(subCategory.getSubCateId(), subCategory.getName(), subCategory.getSlug(),
                subCategory.getImage(), subCategory.getImage2(), categoryResponse);
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
