package com.n3.mebe.dto.response.user.tracking;


import com.n3.mebe.dto.response.user.UserAddressResponse;
import com.n3.mebe.dto.response.user.UserOrderResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class UserForTrackingResponse {

    private int userId;
    private List<UserOrderForTrackingResponse> order;

}
