package com.hotelking.controller;

import com.hotelking.application.Paging;
import com.hotelking.application.RoomSearchService;
import com.hotelking.dto.response.ApiResponse;
import com.hotelking.dto.response.RoomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Search", description = "Search Room or Schedule")
public class RoomSearchController {

  private final RoomSearchService roomSearchService;

  public RoomSearchController(RoomSearchService roomSearchService) {
    this.roomSearchService = roomSearchService;
  }

  @GetMapping("/admin/search/rooms")
  public ApiResponse<Paging<List<RoomResponse>>> searchAvailableRooms(
      RoomSearchCriteria searchCriteria,
      @PageableDefault(size = 100) Pageable pageable
  ) {
    searchCriteria.validationCheck();
    Paging<List<RoomResponse>> listPaging = roomSearchService.searchRooms(searchCriteria.toParam(), pageable);
    return ApiResponse.success(listPaging);
  }
}
