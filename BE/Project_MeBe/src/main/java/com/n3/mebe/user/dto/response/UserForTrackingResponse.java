package com.n3.mebe.user.dto.response;


import com.n3.mebe.user.dto.response.UserAddressResponse;
import com.n3.mebe.user.dto.response.UserOrderResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class UserForTrackingResponse {

    private int userId;
    private List<UserOrderForTrackingResponse> order;

}
