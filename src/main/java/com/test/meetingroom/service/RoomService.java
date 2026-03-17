package com.test.meetingroom.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.test.meetingroom.model.MeetingRoom;

@Service
public class RoomService {

  public List<MeetingRoom> getMeetingRooms() {
    return List.of(new MeetingRoom(1, "Alpha"), new MeetingRoom(2, "Beta"),
        new MeetingRoom(3, "Gamma"),
        new MeetingRoom(4, "Delta"), new MeetingRoom(5, "Epsilon"));
  }

}
