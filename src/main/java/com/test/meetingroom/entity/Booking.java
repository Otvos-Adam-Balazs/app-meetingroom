package com.test.meetingroom.entity;

import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)
  private MeetingRoom room;

  @NotNull
  @Column(nullable = false)
  private OffsetDateTime startTime;

  @NotNull
  @Column(nullable = false)
  private OffsetDateTime endTime;

  public Booking() {}

  public Booking(MeetingRoom room, OffsetDateTime startTime, OffsetDateTime endTime) {
    this.room = room;
    this.startTime = startTime;
    this.endTime = endTime;
  }


  public Integer getId() {
    return id;
  }

  public MeetingRoom getRoom() {
    return room;
  }

  public void setRoom(MeetingRoom room) {
    this.room = room;
  }

  public OffsetDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }

  public OffsetDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(OffsetDateTime endTime) {
    this.endTime = endTime;
  }
}
