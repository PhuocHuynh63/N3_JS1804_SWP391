package com.n3.mebe.dto.response.user.tracking;


import com.n3.mebe.entity.Voucher;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserOrderForTrackingResponse {
    private int orderId;
    private String status;
    private Date createdAt;
    List<UserOrderDetailsResponse> items;

}
