package com.hotelking.domain.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 설계 부족, state ?..
@Getter
@RequiredArgsConstructor
public enum RoomReservationState {
  PENDING(false), PAID(true), CANCEL(true);

  private final boolean isSchedulable;
}
