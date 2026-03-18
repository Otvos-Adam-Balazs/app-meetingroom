package com.test.meetingroom.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.test.meetingroom.model.ErrorResponse;
import com.test.meetingroom.model.ValidationError;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BookingDataException.class)
  public ResponseEntity<ErrorResponse> handleBookingDataException(BookingDataException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("BOOKING_DATA_ERROR", ex.getMessage()));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleInvalidFormat(HttpMessageNotReadableException ex) {
    return ResponseEntity
        .badRequest()
        .body(new ErrorResponse("INVALID_FORMAT", ex.getMostSpecificCause().getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ValidationError>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()))
        .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(BookingConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict(BookingConflictException ex) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(new ErrorResponse("BOOKING_CONFLICT", ex.getMessage()));
  }

  @ExceptionHandler(MeetingRoomNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleMeetingRoomNotFoundException(
      MeetingRoomNotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse("MEETING_ROOM_NOT_FOUND", ex.getMessage()));
  }

}
