package com.test.meetingroom.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.test.meetingroom.entity.MeetingRoom;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Integer> {

  @Query("SELECT m FROM MeetingRoom m")
  public List<MeetingRoom> findMeetingRooms();

  @Query("SELECT m FROM MeetingRoom m WHERE m.id = :meetingRoomId")
  public MeetingRoom findMeetingRoomById(@Param("meetingRoomId") Integer meetingRoomId);

}
