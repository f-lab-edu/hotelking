package com.hotelking.admin.api;

import com.hotelking.admin.service.AddHotelService;
import com.hotelking.api.common.ApiResponse;
import com.hotelking.api.dto.request.AddHotelRequest;
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
