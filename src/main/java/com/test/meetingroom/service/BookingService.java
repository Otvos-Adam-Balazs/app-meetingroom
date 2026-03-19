package com.test.meetingroom.service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
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

/**
 * Service class for managing meeting room bookings. Provides methods to create, retrieve, and
 * delete bookings.
 */
@Service
public class BookingService {

  @Autowired
  private BookingRepository repository;
  @Autowired
  private RoomService roomService;

  public BookingService() {}

  /**
   * Creates a new booking for a meeting room.
   *
   * @param bookingRequest the booking request containing room ID, start time, and end time
   * @return the saved {@link Booking} object
   * @throws NullPointerException if bookingRequest or its required fields are null
   * @throws BookingDataException if the start and end times are invalid
   * @throws BookingConflictException if the requested time overlaps with an existing booking
   */
  public Booking createBooking(BookingRequest bookingRequest) {

    Objects.requireNonNull(bookingRequest, "bookingRequest cant be null!");
    Objects.requireNonNull(bookingRequest.getRoomId(), "roomId cant be null!");
    Objects.requireNonNull(bookingRequest.getStartTime(), "startTime cant be null!");
    Objects.requireNonNull(bookingRequest.getEndTime(), "endTime cant be null!");

    bookingRequest.endTime(bookingRequest.getEndTime().truncatedTo(ChronoUnit.SECONDS));
    bookingRequest.startTime(bookingRequest.getStartTime().truncatedTo(ChronoUnit.SECONDS));

    if (bookingRequest.getStartTime().isBefore(OffsetDateTime.now())) {
      throw new BookingDataException("Start time cannot be in the past");
    }

    if (bookingRequest.getStartTime().isEqual(bookingRequest.getEndTime())) {
      throw new BookingDataException("Start and end time cannot be the same");
    }

    if (bookingRequest.getStartTime().isAfter(bookingRequest.getEndTime())) {
      throw new BookingDataException("Start time cannot be after end time");
    }

    MeetingRoom meetingRoomById = roomService.getMeetingRoomById(bookingRequest.getRoomId());

    List<Booking> existsOverlappingBookings = repository.existsOverlappingBooking(
        meetingRoomById.getId(), bookingRequest.getEndTime(), bookingRequest.getStartTime());

    if (!existsOverlappingBookings.isEmpty()) {
      throw new BookingConflictException("Booking time conflicts with existing booking");
    }

    Booking booking = new Booking(meetingRoomById, bookingRequest.getStartTime(),
        bookingRequest.getEndTime());
    return repository.save(booking);
  }

  /**
   * Deletes a booking by its ID.
   *
   * @param bookingId the ID of the booking to delete
   * @throws NullPointerException if bookingId is null
   */
  public void deleteBooking(Integer bookingId) {
    Objects.requireNonNull(bookingId, "bookingId cant be null!");
    repository.deleteById(bookingId);
  }

  /**
   * Retrieves all bookings for a specific meeting room.
   *
   * @param roomId the ID of the meeting room
   * @return a list of {@link Booking} objects for the specified room
   */
  public List<Booking> getBookingsByMeetingRoomId(Integer roomId) {
    return repository.findBookingsByMeetingRoomId(roomId);
  }

  /**
   * Retrieves all bookings.
   *
   * @return a list of all {@link Booking} objects
   */
  public List<Booking> getBookings() {
    return repository.findBookings();
  }
}
