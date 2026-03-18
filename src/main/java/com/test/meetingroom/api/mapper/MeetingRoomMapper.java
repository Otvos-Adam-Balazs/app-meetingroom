package com.test.meetingroom.api.mapper;

import com.test.meetingroom.entity.MeetingRoom;
import com.test.meetingroom.model.MeetingRoomDto;

public class MeetingRoomMapper {

  public static MeetingRoomDto toDto(MeetingRoom meetingRoom) {
    return new MeetingRoomDto(meetingRoom.getId(), meetingRoom.getName());
  }
}
