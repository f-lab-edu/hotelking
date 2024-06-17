package com.hotelking.application;

import static com.hotelking.exception.ErrorCode.REV_ALREADY_SCHEDULED;
import static com.hotelking.exception.ErrorCode.REV_NOT_FOUND;
import static com.hotelking.exception.ErrorCode.REV_NO_SCHEDULE;

import com.hotelking.domain.reservation_schedule.ReservationSchedule;
import com.hotelking.domain.reservation_schedule.ReservationScheduleRepository;
import com.hotelking.domain.reservation.RoomReservation;
import com.hotelking.domain.reservation.RoomReservationRepository;
import com.hotelking.domain.schedule.RoomSchedule;
import com.hotelking.dto.request.AddReservationScheduleDto;
import com.hotelking.exception.HotelkingException;
import com.hotelking.query.ReservationScheduleQuery;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationScheduleService {

  private final ReservationScheduleQuery reservationScheduleQuery;
  private final ReservationScheduleRepository reservationScheduleRepository;
  private final RoomReservationRepository roomReservationRepository;

  public ReservationScheduleService(ReservationScheduleQuery reservationScheduleQuery,
      ReservationScheduleRepository reservationScheduleRepository,
      RoomReservationRepository roomReservationRepository) {
    this.reservationScheduleQuery = reservationScheduleQuery;
    this.reservationScheduleRepository = reservationScheduleRepository;
    this.roomReservationRepository = roomReservationRepository;
  }

  
  @Transactional
  public void registerReservationSchedule(AddReservationScheduleDto addReservationScheduleDto) {
    RoomReservation roomReservation = findSchedulableReservation(addReservationScheduleDto);
    List<RoomSchedule> findEmptySchedules = findEmptySchedulesForReservation(addReservationScheduleDto, roomReservation);
    findEmptySchedules.forEach(RoomSchedule::markReservedTrue);
    saveReservationSchedules(findEmptySchedules, roomReservation);
  }

  private void saveReservationSchedules(List<RoomSchedule> findEmptySchedules, RoomReservation roomReservation) {
    findEmptySchedules.forEach(roomSchedule -> {
      ReservationSchedule reservationSchedule = new ReservationSchedule(roomReservation, roomSchedule);
      reservationScheduleRepository.save(reservationSchedule);
    });
  }

  private List<RoomSchedule> findEmptySchedulesForReservation(
      AddReservationScheduleDto addReservationScheduleDto,
      RoomReservation roomReservation
  ) {
    final List<RoomSchedule> findEmptySchedules = reservationScheduleQuery.findSchedulesByRoomIdAndDateRange(
        addReservationScheduleDto.roomId(),
        roomReservation.getCheckIn(),
        roomReservation.getCheckOut(),
        roomReservation.getReservationType()
    );

    if (findEmptySchedules.isEmpty()) {
      throw new HotelkingException(REV_NO_SCHEDULE, null);
    }

    return findEmptySchedules;
  }

  private RoomReservation findSchedulableReservation(AddReservationScheduleDto addReservationScheduleDto) {
    RoomReservation roomReservation = findSchedule(addReservationScheduleDto);
    if (!roomReservation.isSchedulable()) {
      throw new HotelkingException(REV_ALREADY_SCHEDULED, null);
    }
    return roomReservation;
  }

  private RoomReservation findSchedule(AddReservationScheduleDto addReservationScheduleDto) {
    return roomReservationRepository.findById(
            addReservationScheduleDto.reservationId())
        .orElseThrow(() -> new HotelkingException(REV_NOT_FOUND, null));
  }

}
