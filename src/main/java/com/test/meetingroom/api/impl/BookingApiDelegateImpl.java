package com.test.meetingroom.api.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    return ResponseEntity.ok(bookingService.bookings(roomId));
  }

  @Override
  public ResponseEntity<BookingDto> createBooking(BookingRequest bookingRequest) {
    // TODO Auto-generated method stub
    return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
  }

  @Override
  public ResponseEntity<Void> deleteBooking(Integer bookingId) {
    // TODO Auto-generated method stub
    return BookingApiDelegate.super.deleteBooking(bookingId);
  }



}
