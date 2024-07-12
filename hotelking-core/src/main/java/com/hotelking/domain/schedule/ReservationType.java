package com.hotelking.domain.schedule;

import static com.hotelking.exception.ErrorCode.SEARCH_TYPE_REQUIRED;

import com.hotelking.exception.HotelkingException;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationType {
  STAY(true), RENT(false), BOTH(false);
  private final boolean isStay;

  public static ReservationType of(String type) {
    if (type == null) {
      throw new HotelkingException(SEARCH_TYPE_REQUIRED, null);
    }
    return Arrays.stream(ReservationType.values())
        .filter(it -> it.name().equals(type))
        .findFirst()
        .orElse(BOTH);
  }
}
