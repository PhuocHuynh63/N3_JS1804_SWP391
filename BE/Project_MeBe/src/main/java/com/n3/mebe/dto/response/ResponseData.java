package com.n3.mebe.dto.response;

import lombok.Data;

/**
 *    {
 *        status: 200
 *        description:
 *        data:
 *    }
 */
@Data
public class ResponseData {
    private int status = 200;
    private boolean isSuccess = true;
    private String description;
    private Object data;
}
