package com.n3.mebe.order.dto.response;

import java.math.BigDecimal;

import com.n3.mebe.order.dto.response.OrderResponse;
import com.n3.mebe.catalog.entity.Product;
import lombok.Data;

@Data
public class OrderDetailsResponse {

    private int odId;

    private OrderResponse order;
    private Product product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal salePrice;
}
