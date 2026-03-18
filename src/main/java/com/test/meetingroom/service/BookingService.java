package com.test.meetingroom.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.test.meetingroom.api.exception.BookingConflictException;
import com.test.meetingroom.api.exception.BookingDataException;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.entity.MeetingRoom;
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

    Objects.requireNonNull(bookingRequest, "bookingRequest cant be null!");
    Objects.requireNonNull(bookingRequest.getRoomId(), "roomId cant be null!");
    Objects.requireNonNull(bookingRequest.getStartTime(), "startTime cant be null!");
    Objects.requireNonNull(bookingRequest.getEndTime(), "endTime cant be null!");

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
    Objects.requireNonNull(bookingId, "bookingId cant be null!");
    repository.deleteById(bookingId);
  }

  public List<Booking> getBookingsByMeetingRoomId(Integer roomId) {
    return repository.findBookingsByMeetingRoomId(roomId);
  }


  public List<Booking> getBookings() {
    return repository.findBookings();
  }
}
