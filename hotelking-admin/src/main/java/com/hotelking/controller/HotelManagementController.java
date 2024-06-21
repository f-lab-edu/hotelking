package com.hotelking.controller;

import com.hotelking.application.AddHotelService;
import com.hotelking.dto.request.AddHotelRequest;
import com.hotelking.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelManagementController {

  private final AddHotelService addHotelService;

  public HotelManagementController(AddHotelService addHotelService) {
    this.addHotelService = addHotelService;
  }

  @PostMapping("/admin/hotels")
  public ApiResponse<?> registerHotel(@RequestBody AddHotelRequest addHotelRequest) {
    addHotelRequest.validationCheck();
    addHotelService.registerHotel(addHotelRequest.toAddHotel());
    return ApiResponse.success();
  }
}
