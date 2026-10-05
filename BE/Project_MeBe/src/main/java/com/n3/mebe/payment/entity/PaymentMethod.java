package com.n3.mebe.payment.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Hình thức thanh toán (Order.orderType, Payment.paymentType).
 * ONLINE là giá trị cũ còn trong DB, FE hiện gửi "VNPay"
 */
public enum PaymentMethod implements LabeledEnum {
    COD("COD"),
    VNPAY("VNPay"),
    ONLINE("Online");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static PaymentMethod fromLabel(String label) {
        return LabeledEnum.fromLabel(PaymentMethod.class, label);
    }

    public boolean isOnline() {
        return this == VNPAY || this == ONLINE;
    }

    public static class JpaConverter extends LabeledEnumConverter<PaymentMethod> {
        public JpaConverter() {
            super(PaymentMethod.class);
        }
    }
}
