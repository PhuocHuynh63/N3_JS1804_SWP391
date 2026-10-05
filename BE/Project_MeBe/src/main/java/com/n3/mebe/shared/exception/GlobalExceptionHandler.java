package com.n3.mebe.shared.exception;


import com.n3.mebe.shared.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException e) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .data(null)
                .pageSize(null)
                .currentPage(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
    // JSON sai định dạng hoặc giá trị enum không hợp lệ (vd. status = "abc")
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    ResponseEntity<ApiResponse<Object>> handleBadRequest(Exception e) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Dữ liệu gửi lên không hợp lệ")
                .data(null)
                .pageSize(null)
                .currentPage(null)
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumkey = e.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALIDATE_MESSAGE_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumkey);
        }catch (IllegalArgumentException exception){

        }
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorCode.getMessage())
                .data(null)
                .pageSize(null)
                .currentPage(null)
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errorCode.getMessage())
                .data(null)
                .pageSize(null)
                .currentPage(null)
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
