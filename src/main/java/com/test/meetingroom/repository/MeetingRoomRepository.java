package com.test.meetingroom.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.test.meetingroom.entity.MeetingRoom;

/**
 * Repository for MeetingRoom entities. Contains custom queries for retrieving meeting room data.
 */
@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {

  /**
   * Retrieves all meeting rooms from the database.
   *
   * @return a list of all {@link MeetingRoom} objects
   */
  @Query("SELECT m FROM MeetingRoom m")
  public List<MeetingRoom> findMeetingRooms();

  /**
   * Retrieves a meeting room by its unique ID.
   *
   * @param meetingRoomId the ID of the meeting room
   * @return the {@link MeetingRoom} with the given ID, or null if not found
   */
  @Query("SELECT m FROM MeetingRoom m WHERE m.id = :meetingRoomId")
  public MeetingRoom findMeetingRoomById(@Param("meetingRoomId") Integer meetingRoomId);

}
