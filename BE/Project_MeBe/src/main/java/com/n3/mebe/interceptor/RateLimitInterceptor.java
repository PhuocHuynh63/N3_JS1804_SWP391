package com.n3.mebe.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

/**
 * Rate limit đơn giản theo IP bằng Redis (fixed window).
 * Mỗi IP được gọi tối đa MAX_REQUESTS lần trong WINDOW.
 * Chưa được đăng ký vào WebMvcConfigurer - đăng ký khi cần dùng.
 */
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final int MAX_REQUESTS = 20;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = "rate_limit:" + request.getRemoteAddr();

        Long count = stringRedisTemplate.opsForValue().increment(key);
        if (count != null && count == 1) {
            // Request đầu tiên trong cửa sổ -> đặt thời gian hết hạn
            stringRedisTemplate.expire(key, WINDOW);
        }

        if (count != null && count > MAX_REQUESTS) {
            response.setStatus(429); // HTTP 429 Too Many Requests
            response.getWriter().write("Spam detected! Xin vui lòng thử lại sau.");
            return false;
        }

        return true;
    }
}
