package com.test.meetingroom.api.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.test.meetingroom.api.RoomApiDelegate;
import com.test.meetingroom.model.MeetingRoom;
import com.test.meetingroom.service.RoomService;

@Service
public class RoomApiDelegateImpl implements RoomApiDelegate {

  @Autowired
  private RoomService roomService;

  @Override
  public ResponseEntity<List<MeetingRoom>> getRooms() {
    return ResponseEntity.ok(roomService.getMeetingRooms());

  }



}
