package com.n3.mebe.voucher.dto.response;

import java.math.BigDecimal;


import lombok.Data;

import java.util.Date;
@Data
public class VoucherResponse {

    private int voucherId;
    private String voucherCode;
    private String discountType;
    private int discountValue;
    private String name;
    private BigDecimal cost;
    private float quantity;
    private BigDecimal minimumApply;
    private BigDecimal maxDiscount;
    private boolean isActive;
    private boolean isPublic;
    private Date startDate;
    private Date endDate;
    private Date createAt;
    private Date updateAt;

}
