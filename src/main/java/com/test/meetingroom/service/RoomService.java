package com.test.meetingroom.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.test.meetingroom.entity.MeetingRoom;
import com.test.meetingroom.exception.MeetingRoomNotFoundException;
import com.test.meetingroom.repository.MeetingRoomRepository;

@Service
public class RoomService {

  private final MeetingRoomRepository meetingRoomRepository;

  public RoomService(MeetingRoomRepository meetingRoomRepository) {
    this.meetingRoomRepository = meetingRoomRepository;
  }

  public List<MeetingRoom> getMeetingRooms() {
    return meetingRoomRepository.findMeetingRooms();
  }

  public MeetingRoom getMeetingRoomById(Integer id) {
    MeetingRoom meetingRoomById = meetingRoomRepository.findMeetingRoomById(id);
    if (ObjectUtils.isEmpty(meetingRoomById)) {
      throw new MeetingRoomNotFoundException();
    }
    return meetingRoomRepository.findMeetingRoomById(id);
  }

}
