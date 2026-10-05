package com.n3.mebe.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Trạng thái đơn hàng
 */
public enum OrderStatus implements LabeledEnum {
    PENDING_CONFIRMATION("Chờ xác nhận"),
    PROCESSING("Đang được xử lý"),
    AWAITING_PAYMENT("Đang thanh toán"),
    SHIPPING("Đang giao"),
    DELIVERED("Đã giao"),
    CANCELLED("Đã hủy"),
    REFUNDED("Hoàn trả");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static OrderStatus fromLabel(String label) {
        return LabeledEnum.fromLabel(OrderStatus.class, label);
    }

    public static class JpaConverter extends LabeledEnumConverter<OrderStatus> {
        public JpaConverter() {
            super(OrderStatus.class);
        }
    }
}
