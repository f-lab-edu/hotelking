package com.hotelking.api;

import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.dto.query.RoomAllocationType;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;


@Builder
public record RoomSearchCriteria(
    @Schema(description = "룸 할당 종류(하나 또는 복수)", implementation = RoomAllocationType.class, requiredMode = RequiredMode.REQUIRED) RoomAllocationType allocationType,
    @Schema(description = "룸 타입 리스트", type = "list<number>", example = "1, 2, 3", requiredMode = RequiredMode.REQUIRED) List<Long> types,
    @Schema(description = "체크인 날짜", format = "yyyy-MM-dd", example = "2024-06-05", requiredMode = RequiredMode.REQUIRED) LocalDate checkInDate,
    @Schema(description = "체크아웃 날짜", format = "yyyy-MM-dd", example = "2024-06-07", requiredMode = RequiredMode.REQUIRED) LocalDate checkOutDate,
    @Schema(description = "예약 타입(숙박, 대실, 전부)", implementation = ReservationType.class, requiredMode = RequiredMode.REQUIRED) ReservationType reservationType,
    @Schema(description = "예약유무", type = "boolean", defaultValue = "false", requiredMode = RequiredMode.NOT_REQUIRED) boolean reserved
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
