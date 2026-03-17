package com.test.meetingroom.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.model.BookingDto;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.repository.BookingRepository;

@Service
public class BookingService {

  private final BookingRepository repository;

  public BookingService(BookingRepository repository) {
    this.repository = repository;
  }

  public BookingDto createBooking(BookingRequest bookingRequest) {

    boolean existsOverlappingBooking = repository.existsOverlappingBooking(
        bookingRequest.getRoomId(), bookingRequest.getEndTime(), bookingRequest.getStartTime());

    if (existsOverlappingBooking) {
      throw new RuntimeException("Booking conflict");
    }

    Booking booking = new Booking(bookingRequest.getRoomId(), bookingRequest.getStartTime(),
        bookingRequest.getEndTime());
    Booking savedBooking = repository.save(booking);
    return new BookingDto().roomId(booking.getRoomId()).startTime(booking.getStartTime())
        .endTime(booking.getEndTime()).id(savedBooking.getId());
  }

  public BookingDto deleteBooking(Integer bookingId) {

    if (!repository.existsById(bookingId)) {
      throw new RuntimeException("Booking not found");
    }
    repository.deleteById(bookingId);
    return null;
  }

  public List<BookingDto> bookings(Integer roomId) {
    return repository.findBookingsByMeetingRoomId(roomId).stream().map(booking -> new BookingDto()
        .roomId(booking.getRoomId()).startTime(booking.getStartTime()).endTime(booking.getEndTime())
        .id(booking.getId())).collect(Collectors.toList());

  }
}
