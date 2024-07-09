package com.n3.mebe.dto.response.voucher;



import lombok.Data;

import java.util.Date;
@Data
public class VoucherResponse {

    private int voucherId;
    private String voucherCode;
    private String discountType;
    private int discountValue;
    private String name;
    private float cost;
    private float quantity;
    private float minimumApply;
    private float maxDiscount;
    private boolean isActive;
    private boolean isPublic;
    private Date startDate;
    private Date endDate;
    private Date createAt;
    private Date updateAt;

}
