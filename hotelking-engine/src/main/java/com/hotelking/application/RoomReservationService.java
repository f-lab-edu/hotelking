package com.hotelking.application;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.reservation.RoomReservation;
import com.hotelking.domain.reservation.RoomReservationRepository;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.ActiveUser;
import com.hotelking.dto.AddOrderDto;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.query.RoomScheduleQuery;
import com.hotelking.query.RoomTypeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {

  private final RoomReservationRepository roomReservationRepository;
  private final RoomScheduleQuery roomScheduleQuery;
  private final RoomTypeQuery roomTypeQuery;

  public OrderService(RoomReservationRepository roomReservationRepository, RoomScheduleQuery roomScheduleQuery,
      RoomTypeQuery roomTypeQuery) {
    this.roomReservationRepository = roomReservationRepository;
    this.roomScheduleQuery = roomScheduleQuery;
    this.roomTypeQuery = roomTypeQuery;
  }

  @Transactional
  public void addOrder(ActiveUser activeUser, AddOrderDto addOrderDto) {
    checkRoomRevAvailability(addOrderDto);
    final RoomType roomType = findRoomType(addOrderDto);
    RoomReservation roomReservation = addOrderDto.toRoomRevWithType(activeUser, roomType);
    roomReservationRepository.save(roomReservation);
  }

  private RoomType findRoomType(AddOrderDto addOrderDto) {
    return roomTypeQuery.findById(addOrderDto.roomTypeId())
        .orElseThrow(() -> new HotelkingException(ErrorCode.NOT_FOUND_ROOM_TYPE, log));
  }

  public void checkRoomRevAvailability(AddOrderDto addOrderDto) {
    long emptyRoomScheduleCnt = countEmptyRoomSchedule(addOrderDto);
    long orderedRoomCnt = countOrdered(addOrderDto);
    checkAndThrowIfRoomOrderFull(emptyRoomScheduleCnt, orderedRoomCnt);
  }

  private void checkAndThrowIfRoomOrderFull(long emptyRoomCnt, long orderedRoomCnt) {
    if (emptyRoomCnt == orderedRoomCnt) {
      throw new HotelkingException(ErrorCode.ROOM_ORDER_FULL, log);
    }
  }

  private long countOrdered(AddOrderDto addOrderDto) {
    return roomReservationRepository.countOccupiedRoomOrderWithType(
        addOrderDto.hotelId(),
        addOrderDto.roomTypeId(),
        addOrderDto.checkIn().toLocalDate().atStartOfDay(),
        addOrderDto.checkOut().toLocalDate().atStartOfDay().plusDays(1L),
        addOrderDto.reservationType()
    );
  }

  private long countEmptyRoomSchedule(AddOrderDto addOrderDto) {
    long emptyScheduleRoomsCount = getEmptyScheduleRoomsCount(addOrderDto);

    if (isScheduleFull(emptyScheduleRoomsCount)) {
      throw new HotelkingException(ErrorCode.ROOM_SCHEDULE_FULL, log);
    }

    return emptyScheduleRoomsCount;
  }

  private long getEmptyScheduleRoomsCount(AddOrderDto addOrderDto) {
    if (isDaeSilReservation(addOrderDto)) {
      return countEmptyDaeSilScheduleRooms(addOrderDto);
    } else {
      return countEmptyStayScheduleRooms(addOrderDto);
    }
  }

  private boolean isDaeSilReservation(AddOrderDto addOrderDto) {
    return addOrderDto.reservationType() == ReservationType.DAESIL;
  }

  private long countEmptyDaeSilScheduleRooms(AddOrderDto addOrderDto) {
    return roomScheduleQuery.countEmptyDasilSchduleRooms(
        addOrderDto.hotelId(),
        addOrderDto.roomTypeId(),
        addOrderDto.checkIn().toLocalDate().atStartOfDay()
    );
  }

  private long countEmptyStayScheduleRooms(AddOrderDto addOrderDto) {
    return roomScheduleQuery.countEmptyStayScheduleRooms(
        addOrderDto.hotelId(),
        addOrderDto.roomTypeId(),
        addOrderDto.checkIn(),
        addOrderDto.checkOut(),
        addOrderDto.getDayDiffer()
    );
  }

  private boolean isScheduleFull(long emptyScheduleRoomsCount) {
    return isEmpty(emptyScheduleRoomsCount);
  }

  private boolean isEmpty(long count) {
    return count == 0;
  }
}
