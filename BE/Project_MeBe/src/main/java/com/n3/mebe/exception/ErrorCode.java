package com.n3.mebe.exception;

import lombok.Getter;

public enum ErrorCode {

    PRODUCT_NO_EXIST(1000, "Product does not exist"),
    USER_EXIST(1001, "User existed"),
    PRODUCT_NAME_EXIST(1002, "Product name already exist"),
    NO_USER_EXIST(1003, "No User existed"),
    Address_NO_EXIST(1004, " Address does not exist"),
    INVALITE_MESSAGE_KEY(1005, "Invalid message key"),
    CATEGORY_EXIST(1006, "Category exist"),
    CATEGORY_NO_EXIST(1007, "Category no exist"),
    PRODUCT_SKU_NO_EXIST(1008, "Product sku does not exist"),
    PRODUCT_ATTRIBUTE_NO_EXIST(1009, "Product attribute does not exist"),
    PRODUCT_ATTRIBUTE_EXIST(1010, "Product attribute exist"),
    ORDER_NO_EXIST(1011, "Order does not exist"),
    INVENTORY_NO_EXIST(1012, "Inventory does not exist"),
    INVENTORY_QUANTITY_END(1013, "Inventory quantity out"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    ;


    @Getter
    private int code;


    @Getter
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
