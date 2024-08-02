package com.hotelking.controller.reservation;

import com.hotelking.application.RoomReservationService;
import com.hotelking.auth.Login;
import com.hotelking.dto.AppUser;
import com.hotelking.dto.request.AddOrderRequest;
import com.hotelking.dto.request.OrderResponse;
import com.hotelking.dto.response.ApiResponse;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomReservationController {

  private final RoomReservationService roomReservationService;

  public RoomReservationController(RoomReservationService roomReservationService) {
    this.roomReservationService = roomReservationService;
  }

  @PostMapping("/order")
  public ApiResponse<OrderResponse> createOrder(
      @Login AppUser appUser,
      @RequestBody AddOrderRequest addOrderRequest
  ) {
    addOrderRequest.validationCheck();
    UUID orderKey = roomReservationService.addOrder(appUser, addOrderRequest.toOrderDto());
    return ApiResponse.success(new OrderResponse(orderKey));
  }

}
