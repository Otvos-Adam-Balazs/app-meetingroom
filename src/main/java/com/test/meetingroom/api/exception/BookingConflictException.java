package com.test.meetingroom.api.exception;

public class BookingConflictException extends RuntimeException {

  public BookingConflictException(String message) {
    super(message);
  }
}
