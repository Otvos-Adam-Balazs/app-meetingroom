package com.test.meetingroom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.service.BookingService;

@SpringBootTest
class BookingServiceTest {

  @Autowired
  BookingService bookingService;


  @Test
  void testBookingFlow() {
    BookingDto booking = bookingService.createBooking(new BookingRequest().roomId(1)
        .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 0), ZoneOffset.UTC))
        .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 0), ZoneOffset.UTC)));

    assertThrows(RuntimeException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 30), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(2)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 9, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 30), ZoneOffset.UTC))));

    assertThrows(RuntimeException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 45), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(3)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 11, 45), ZoneOffset.UTC))));

    assertThrows(RuntimeException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 15), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(4)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 15), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 10, 45), ZoneOffset.UTC))));


    assertThrows(RuntimeException.class,
        () -> bookingService.createBooking(new BookingRequest().roomId(1)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 12, 0), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(5)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 4, 1, 12, 0), ZoneOffset.UTC))));

    assertDoesNotThrow(
        () -> bookingService.createBooking(new BookingRequest().roomId(5)
            .startTime(OffsetDateTime.of(LocalDateTime.of(2026, 5, 1, 8, 0), ZoneOffset.UTC))
            .endTime(OffsetDateTime.of(LocalDateTime.of(2026, 5, 1, 12, 0), ZoneOffset.UTC))));

    assertEquals(2, bookingService.bookings(5).size());

    BookingDto bookingDto = bookingService.bookings(5).get(0);

    bookingService.deleteBooking(bookingDto.getId());

    assertEquals(1, bookingService.bookings(5).size());
  }
}
