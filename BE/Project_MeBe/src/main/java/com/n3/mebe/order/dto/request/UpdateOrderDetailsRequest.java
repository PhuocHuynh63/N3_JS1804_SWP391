package com.n3.mebe.order.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateOrderDetailsRequest {

    private int orderDetailId;

    private int orderId;
    private int inventoryId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal salePrice;
}
