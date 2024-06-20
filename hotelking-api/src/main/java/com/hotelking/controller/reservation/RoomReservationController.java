package com.hotelking.controller.reservation;

import com.hotelking.application.RoomReservationService;
import com.hotelking.dto.AppUser;
import com.hotelking.dto.request.AddOrderRequest;
import com.hotelking.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomReservationController {

  private final RoomReservationService roomReservationService;

  public RoomReservationController(RoomReservationService roomReservationService) {
    this.roomReservationService = roomReservationService;
  }

  @PostMapping("/reservation")
  public ApiResponse<?> doReservation(@RequestBody AddOrderRequest addOrderRequest) {
    addOrderRequest.validationCheck();
    // TODO ActiveUser 인증객체로 부터 가져오기
    AppUser appUser = new AppUser(1L);
    roomReservationService.addOrder(appUser, addOrderRequest.toOrderDto());
    return ApiResponse.success();
  }

}
