package com.test.meetingroom.api.mapper;

import java.time.ZoneId;
import com.test.meetingroom.entity.Booking;
import com.test.meetingroom.model.BookingDto;

public class BookingMapper {

  public static BookingDto toDto(Booking booking) {
    return new BookingDto().roomId(booking.getRoom().getId()).roomName(booking.getRoom().getName())
        .startTime(booking.getStartTime().atZoneSameInstant(ZoneId.systemDefault())
            .toOffsetDateTime())
        .endTime(booking.getEndTime().atZoneSameInstant(ZoneId.systemDefault())
            .toOffsetDateTime())
        .id(booking.getId());
  }

}
