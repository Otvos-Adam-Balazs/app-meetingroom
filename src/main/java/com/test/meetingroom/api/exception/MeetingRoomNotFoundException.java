package com.test.meetingroom.api.exception;

public class MeetingRoomNotFoundException extends RuntimeException {

  public MeetingRoomNotFoundException() {
    super("Meeting room not found");
  }
}
