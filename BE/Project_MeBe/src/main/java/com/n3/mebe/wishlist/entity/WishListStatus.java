package com.n3.mebe.wishlist.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Trạng thái đăng ký chờ hàng
 */
public enum WishListStatus implements LabeledEnum {
    WAITING("Chờ thông báo"),
    AVAILABLE("Đã có hàng");

    private final String label;

    WishListStatus(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static WishListStatus fromLabel(String label) {
        return LabeledEnum.fromLabel(WishListStatus.class, label);
    }

    public static class JpaConverter extends LabeledEnumConverter<WishListStatus> {
        public JpaConverter() {
            super(WishListStatus.class);
        }
    }
}
