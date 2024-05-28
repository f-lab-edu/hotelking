package com.hotelking.application;

import com.hotelking.domain.hotel.RoomType;
import com.hotelking.domain.reservation.RoomReservation;
import com.hotelking.domain.reservation.RoomReservationRepository;
import com.hotelking.domain.schedule.ReservationType;
import com.hotelking.dto.ActiveUser;
import com.hotelking.dto.AddRoomReservationDto;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.query.RoomScheduleQuery;
import com.hotelking.query.RoomTypeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RoomReservationService {

  private final RoomReservationRepository roomReservationRepository;
  private final RoomScheduleQuery roomScheduleQuery;
  private final RoomTypeQuery roomTypeQuery;

  public RoomReservationService(RoomReservationRepository roomReservationRepository, RoomScheduleQuery roomScheduleQuery,
      RoomTypeQuery roomTypeQuery) {
    this.roomReservationRepository = roomReservationRepository;
    this.roomScheduleQuery = roomScheduleQuery;
    this.roomTypeQuery = roomTypeQuery;
  }

  @Transactional
  public void addOrder(ActiveUser activeUser, AddRoomReservationDto addRoomReservationDto) {
    checkRoomRevAvailability(addRoomReservationDto);
    final RoomType roomType = findRoomType(addRoomReservationDto);
    RoomReservation roomReservation = addRoomReservationDto.toRoomRevWithType(activeUser, roomType);
    roomReservationRepository.save(roomReservation);
  }

  private RoomType findRoomType(AddRoomReservationDto addRoomReservationDto) {
    return roomTypeQuery.findById(addRoomReservationDto.roomTypeId())
        .orElseThrow(() -> new HotelkingException(ErrorCode.NOT_FOUND_ROOM_TYPE, log));
  }

  public void checkRoomRevAvailability(AddRoomReservationDto addRoomReservationDto) {
    long emptyRoomScheduleCnt = countEmptyRoomSchedule(addRoomReservationDto);
    long orderedRoomCnt = countOrdered(addRoomReservationDto);
    checkAndThrowIfRoomOrderFull(emptyRoomScheduleCnt, orderedRoomCnt);
  }

  private void checkAndThrowIfRoomOrderFull(long emptyRoomCnt, long orderedRoomCnt) {
    if (emptyRoomCnt == orderedRoomCnt) {
      throw new HotelkingException(ErrorCode.ROOM_ORDER_FULL, log);
    }
  }

  private long countOrdered(AddRoomReservationDto addRoomReservationDto) {
    return roomReservationRepository.countOccupiedRoomOrderWithType(
        addRoomReservationDto.hotelId(),
        addRoomReservationDto.roomTypeId(),
        addRoomReservationDto.getStartOfDayOfCheckIn(),
        addRoomReservationDto.getLastOfDayOfCheckOut(),
        addRoomReservationDto.reservationType()
    );
  }

  private long countEmptyRoomSchedule(AddRoomReservationDto addRoomReservationDto) {
    long emptyScheduleRoomsCount = getEmptyScheduleRoomsCount(addRoomReservationDto);

    if (isScheduleFull(emptyScheduleRoomsCount)) {
      throw new HotelkingException(ErrorCode.ROOM_SCHEDULE_FULL, log);
    }

    return emptyScheduleRoomsCount;
  }

  private long getEmptyScheduleRoomsCount(AddRoomReservationDto addRoomReservationDto) {
    if (isDaeSilReservation(addRoomReservationDto)) {
      return countEmptyDaeSilScheduleRooms(addRoomReservationDto);
    } else {
      return countEmptyStayScheduleRooms(addRoomReservationDto);
    }
  }

  private boolean isDaeSilReservation(AddRoomReservationDto addRoomReservationDto) {
    return addRoomReservationDto.reservationType() == ReservationType.DAESIL;
  }

  private long countEmptyDaeSilScheduleRooms(AddRoomReservationDto addRoomReservationDto) {
    return roomScheduleQuery.countEmptyDasilSchduleRooms(
        addRoomReservationDto.hotelId(),
        addRoomReservationDto.roomTypeId(),
        addRoomReservationDto.checkIn().toLocalDate().atStartOfDay()
    );
  }

  private long countEmptyStayScheduleRooms(AddRoomReservationDto addRoomReservationDto) {
    return roomScheduleQuery.countEmptyStayScheduleRooms(
        addRoomReservationDto.hotelId(),
        addRoomReservationDto.roomTypeId(),
        addRoomReservationDto.checkIn(),
        addRoomReservationDto.checkOut(),
        addRoomReservationDto.getDayDiffer()
    );
  }

  private boolean isScheduleFull(long emptyScheduleRoomsCount) {
    return isEmpty(emptyScheduleRoomsCount);
  }

  private boolean isEmpty(long count) {
    return count == 0;
  }
}
