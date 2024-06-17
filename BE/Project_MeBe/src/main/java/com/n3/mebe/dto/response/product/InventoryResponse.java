package com.n3.mebe.dto.response.product;


import com.n3.mebe.entity.Product;
import com.n3.mebe.entity.ProductAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    private int productSkuId;
    private Product product;
    private ProductAttribute sizeAttributeId;
    private ProductAttribute colorAttributeId;
    private int quantity;
}
