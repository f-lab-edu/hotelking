package com.hotelking.controller;

import com.hotelking.application.AddHotelService;
import com.hotelking.dto.request.AddHotelRequest;
import com.hotelking.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hotel", description = "Manage hotel")
public class HotelManagementController {

  private final AddHotelService addHotelService;

  public HotelManagementController(AddHotelService addHotelService) {
    this.addHotelService = addHotelService;
  }

  @PostMapping("/admin/hotels")
  @Operation(summary = "새로운 호텔 등록", description = "새로운 호텔을 등록합니다.")
  public ApiResponse<Void> registerHotel(@RequestBody AddHotelRequest addHotelRequest) {
    addHotelRequest.validationCheck();
    addHotelService.registerHotel(addHotelRequest.toAddHotel());
    return ApiResponse.success();
  }
}
