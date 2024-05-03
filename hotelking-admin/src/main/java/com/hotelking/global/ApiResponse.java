package com.hotelking.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
public record ApiResponse<T>(String result, T data, ErrorContent error, List<ErrorContent> errors) {

  /**
   *
   * ex)
   * {
   *       "result" : "success",
   *       "data": {
   *         "hotelId": 123L
   *       }
   *     }
   */
  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data, null, null);
  }


  /**
   *
   * ex)
   * {
   *       "result" : "error",
   *       "error" : {
   *         "code" : "ADMIN_HOTEL_01",
   *         "message": "호텔 이름은 필수입니다."
   *       }
   *     }
   */
  public static <T> ApiResponse<T> error(ErrorContent error) {
    return new ApiResponse<>("error", null, error, null);
  }

  public static <T> ApiResponse<T> error(List<ErrorContent> errors) {
    return new ApiResponse<>("error", null, null, errors);
  }
}
