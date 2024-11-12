package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.DailyAttendance;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MonthlyAttendanceForm {
  // 期
  private String period;

  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;

  private String action;

  private List<DailyAttendanceForm> dailyAttendance;
}
