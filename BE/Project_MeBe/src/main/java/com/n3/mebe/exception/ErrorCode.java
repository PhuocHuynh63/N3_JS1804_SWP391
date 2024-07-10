package com.n3.mebe.exception;

import lombok.Getter;

public enum ErrorCode {

    PRODUCT_NO_EXIST(1000, "Product does not exist"),
    USER_EXIST(1001, "User existed"),
    PRODUCT_NAME_EXIST(1002, "Product name already exist"),
    NO_USER_EXIST(1003, "No User existed"),
    Address_NO_EXIST(1004, " Address does not exist"),
    INVALIDATE_MESSAGE_KEY(1005, "Invalid message key"),
    CATEGORY_EXIST(1006, "Thể loại đã tồn tại"),
    CATEGORY_NO_EXIST(1007, "Thể loại không tồn tại"),
    PRODUCT_SKU_NO_EXIST(1008, "Product sku does not exist"),
    PRODUCT_ATTRIBUTE_NO_EXIST(1009, "Product attribute does not exist"),
    PRODUCT_ATTRIBUTE_EXIST(1010, "Product attribute exist"),
    ORDER_NO_EXIST(1011, "Đơn hàng không tồn tại"),
    ORDER_NOT_CANCEL(1014, "Order cannot be cancelled"),
    INVENTORY_NO_EXIST(1012, "Inventory does not exist"),
    PRODUCT_QUANTITY_END(1013, "Product quantity out"),
    REVIEW_NOT_FOUND(1015, "Review does not exist"),
    ORDER_DETAILS_NO_EXIST(1016, "Order details does not exist"),
    EMAIL_EXIST(1017, "Email đã tồn tại"),
    USERNAME_EXIST(1018, "Username đã tồn tại"),
    Address_EXIST(1019, " Address đã tồn tại "),
    PHONE_NUMBER_EXIST(1020, " Số điện thoại đã tồn tại"),
    PRODUCT_QUANTITY_NOT_OUT(1021, " Sản phẩm đã hết hàng"),
    SUB_CATEGORY_NO_EXIST(1022, "Id không tồn tại"),
    VOUCHER_NO_EXIST(1023, "Id voucher không tồn tại"),
    VOUCHER_CODE_EXIST(1024, "Code voucher đã tồn tại"),
    VOUCHER_CODE_NO_EXIST(1025, "Code voucher không tồn tại"),
    VOUCHER_USED(1026, "Mã khuyến mãi đã được sử dụng"),
    BAN_ACCOUNT(1027, "Tài khoản đã bị khóa"),
    EMAIL_NO_EXIST(1028, "Email không tồn tại trên hệ thống"),
    SUB_CATEGORY_ID_NO_EXIST(1029, "ID không tồn tại"),
    SUB_CATEGORY_NAME_EXIST(1030, "Name SubCate đã tồn tại"),
    SUB_CATEGORY_SLUG_EXIST(1031, "Slug SubCate đã tồn tại"),

    EMAIL_HAVE_ACCOUNT(1032, "Email đã có tài khoản đăng ký, vui lòng chuyển sang trang nhập hoặc nhập email mới"),


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
