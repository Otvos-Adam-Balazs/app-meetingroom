package com.test.meetingroom.api.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.test.meetingroom.api.BookingApiDelegate;
import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.service.BookingService;

@Service
public class BookingApiDelegateImpl implements BookingApiDelegate {

  @Autowired
  private BookingService bookingService;

  @Override
  public ResponseEntity<List<BookingDto>> bookings(Integer roomId) {
    List<BookingDto> bookings = bookingService.bookings(roomId);
    return ResponseEntity.ok(bookings);
  }

  @Override
  public ResponseEntity<BookingDto> createBooking(BookingRequest bookingRequest) {
    BookingDto booking = bookingService.createBooking(bookingRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(booking);
  }

  @Override
  public ResponseEntity<Void> deleteBooking(Integer bookingId) {
    bookingService.deleteBooking(bookingId);
    return ResponseEntity.noContent().build();
  }



}
