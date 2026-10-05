package com.n3.mebe.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Cấu hình VNPay, đọc từ application.properties (prefix "vnpay").
 */
@ConfigurationProperties(prefix = "vnpay")
public record VNPayProperties(
        String tmnCode,
        String secretKey,
        String payUrl,
        String returnUrl,
        String version,
        String command
) {
}
