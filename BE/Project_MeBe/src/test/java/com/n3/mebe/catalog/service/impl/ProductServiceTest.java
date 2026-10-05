package com.n3.mebe.catalog.service.impl;

import com.n3.mebe.catalog.entity.Product;
import com.n3.mebe.catalog.entity.ProductStatus;
import com.n3.mebe.catalog.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final int PRODUCT_ID = 10;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void reduceProductQuantity_notEnoughStock_returnsFalse() {
        givenProduct(product(1, ProductStatus.IN_STOCK));

        boolean result = productService.reduceProductQuantity(2, PRODUCT_ID);

        assertThat(result).isFalse();
        verify(productRepository, never()).save(any());
    }

    @Test
    void reduceProductQuantity_lastItems_marksOutOfStock() {
        Product product = givenProduct(product(2, ProductStatus.IN_STOCK));

        boolean result = productService.reduceProductQuantity(2, PRODUCT_ID);

        assertThat(result).isTrue();
        assertThat(product.getQuantity()).isZero();
        assertThat(product.getStatus()).isEqualTo(ProductStatus.OUT_OF_STOCK);
        verify(productRepository).save(product);
    }

    @Test
    void increaseProductQuantity_outOfStock_marksInStock() {
        Product product = givenProduct(product(0, ProductStatus.OUT_OF_STOCK));

        productService.increaseProductQuantity(3, PRODUCT_ID);

        assertThat(product.getQuantity()).isEqualTo(3);
        assertThat(product.getStatus()).isEqualTo(ProductStatus.IN_STOCK);
        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct_softDeletesAndSaves() {
        Product product = givenProduct(product(5, ProductStatus.IN_STOCK));

        productService.deleteProduct(PRODUCT_ID);

        assertThat(product.getStatus()).isEqualTo(ProductStatus.DISCONTINUED);
        verify(productRepository).save(product);
    }

    private Product givenProduct(Product product) {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
        return product;
    }

    private Product product(int quantity, ProductStatus status) {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setQuantity(quantity);
        product.setStatus(status);
        return product;
    }
}
