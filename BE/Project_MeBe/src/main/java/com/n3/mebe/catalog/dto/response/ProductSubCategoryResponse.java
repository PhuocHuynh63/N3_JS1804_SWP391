package com.n3.mebe.catalog.dto.response;

/**
 * SubCategory lồng trong ProductResponse. Giữ tên field cũ (subCateId, category.name...) cho FE.
 */
public record ProductSubCategoryResponse(
        int subCateId,
        String name,
        String slug,
        String image,
        String image2,
        Category category
) {
    public record Category(int categoryId, String name, String slug) {
    }
}
