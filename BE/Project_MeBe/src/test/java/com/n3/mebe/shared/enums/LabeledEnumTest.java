package com.n3.mebe.shared.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3.mebe.order.entity.OrderStatus;
import com.n3.mebe.user.entity.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LabeledEnumTest {

    @Test
    void fromLabel_matchesLabelIgnoringCase() {
        assertThat(OrderStatus.fromLabel("Chờ xác nhận")).isEqualTo(OrderStatus.PENDING_CONFIRMATION);
        assertThat(UserStatus.fromLabel("BAN")).isEqualTo(UserStatus.BANNED);
    }

    @Test
    void fromLabel_acceptsEnumName() {
        assertThat(OrderStatus.fromLabel("DELIVERED")).isEqualTo(OrderStatus.DELIVERED);
    }

    @Test
    void fromLabel_returnsNullForBlank() {
        assertThat(OrderStatus.fromLabel(null)).isNull();
        assertThat(OrderStatus.fromLabel("  ")).isNull();
    }

    @Test
    void fromLabel_throwsForUnknownValue() {
        assertThatThrownBy(() -> OrderStatus.fromLabel("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("OrderStatus");
    }

    @Test
    void jpaConverter_storesLabel() {
        OrderStatus.JpaConverter converter = new OrderStatus.JpaConverter();

        assertThat(converter.convertToDatabaseColumn(OrderStatus.CANCELLED)).isEqualTo("Đã hủy");
        assertThat(converter.convertToEntityAttribute("Đã hủy")).isEqualTo(OrderStatus.CANCELLED);
        assertThat(converter.convertToDatabaseColumn(null)).isNull();
    }

    @Test
    void json_usesLabel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        assertThat(objectMapper.writeValueAsString(OrderStatus.DELIVERED)).isEqualTo("\"Đã giao\"");
        assertThat(objectMapper.readValue("\"Đang giao\"", OrderStatus.class)).isEqualTo(OrderStatus.SHIPPING);
    }
}
