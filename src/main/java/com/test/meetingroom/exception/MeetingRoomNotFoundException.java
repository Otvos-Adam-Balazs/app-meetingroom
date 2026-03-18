package com.test.meetingroom.exception;

public class MeetingRoomNotFoundException extends RuntimeException {

  public MeetingRoomNotFoundException() {
    super("Meeting room not found");
  }
}
