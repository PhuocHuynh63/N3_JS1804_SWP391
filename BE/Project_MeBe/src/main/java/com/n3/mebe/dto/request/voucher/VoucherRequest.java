package com.n3.mebe.dto.request.voucher;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class VoucherRequest {

    private String code;
    private String discountType;
    private int discountValue;
    private String name;
    private float cost;
    private float quantity;
    private float minimumApply;
    private float maxDiscount;
    private boolean isActive;
    private boolean isPublic;

    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy") //format date
    private Date endDate;
}
