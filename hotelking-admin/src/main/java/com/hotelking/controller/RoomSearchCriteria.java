package com.hotelking.controller;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.dto.query.RoomAllocationType;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

// swagger 표시 잘 되는 지 확인하기, 파라미터 표시될까? 객체로 ? 어떻게 되는 지 보자
// 살펴보자
// 객체로 받을 때 요청값 validation 잘하기
@Builder
public record RoomSearchCriteria(
    RoomAllocationType allocationType,
    List<Long> types,
    LocalDate checkInDate,
    LocalDate checkOutDate,
    ReservationType reservationType,
    boolean reserved
) implements RequestDtoCheckable {

  @Override
  public void validationCheck() {
    if (types == null || types.isEmpty()) {
      throw new HotelkingException(ErrorCode.JWT_TOKEN_NOT_VALID, null);
    }
  }

  public RoomSearchParameter toParam() {
    return RoomSearchParameter.builder()
        .allocationType(allocationType)
        .types(types)
        .checkInDate(checkInDate.atStartOfDay())
        .checkOutDate(checkOutDate.atStartOfDay())
        .reservationType(reservationType)
        .reserved(reserved)
        .build();
  }
}
