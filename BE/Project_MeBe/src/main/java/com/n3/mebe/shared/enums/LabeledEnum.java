package com.n3.mebe.shared.enums;

/**
 * Enum có "label" - giá trị chuỗi lưu trong DB và gửi/nhận qua JSON
 * (vd. OrderStatus.PENDING_CONFIRMATION <-> "Chờ xác nhận").
 * Giữ nguyên label giúp API và dữ liệu cũ không bị thay đổi.
 */
public interface LabeledEnum {

    String getLabel();

    /**
     * Tìm enum theo label (không phân biệt hoa thường). Chuỗi rỗng/null -> null.
     */
    static <E extends Enum<E> & LabeledEnum> E fromLabel(Class<E> type, String label) {
        if (label == null || label.isBlank()) {
            return null;
        }
        String value = label.trim();
        for (E constant : type.getEnumConstants()) {
            if (constant.getLabel().equalsIgnoreCase(value) || constant.name().equalsIgnoreCase(value)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("Unknown " + type.getSimpleName() + ": " + label);
    }
}
