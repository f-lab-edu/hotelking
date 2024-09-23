package com.hotelking.api;

import com.hotelking.api.common.ApiResponse;
import com.hotelking.api.request.AddOrderRequest;
import com.hotelking.api.request.OrderResponse;
import com.hotelking.domain.booking.service.RoomReservationService;
import com.hotelking.domain.common.AppUser;
import com.hotelking.global.resolver.Login;
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
