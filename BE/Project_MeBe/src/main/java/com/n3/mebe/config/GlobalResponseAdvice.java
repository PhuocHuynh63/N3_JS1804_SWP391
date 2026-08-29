package com.n3.mebe.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n3.mebe.dto.response.ApiResponse;
import com.n3.mebe.dto.response.ResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collection;

@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    public GlobalResponseAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> parameterType = returnType.getParameterType();
        if (Void.TYPE.equals(parameterType) || Void.class.equals(parameterType)) {
            return false;
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse<?>) {
            return body;
        }

        if (body instanceof ResponseData responseData) {
            ApiResponse<Object> normalized = ApiResponse.builder()
                    .status(responseData.getStatus())
                    .message(responseData.getDescription())
                    .data(responseData.getData())
                    .pageSize(null)
                    .currentPage(null)
                    .build();
            return writeStringIfNeeded(normalized, selectedConverterType);
        }

        int status = HttpStatus.OK.value();
        if (response instanceof ServletServerHttpResponse servletResponse) {
            status = servletResponse.getServletResponse().getStatus();
        }

        Integer pageSize = null;
        Integer currentPage = null;
        Object data = body;

        if (body instanceof Page<?> pageBody) {
            pageSize = pageBody.getSize();
            currentPage = pageBody.getNumber() + 1;
            data = pageBody.getContent();
        } else if (body instanceof Collection<?> collectionBody) {
            pageSize = collectionBody.size();
            currentPage = 1;
        }

        ApiResponse<Object> wrapped = ApiResponse.builder()
                .status(status)
                .message("Success")
                .data(data)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .build();

        return writeStringIfNeeded(wrapped, selectedConverterType);
    }

    private Object writeStringIfNeeded(ApiResponse<Object> wrapped,
                                       Class<? extends HttpMessageConverter<?>> selectedConverterType) {
        if (StringHttpMessageConverter.class.isAssignableFrom(selectedConverterType)) {
            try {
                return objectMapper.writeValueAsString(wrapped);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Can not serialize response body", e);
            }
        }
        return wrapped;
    }
}
