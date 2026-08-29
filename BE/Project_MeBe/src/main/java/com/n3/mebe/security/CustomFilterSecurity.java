package com.n3.mebe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    CustomJwtFilter customJwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CHỈ đưa các API không cần đăng nhập vào đây (Xem sản phẩm, Đăng nhập, Quên mật khẩu)
        String[] publicEndpoints = {
                "/login/**",
                "/forgot_password/**",
                "/signingoogle",
                "/oauth2/**",
                "/category/**",
                "/sub_category/**",
                "/product/**",
                "/voucher/**"
        };

        http.cors(Customizer.withDefaults()) // Đã bật CORS chuẩn
                // Sử dụng Lambda DSL (Cú pháp chuẩn của Spring Boot 3)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // Mở cửa tự do cho danh sách Public
                        .requestMatchers(publicEndpoints).permitAll()

                        // Mở cửa riêng cho hàm Đăng ký user (Nếu có)
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()

                        // 2. KHÓA CHẶT các API nhạy cảm - Bắt buộc phải có Token JWT hợp lệ mới được vào
                        .requestMatchers("/order/**", "/order_details/**", "/address/**", "/api/payment/**", "/wishlist/**", "/user/**").authenticated()

                        // (Tùy chọn) Phân quyền: Chỉ Admin mới được quyền thêm/sửa/xóa Category
                        // .requestMatchers(HttpMethod.POST, "/category/**").hasRole("ADMIN")

                        // Bất kỳ Request nào khác đi lạc vào cũng đều phải bắt chứng thực
                        .anyRequest().authenticated()
                );

        // Cấu hình OAuth2 login nếu cần
        // http.oauth2Login(Customizer.withDefaults());

        // Đặt Filter kiểm tra JWT lên trước Filter mặc định của Spring
        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}