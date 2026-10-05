package com.n3.mebe.payment.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Trạng thái thanh toán (dùng cho Order và Payment)
 */
public enum PaymentStatus implements LabeledEnum {
    UNPAID("Chưa thanh toán"),
    PAID("Đã thanh toán"),
    CANCELLED("Hủy thanh toán");

    private final String label;

    PaymentStatus(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static PaymentStatus fromLabel(String label) {
        return LabeledEnum.fromLabel(PaymentStatus.class, label);
    }

    public static class JpaConverter extends LabeledEnumConverter<PaymentStatus> {
        public JpaConverter() {
            super(PaymentStatus.class);
        }
    }
}
