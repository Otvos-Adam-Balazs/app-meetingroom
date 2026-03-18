package com.test.meetingroom.repository;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.test.meetingroom.entity.Booking;

/**
 * Repository for Booking entities. Contains custom queries for retrieving and checking bookings.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

  /**
   * Finds all bookings for a specific meeting room.
   *
   * @param roomId the ID of the meeting room
   * @return a list of {@link Booking} objects for the specified room
   */
  @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId")
  public List<Booking> findBookingsByMeetingRoomId(@Param("roomId") Integer roomId);

  /**
   * Retrieves all bookings in the system.
   *
   * @return a list of all {@link Booking} objects
   */
  @Query("SELECT b FROM Booking b")
  public List<Booking> findBookings();

  /**
   * Checks whether there exists a booking for the given room that overlaps with a given time
   * interval.
   *
   * @param roomId the ID of the meeting room
   * @param startTime the start time of the new booking
   * @param endTime the end time of the new booking
   * @return true if there is a conflicting booking, false otherwise
   */
  @Query("""
      SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
      FROM Booking b
      WHERE b.room.id = :roomId
        AND b.startTime < :endTime
        AND b.endTime > :startTime
      """)
  boolean existsOverlappingBooking(
      @Param("roomId") Integer roomId,
      @Param("endTime") OffsetDateTime endTime,
      @Param("startTime") OffsetDateTime startTime);

}
