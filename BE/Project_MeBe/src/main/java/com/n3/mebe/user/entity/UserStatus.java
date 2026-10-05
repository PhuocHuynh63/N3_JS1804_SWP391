package com.n3.mebe.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.n3.mebe.shared.enums.LabeledEnum;
import com.n3.mebe.shared.enums.LabeledEnumConverter;

/**
 * Trạng thái tài khoản
 */
public enum UserStatus implements LabeledEnum {
    ACTIVE("active"),
    BANNED("ban"),
    FORGOT_PASSWORD("forgot");

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }

    @Override
    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static UserStatus fromLabel(String label) {
        return LabeledEnum.fromLabel(UserStatus.class, label);
    }

    public static class JpaConverter extends LabeledEnumConverter<UserStatus> {
        public JpaConverter() {
            super(UserStatus.class);
        }
    }
}
