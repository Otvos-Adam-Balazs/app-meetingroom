package com.test.meetingroom.api.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.test.meetingroom.api.BookingApiDelegate;
import com.test.meetingroom.api.mapper.BookingMapper;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.service.BookingService;

@Service
public class BookingApiDelegateImpl implements BookingApiDelegate {

  @Autowired
  private BookingService bookingService;

  @Override
  public ResponseEntity<List<BookingDto>> getBookingsByMeetingRoom(Integer roomId) {
    List<BookingDto> bookings = bookingService.getBookingsByMeetingRoomId(roomId).stream()
        .map(booking -> BookingMapper.toDto(booking)).toList();
    return ResponseEntity.ok(bookings);
  }

  @Override
  public ResponseEntity<List<BookingDto>> getBookings() {
    List<BookingDto> bookings = bookingService.getBookings().stream()
        .map(booking -> BookingMapper.toDto(booking)).toList();
    return ResponseEntity.ok(bookings);
  }



  @Override
  public ResponseEntity<BookingDto> createBooking(BookingRequest bookingRequest) {
    Booking booking = bookingService.createBooking(bookingRequest);
    BookingDto bookingDto = BookingMapper.toDto(booking);
    return ResponseEntity.status(HttpStatus.CREATED).body(bookingDto);
  }

  @Override
  public ResponseEntity<Void> deleteBooking(Integer bookingId) {
    bookingService.deleteBooking(bookingId);
    return ResponseEntity.ok().build();
  }



}
