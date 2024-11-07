package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.DailyAttendance;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
public class MonthlyAttendanceForm {
  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;

  private List<DailyAttendanceForm> dailyAttendance;
}
