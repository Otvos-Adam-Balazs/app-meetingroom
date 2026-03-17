package com.test.meetingroom.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(BookingDataException.class)
  public ResponseEntity<String> handleBooknkDataException(BookingDataException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }

  @ExceptionHandler(BookingConflictException.class)
  public ResponseEntity<String> handleConflict(BookingConflictException ex) {

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }

  @ExceptionHandler(BookingNotFoundException.class)
  public ResponseEntity<String> handleBookingNotFoundConflict(BookingNotFoundException ex) {

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }

}
