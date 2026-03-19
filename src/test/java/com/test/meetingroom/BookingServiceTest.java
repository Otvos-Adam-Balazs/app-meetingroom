package com.test.meetingroom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.test.meetingroom.api.exception.BookingConflictException;
import com.test.meetingroom.api.exception.BookingDataException;
import com.test.meetingroom.api.exception.MeetingRoomNotFoundException;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.service.BookingService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {

  @Autowired
  BookingService bookingService;

  private static final OffsetDateTime NOW = OffsetDateTime.now();

  @Test
  @Order(1)
  void testCreateValidBooking() {
    assertEquals(0, bookingService.getBookings().size());

    OffsetDateTime start = NOW.plusHours(2);
    OffsetDateTime end = start.plusHours(2);

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(1).startTime(start).endTime(end)));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(end)
            .endTime(end.plusHours(1))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(start.minusHours(1))
            .endTime(start)));


    // Create bookins for testBookingConflictSameRoom
    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(1).startTime(start.plusHours(3)).endTime(end.plusHours(3))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(1).startTime(start.plusHours(6)).endTime(end.plusHours(6))));

    assertEquals(5, bookingService.getBookings().size());
  }

  @Test
  @Order(2)
  void testInvalidBookingTimes() {
    OffsetDateTime start = NOW.plusHours(3);

    // start == end
    OffsetDateTime endSame = start;
    assertThrows(BookingDataException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1).startTime(start).endTime(endSame)));

    // start after end
    OffsetDateTime endBefore = start.minusHours(1);
    assertThrows(BookingDataException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1).startTime(start).endTime(endBefore)));

    // start in the past
    assertThrows(BookingDataException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1).startTime(start.minusHours(4)).endTime(endBefore)));
  }

  @Test
  @Order(3)
  void testBookingConflictSameRoom() {
    OffsetDateTime baseStart = NOW.plusHours(5);
    OffsetDateTime baseEnd = baseStart.plusHours(5);

    // Exact match with first booking
    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(baseStart)
            .endTime(baseEnd)));

    // New booking partially overlaps the end of the base booking
    // Base: baseStart -> baseEnd
    // New: baseStart + 30m -> baseEnd + 30m
    // Overlap: baseStart + 30m -> baseEnd
    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(baseStart.plusMinutes(30))
            .endTime(baseEnd.plusMinutes(30))));

    // New booking completely covers the base booking
    // Base: baseStart -> baseEnd
    // New: baseStart - 30m -> baseEnd + 60m
    // Overlap: baseStart -> baseEnd
    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(baseStart.minusMinutes(30))
            .endTime(baseEnd.plusMinutes(60))));

    // New booking fully within the base booking
    // Base: baseStart -> baseEnd
    // New: baseStart + 15m -> baseStart + 45m
    // Overlap: baseStart + 15m -> baseStart + 45m
    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(baseStart.plusMinutes(15))
            .endTime(baseStart.plusMinutes(45))));

    // New booking starts before the base booking and ends after it
    // Base: baseStart -> baseEnd
    // New: baseStart - 1h30m -> baseEnd + 1h
    // Overlap: baseStart -> baseEnd
    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(baseStart.minusHours(1).minusMinutes(30))
            .endTime(baseEnd.plusHours(1))));


    // New booking starts during the first booking and ends before the second booking finishes
    // Existing bookings (Order(1)): 2–4h and 8–10h
    // New booking: 2:30h -> 8:30h
    // Overlaps both existing bookings: 2:30–4h (first) and 8–8:30h (second)
    assertThrows(BookingConflictException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(1)
            .startTime(baseStart.plusMinutes(30))
            .endTime(baseStart.plusHours(6).plusMinutes(30))));
  }

  @Test
  @Order(4)
  void testBookingDifferentRoomsNoConflict() {

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(2)
            .startTime(NOW.plusHours(1))
            .endTime(NOW.plusHours(2))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(3)
            .startTime(NOW.plusHours(2))
            .endTime(NOW.plusHours(3))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(4)
            .startTime(NOW.plusHours(1))
            .endTime(NOW.plusHours(1).plusMinutes(30))));

    assertDoesNotThrow(() -> bookingService.createBooking(
        new BookingRequest().roomId(4)
            .startTime(NOW.plusHours(3))
            .endTime(NOW.plusHours(4))));
  }

  @Test
  @Order(5)
  void testBookingNonExistentRoom() {

    assertThrows(MeetingRoomNotFoundException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(5)
            .startTime(NOW.plusHours(1))
            .endTime(NOW.plusHours(4))));
  }

  @Test
  @Order(6)
  void testDeleteBooking() {
    List<Booking> bookings = bookingService.getBookingsByMeetingRoomId(4);
    assertEquals(2, bookings.size());

    bookingService.deleteBooking(bookings.get(0).getId());
    assertEquals(1, bookingService.getBookingsByMeetingRoomId(4).size());

    assertEquals(bookingService.getBookings().size(), 8);
  }

  @Test
  @Order(7)
  void testBookingWithNullValues() {

    assertThrows(NullPointerException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(null)
            .startTime(NOW.plusHours(1))
            .endTime(NOW.plusHours(3))));

    assertThrows(NullPointerException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(5)
            .startTime(null)
            .endTime(NOW.plusHours(3))));

    assertThrows(NullPointerException.class, () -> bookingService.createBooking(
        new BookingRequest().roomId(5)
            .startTime(NOW.plusHours(1))
            .endTime(null)));
  }
}
