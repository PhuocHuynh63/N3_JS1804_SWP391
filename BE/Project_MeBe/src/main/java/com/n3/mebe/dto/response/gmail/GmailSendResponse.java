package com.n3.mebe.dto.response.gmail;

import lombok.Data;

import java.util.Map;

@Data
public class GmailSendResponse {
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> props;
}
