package com.test.meetingroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.test.meetingroom.entity.MeetingRoom;
import com.test.meetingroom.repository.MeetingRoomRepository;
import jakarta.annotation.PostConstruct;

@Component
public class MeetingRoomDataInitializer {
  @Autowired
  private MeetingRoomRepository meetingRoomRepository;

  public MeetingRoomDataInitializer() {}

  @PostConstruct
  public void init() {
    if (meetingRoomRepository.count() == 0) {
      meetingRoomRepository.save(new MeetingRoom("Alpha"));
      meetingRoomRepository.save(new MeetingRoom("Beta"));
      meetingRoomRepository.save(new MeetingRoom("Gamma"));
      meetingRoomRepository.save(new MeetingRoom("Delta"));
    }
  }
}
