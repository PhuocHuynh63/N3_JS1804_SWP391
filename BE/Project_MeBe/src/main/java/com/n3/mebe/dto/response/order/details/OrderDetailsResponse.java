package com.n3.mebe.dto.response.order.details;


import com.n3.mebe.entity.Inventory;
import com.n3.mebe.entity.Order;
import com.n3.mebe.entity.Product;
import lombok.Data;

@Data
public class OrderDetailsResponse {

    private int odId;

    private Order order;
    private Product product;
    private Inventory inventory;

    private int quantity;
    private float price;
    private float salePrice;
}
