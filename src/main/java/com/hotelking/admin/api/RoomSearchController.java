package com.hotelking.admin.api;

import com.hotelking.application.Paging;
import com.hotelking.application.RoomSearchService;
import com.hotelking.dto.response.ApiResponse;
import com.hotelking.dto.response.RoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springdoc.core.annotations.ParameterObject;
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
  @Operation(summary = "이용가능한 룸을 조회", description = "검색 날짜에 비어있는 룸을 조회한다.")
  public ApiResponse<Paging<List<RoomResponse>>> searchAvailableRooms(
      @ParameterObject RoomSearchCriteria searchCriteria,
      @ParameterObject @PageableDefault(size = 100) Pageable pageable
  ) {
    searchCriteria.validationCheck();
    Paging<List<RoomResponse>> listPaging = roomSearchService.searchRooms(searchCriteria.toParam(), pageable);
    return ApiResponse.success(listPaging);
  }
}
