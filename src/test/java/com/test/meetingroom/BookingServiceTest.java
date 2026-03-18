package com.test.meetingroom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.exception.BookingConflictException;
import com.test.meetingroom.exception.BookingDataException;
import com.test.meetingroom.exception.MeetingRoomNotFoundException;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.repository.MeetingRoomRepository;
import com.test.meetingroom.service.BookingService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {

  @Autowired
  BookingService bookingService;

  @Autowired
  private MeetingRoomRepository meetingRoomRepository;

  @Test
  @Order(1)
  void testCreateValidBooking() {
    assertEquals(0, bookingService.getBookings().size());

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 0), ZoneOffset.UTC))));

    assertEquals(1, bookingService.getBookings().size());
  }

  @Test
  @Order(2)
  void testInvalidBookingTimes() {
    assertThrows(BookingDataException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))));

    assertThrows(BookingDataException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 5, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))));
  }

  @Test
  @Order(3)
  void testBookingConflictSameRoom() {

    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 30), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 30), ZoneOffset.UTC))));

    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 12, 0), ZoneOffset.UTC))));

    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 15), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))));

    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 45), ZoneOffset.UTC))));
  }

  @Test
  @Order(4)
  void testBookingDifferentRoomsNoConflict() {
    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(2)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 30), ZoneOffset.UTC))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(3)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 45), ZoneOffset.UTC))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(4)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 15), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(4)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 13, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 14, 0), ZoneOffset.UTC))));
  }

  @Test
  @Order(5)
  void testBookingNonExistentRoom() {
    assertThrows(MeetingRoomNotFoundException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(5)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 12, 0), ZoneOffset.UTC))));
  }

  @Test
  @Order(6)
  void testDeleteBooking() {

    List<Booking> bookings = bookingService.getBookingsByMeetingRoomId(4);
    assertEquals(2, bookings.size());

    bookingService.deleteBooking((bookings.get(0)).getId());
    assertEquals(1, bookingService.getBookingsByMeetingRoomId(4).size());

    assertEquals(bookingService.getBookings().size(), 4);
  }
}
