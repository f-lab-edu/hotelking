package com.hotelking.controller;

import com.hotelking.domain.hotel.Hotel;
import com.hotelking.dto.request.AddHotelRequest;
import com.hotelking.service.AddHotelService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelManagementController {

  private final AddHotelService addHotelService;

  public HotelManagementController(AddHotelService addHotelService) {
    this.addHotelService = addHotelService;
  }

  @PostMapping("/hotels")
  public ResponseEntity<?> registerHotel(@RequestBody @Valid AddHotelRequest addHotelRequest) {
    long hotelId = addHotelService.registerHotel(addHotelRequest.toAddHotel());
    return ResponseEntity.created(URI.create("/hotels/" + hotelId)).build();
  }
}
