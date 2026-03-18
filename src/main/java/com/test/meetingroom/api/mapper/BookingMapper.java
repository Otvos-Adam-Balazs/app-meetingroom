package com.test.meetingroom.api.mapper;

import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.model.BookingDto;

public class BookingMapper {

  public static BookingDto toDto(Booking booking) {
    return new BookingDto().roomId(booking.getRoom().getId()).roomName(booking.getRoom().getName())
        .startTime(booking.getStartTime())
        .endTime(booking.getEndTime()).id(booking.getId());
  }

}
