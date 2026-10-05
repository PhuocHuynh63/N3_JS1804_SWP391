package com.n3.mebe.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Vai trò người dùng
 */
public enum UserRole implements LabeledEnum {
    ADMIN("admin"),
    STAFF("staff"),
    MEMBER("member"),
    GUEST("guest");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static UserRole fromLabel(String label) {
        return LabeledEnum.fromLabel(UserRole.class, label);
    }

    public static class JpaConverter extends LabeledEnumConverter<UserRole> {
        public JpaConverter() {
            super(UserRole.class);
        }
    }
}
