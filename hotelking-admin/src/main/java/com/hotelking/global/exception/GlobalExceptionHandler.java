package com.hotelking.global.exception;

import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.ErrorContent;
import com.hotelking.exception.HotelkingException;
import com.hotelking.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HotelkingException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleHotelkingException(
      HotelkingException e,
      HttpServletRequest request
  ){
    ErrorCode ec = e.getErrorCode();
    printLog(request, ec);
    return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.error(ErrorContent.from(ec)));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponse<ErrorContent>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
    ErrorCode ec = ErrorCode.NOT_READABLE;
    printLog(request, ec);
    return ResponseEntity
        .status(ErrorCode.NOT_READABLE.getHttpStatus())
        .body(ApiResponse.error(ErrorContent.from(ec)));
  }

  private void printLog(HttpServletRequest request, ErrorCode ec) {
    if (request instanceof ContentCachingRequestWrapper wrapper) {
      String body = new String(wrapper.getContentAsByteArray());
      String params = wrapper.getQueryString();
      log.error("code={} | internal_msg={} | status={} | method={} | uri={} | params={} | body={}",
          ec.getCode(), ec.getInternalErrorMsg(), ec.getHttpStatus().value(), request.getMethod(),
          request.getRequestURI(), params, body);
    } else {
      log.error("code={} | internal_msg={} | status={} | method={} | uri={}",
          ec.getCode(), ec.getInternalErrorMsg(), ec.getHttpStatus().value(), request.getMethod(),
          request.getRequestURI());
    }
  }

}
