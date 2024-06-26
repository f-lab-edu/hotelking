package com.hotelking.application;

import com.hotelking.controller.RoomSearchParameter;
import com.hotelking.dto.response.RoomResponse;
import com.hotelking.dto.query.RoomDTO;
import com.hotelking.query.RoomScheduleQueryRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RoomSearchService {

  private final RoomScheduleQueryRepository roomScheduleQueryRepository;

  public RoomSearchService(RoomScheduleQueryRepository roomScheduleQueryRepository) {
    this.roomScheduleQueryRepository = roomScheduleQueryRepository;
  }

  public Paging<List<RoomResponse>> searchRooms(
      RoomSearchParameter roomSearchParam,
      Pageable pageable
  ) {
    final Page<RoomDTO> rooms = roomScheduleQueryRepository.searchRooms(
        roomSearchParam.types(),
        roomSearchParam.checkInDate(),
        roomSearchParam.checkOutDate(),
        roomSearchParam.reservationType(),
        roomSearchParam.allocationType(),
        roomSearchParam.reserved(),
        pageable
    );

    return Paging.from(
        RoomResponse.listOf(rooms.toList()),
        rooms.isLast(),
        rooms.getTotalElements()
    );
  }

}
