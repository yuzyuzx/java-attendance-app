package com.example.Attendance.web.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DailyAttendanceForm {
  private LocalTime startTime;

  private LocalTime endTime;
}
