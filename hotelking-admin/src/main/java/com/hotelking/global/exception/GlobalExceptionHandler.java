package com.hotelking.global.exception;

import com.hotelking.global.ApiResponse;
import com.hotelking.global.ErrorContent;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 모두 validation 과정에서 생길 수 있는 예외
  @ExceptionHandler({BindException.class, IllegalArgumentException.class})
  public ResponseEntity<ApiResponse<ErrorContent>> handleArgumentNotValidException(Exception e, HttpServletRequest request) {
    if (e instanceof BindException) {
      BindingResult bindingResult = ((BindException) e).getBindingResult();
      List<ErrorContent> errorContents = bindingResult.getAllErrors().stream()
          .map(it ->
              {
                FieldError fieldError = (FieldError) it;
                return ErrorContent.of(ErrorCode.findCode(fieldError.getDefaultMessage()),
                    fieldError.getField());
              }
          ).toList();
      log.warn("[validation error], exceptionClass = {}, uri = {}, errorContents = {}", e.getClass(), request.getRequestURI(), errorContents);
      return ResponseEntity.status(400).body(ApiResponse.error(errorContents));
    }

    ErrorContent errorContent = ErrorContent.from(ErrorCode.findCode(e.getMessage()));
    log.warn("[validation error], exceptionClass = {}, uri = {}, errorContent = {}", e.getClass(), request.getRequestURI(), errorContent);
    return ResponseEntity.status(400)
        .body(ApiResponse.error(errorContent));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
    ErrorContent errorContent = ErrorContent.from(ErrorCode.NOT_READABLE);
    log.warn("[ error], exceptionClass = {}, uri = {}, errorContent = {}", e.getClass(), request.getRequestURI(), errorContent);
    return ResponseEntity.status(400).body(ApiResponse.error(errorContent));
  }

}
