package com.test.meetingroom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.entity.MeetingRoom;
import com.test.meetingroom.exception.BookingConflictException;
import com.test.meetingroom.exception.BookingDataException;
import com.test.meetingroom.exception.MeetingRoomNotFoundException;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.repository.MeetingRoomRepository;
import com.test.meetingroom.service.BookingService;

@SpringBootTest
class BookingServiceTest {

  @Autowired
  BookingService bookingService;

  @Autowired
  private MeetingRoomRepository meetingRoomRepository;


  @Test
  void testBookingFlow() {
    List<MeetingRoom> meetingRooms = meetingRoomRepository.findMeetingRooms();
    assertEquals(0, bookingService.getBookings().size());
    bookingService.createBooking(new BookingRequest().roomId(1)
        .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 0), ZoneOffset.UTC))
        .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 0), ZoneOffset.UTC)));


    assertThrows(BookingDataException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))));

    assertThrows(BookingDataException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 5, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))));

    assertThrows(BookingConflictException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 30), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(2)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 30), ZoneOffset.UTC))));

    assertThrows(BookingConflictException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 45), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(3)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 45), ZoneOffset.UTC))));

    assertThrows(BookingConflictException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 15), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(4)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 15), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))));


    assertThrows(BookingConflictException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 12, 0), ZoneOffset.UTC))));

    assertThrows(MeetingRoomNotFoundException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(5)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 12, 0), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(4)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 5, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 5, 1, 12, 0), ZoneOffset.UTC))));

    assertEquals(2, bookingService.getBookingsByMeetingRoomId(4).size());

    Booking booking = bookingService.getBookingsByMeetingRoomId(4).get(0);

    bookingService.deleteBooking(booking.getId());

    assertEquals(1, bookingService.getBookingsByMeetingRoomId(4).size());
    assertEquals(4, bookingService.getBookings().size());
  }
}
