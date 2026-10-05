package com.n3.mebe.shared.enums;

import jakarta.persistence.AttributeConverter;

/**
 * JPA converter dùng chung: lưu label của enum xuống DB và đọc ngược lại.
 * Mỗi enum tạo một class con, vd. {@code class JpaConverter extends LabeledEnumConverter<OrderStatus>}.
 */
public abstract class LabeledEnumConverter<E extends Enum<E> & LabeledEnum> implements AttributeConverter<E, String> {

    private final Class<E> type;

    protected LabeledEnumConverter(Class<E> type) {
        this.type = type;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.getLabel();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return LabeledEnum.fromLabel(type, dbData);
    }
}
