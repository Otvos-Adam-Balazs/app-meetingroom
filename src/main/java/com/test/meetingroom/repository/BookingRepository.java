package com.test.meetingroom.repository;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.test.meetingroom.entity.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

  @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId")
  public List<Booking> findBookingsByMeetingRoomId(@Param("roomId") Integer roomId);

  @Query("SELECT b FROM Booking b")
  public List<Booking> findBookings();

  @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId")
  public List<Booking> deleteBooking(@Param("bookingId") Integer bookingId);

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
