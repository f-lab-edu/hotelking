package com.hotelking.controller;

import com.hotelking.application.SearchRoomService;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.search.SearchRoomParameter;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelRoomSearchController {

  private final SearchRoomService searchRoomService;

  public HotelRoomSearchController(SearchRoomService searchRoomService) {
    this.searchRoomService = searchRoomService;
  }

  @GetMapping("/search/room/availability")
  public ResponseEntity<?> findRemainRoomsWithPrices(
      @RequestParam long hotelId,
      @RequestParam LocalDate checkIn,
      @RequestParam(required = false) LocalDate checkOut,
      @RequestParam(required = false) ReservationType reservationType
  ) {
    var searchRoomParameter = new SearchRoomParameter(hotelId, checkIn, checkOut, reservationType);
    return ResponseEntity.ok(searchRoomService.findRemainRooms(searchRoomParameter));
  }
}
