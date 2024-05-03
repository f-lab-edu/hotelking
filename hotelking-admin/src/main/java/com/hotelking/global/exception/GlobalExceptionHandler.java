package com.hotelking.global.exception;

import com.hotelking.global.ApiResponse;
import com.hotelking.global.ErrorContent;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ApiResponse<ErrorContent>> handleArgumentNotValidException(Exception e) {

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
      return ResponseEntity.status(400).body(ApiResponse.error(errorContents));
    }

    return ResponseEntity.status(400)
        .body(ApiResponse.error(ErrorContent.from(ErrorCode.findCode(e.getMessage()))));
  }

}
