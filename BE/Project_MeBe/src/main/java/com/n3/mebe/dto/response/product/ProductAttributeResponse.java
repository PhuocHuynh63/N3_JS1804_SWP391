package com.n3.mebe.dto.response.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeResponse {

    private int paId;
    private String type;
    private String value;
}
