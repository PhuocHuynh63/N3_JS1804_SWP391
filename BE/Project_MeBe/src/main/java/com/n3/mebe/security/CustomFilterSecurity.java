package com.n3.mebe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http: là nơi định nghĩa cái rule, tức là link nào được phép hoặc không được phép
        //csrf: là lợi dụng người dùng đăng nhập vào trang web hợp lệ để gửi những yêu cầu trái phép
        http.cors().disable()
                .csrf().disable() //Chống tấn công Token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Khai báo ứng dụng không được dùng session
                .and()
                .authorizeHttpRequests() //authorizeHttpRequests: Can thiệp người dùng truy cập
                .requestMatchers("/login/**", "user/**") //requestMatchers: Chỉ định đường dẫn người dùng không được truy cập
                                                //**: là tất cả
                .permitAll()        //permitAll: Không cần chứng thực
                                    //authenticated: Bắt chứng thực
                .anyRequest()       //anyRequest: Những request còn lại đều phải chứng thực
                .authenticated();

        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
