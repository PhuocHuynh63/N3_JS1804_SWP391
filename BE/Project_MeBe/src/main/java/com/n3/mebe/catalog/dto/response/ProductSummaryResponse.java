package com.n3.mebe.catalog.dto.response;

import com.n3.mebe.catalog.entity.ProductStatus;

import java.math.BigDecimal;

/**
 * Thông tin rút gọn của sản phẩm, dùng khi lồng trong response khác (review, order detail).
 * Không chứa quan hệ JPA -> không bị đệ quy / lazy-loading khi serialize JSON.
 */
public record ProductSummaryResponse(
        int productId,
        String name,
        String slug,
        String images,
        BigDecimal price,
        BigDecimal salePrice,
        ProductStatus status
) {
}
