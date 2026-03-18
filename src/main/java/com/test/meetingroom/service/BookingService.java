package com.test.meetingroom.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.entity.MeetingRoom;
import com.test.meetingroom.exception.BookingConflictException;
import com.test.meetingroom.exception.BookingDataException;
import com.test.meetingroom.model.BookingRequest;
import com.test.meetingroom.repository.BookingRepository;

@Service
public class BookingService {

  @Autowired
  private BookingRepository repository;
  @Autowired
  private RoomService roomService;

  public BookingService() {}

  public Booking createBooking(BookingRequest bookingRequest) {

    if (bookingRequest.getStartTime().isEqual(bookingRequest.getEndTime())) {
      throw new BookingDataException("Start and end time cannot be the same");
    }

    if (bookingRequest.getStartTime().isAfter(bookingRequest.getEndTime())) {
      throw new BookingDataException("Start time cannot be after end time");
    }

    MeetingRoom meetingRoomById = roomService.getMeetingRoomById(bookingRequest.getRoomId());

    boolean existsOverlappingBooking = repository.existsOverlappingBooking(
        meetingRoomById.getId(), bookingRequest.getEndTime(), bookingRequest.getStartTime());

    if (existsOverlappingBooking) {
      throw new BookingConflictException("Booking time conflicts with existing booking");
    }

    Booking booking = new Booking(meetingRoomById, bookingRequest.getStartTime(),
        bookingRequest.getEndTime());
    return repository.save(booking);

  }

  public void deleteBooking(Integer bookingId) {
    repository.deleteById(bookingId);
  }

  public List<Booking> getBookingsByMeetingRoomId(Integer roomId) {
    return repository.findBookingsByMeetingRoomId(roomId);
  }


  public List<Booking> getBookings() {
    return repository.findBookings();
  }
}
