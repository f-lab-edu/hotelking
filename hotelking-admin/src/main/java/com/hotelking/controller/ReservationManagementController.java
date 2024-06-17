package com.hotelking.controller;

import com.hotelking.application.ReservationScheduleService;
import com.hotelking.dto.request.AddReservationScheduleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationManagementController {

  private final ReservationScheduleService reservationService;

  public ReservationManagementController(ReservationScheduleService reservationScheduleService) {
    this.reservationService = reservationScheduleService;
  }

  @PostMapping("/admin/reservation/register-schedule")
  public ResponseEntity<Void> addReservationSchedule(
      @RequestBody AddReservationScheduleRequest addReservationScheduleRequest
  ) {
    reservationService.registerReservationSchedule(addReservationScheduleRequest.toDto());
    return ResponseEntity.ok().build();
  }
}