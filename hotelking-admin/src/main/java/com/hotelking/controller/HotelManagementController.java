package com.hotelking.controller;

import com.hotelking.dto.request.AddHotelRequest;
import com.hotelking.response.ApiResponse;
import com.hotelking.service.AddHotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
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
