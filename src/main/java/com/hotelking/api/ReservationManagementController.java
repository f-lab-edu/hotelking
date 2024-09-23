package com.hotelking.api;

import com.hotelking.application.ReservationScheduleService;
import com.hotelking.dto.request.AddReservationScheduleRequest;
import com.hotelking.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Reservation", description = "Manage Client Reservation")
public class ReservationManagementController {

  private final ReservationScheduleService reservationService;

  public ReservationManagementController(ReservationScheduleService reservationScheduleService) {
    this.reservationService = reservationScheduleService;
  }

  @PostMapping("/admin/reservation/register-schedule")
  @Operation(summary = "예약 - 룸 스케줄 매칭", description = "사용자가 예약한 건과 방 스케줄을 매칭합니다.")
  public ApiResponse<Void> addReservationSchedule(
      @RequestBody AddReservationScheduleRequest addReservationScheduleRequest
  ) {
    reservationService.registerReservationSchedule(addReservationScheduleRequest.toDto());
    return ApiResponse.success();
  }
}
