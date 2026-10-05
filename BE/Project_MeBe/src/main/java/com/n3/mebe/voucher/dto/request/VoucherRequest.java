package com.n3.mebe.voucher.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class VoucherRequest {

    private String code;
    private String discountType;
    private int discountValue;
    private String name;
    private BigDecimal cost;
    private float quantity;
    private BigDecimal minimumApply;
    private BigDecimal maxDiscount;
    private boolean isActive;
    private boolean isPublic;

    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date endDate;
}
