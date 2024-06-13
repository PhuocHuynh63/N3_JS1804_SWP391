package com.n3.mebe.dto.request.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

    private int sizeAttributeId;
    private int colorAttributeId;
    private int quantity;
}
