package com.hotelking.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hotelking.exception.ErrorContent;

@JsonInclude(value = Include.NON_NULL)
public record ApiResponse<T>(String result, T data, ErrorContent error) {

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data, null);
  }

  public static <T> ApiResponse<T> success() {
    return new ApiResponse<>("success", null, null);
  }

  public static <T> ApiResponse<T> error(ErrorContent error) {
    return new ApiResponse<>("error", null, error);
  }
}