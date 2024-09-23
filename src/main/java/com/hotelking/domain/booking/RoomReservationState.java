package com.hotelking.domain.booking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 설계 부족, state ?..
@Getter
@RequiredArgsConstructor
public enum RoomReservationState {
  PENDING(true), PAID(false), CANCEL(false);

  private final boolean isSchedulable;
}
