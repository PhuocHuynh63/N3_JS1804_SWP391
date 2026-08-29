package com.n3.mebe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    // Tiêm (Inject) Service chứa logic Bucket4j + Redis của bạn vào đây
    // @Autowired private RateLimiterService rateLimiterService;

    private Redis

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr(); // Lấy IP người dùng

        // Gọi hàm kiểm tra từ Service
        // boolean isAllowed = rateLimiterService.tryConsume(clientIp);
        boolean isAllowed = false; // Giả lập hết Token

        if (!isAllowed) {
            // Hết Token -> Đuổi về ngay lập tức, không cho chạy vào OrderController
            response.setStatus(429); // HTTP 429 Too Many Requests
            response.getWriter().write("Spam detected! Xin vui lòng thử lại sau.");
            return false;
        }

        return true; // Còn Token -> Cho phép đi tiếp vào Controller
    }
}