package com.test.meetingroom.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.test.meetingroom.api.exception.MeetingRoomNotFoundException;
import com.test.meetingroom.entity.MeetingRoom;
import com.test.meetingroom.repository.MeetingRoomRepository;

/**
 * Service class for managing meeting rooms. Provides methods to retrieve all meeting rooms or a
 * specific meeting room by ID.
 */
@Service
public class RoomService {

  private final MeetingRoomRepository meetingRoomRepository;

  public RoomService(MeetingRoomRepository meetingRoomRepository) {
    this.meetingRoomRepository = meetingRoomRepository;
  }

  /**
   * Retrieves all meeting rooms from the repository.
   *
   * @return a list of {@link MeetingRoom} objects
   */
  public List<MeetingRoom> getMeetingRooms() {
    return meetingRoomRepository.findMeetingRooms();
  }

  /**
   * Retrieves a meeting room by its unique ID.
   *
   * @param id the unique identifier of the meeting room
   * @return the {@link MeetingRoom} with the given ID
   * @throws MeetingRoomNotFoundException if no meeting room with the given ID exists
   */
  public MeetingRoom getMeetingRoomById(Integer id) {
    MeetingRoom meetingRoomById = meetingRoomRepository.findMeetingRoomById(id);
    if (ObjectUtils.isEmpty(meetingRoomById)) {
      throw new MeetingRoomNotFoundException();
    }
    return meetingRoomRepository.findMeetingRoomById(id);
  }

}
