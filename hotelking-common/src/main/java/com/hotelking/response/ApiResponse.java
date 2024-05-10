package com.hotelking.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hotelking.exception.ErrorContent;

@JsonInclude(value = Include.NON_NULL)
public record ApiResponse<T>(String result, T data, ErrorContent error) {

  /**
   *
   * ex)
   * {
   *       "result" : "success",
   *       "data": {
   *         "hotelId": 1L
   *       }
   *     }
   */
  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data, null);
  }


  /**
   *
   * ex)
   * {
   *       "result" : "error",
   *       "error" : {
   *         "code" : "A001",
   *         "message": "호텔 이름은 필수입니다."
   *       }
   *     }
   */
  public static <T> ApiResponse<T> error(ErrorContent error) {
    return new ApiResponse<>("error", null, error);
  }
}