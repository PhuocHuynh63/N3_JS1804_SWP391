package com.n3.mebe.catalog.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Trạng thái sản phẩm
 */
public enum ProductStatus implements LabeledEnum {
    IN_STOCK("Còn hàng"),
    OUT_OF_STOCK("Hết hàng"),
    DISCONTINUED("Không còn bán");

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ProductStatus fromLabel(String label) {
        return LabeledEnum.fromLabel(ProductStatus.class, label);
    }

    public static class JpaConverter extends LabeledEnumConverter<ProductStatus> {
        public JpaConverter() {
            super(ProductStatus.class);
        }
    }
}
