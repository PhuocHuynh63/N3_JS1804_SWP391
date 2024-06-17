package com.n3.mebe.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiRespones> handleRuntimeException(RuntimeException e) {
        ApiRespones apiRespones = new ApiRespones();

        apiRespones.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiRespones.setMsg(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiRespones);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespones> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumkey = e.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALITE_MESSAGE_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumkey);
        }catch (IllegalArgumentException exception){

        }
        ApiRespones apiRespones = new ApiRespones();

        apiRespones.setCode(errorCode.getCode());
        apiRespones.setMsg(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiRespones);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespones> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiRespones apiRespones = new ApiRespones();

        apiRespones.setCode(errorCode.getCode());
        apiRespones.setMsg(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiRespones);
    }
}
