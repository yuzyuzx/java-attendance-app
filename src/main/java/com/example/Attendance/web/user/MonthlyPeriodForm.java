package com.example.Attendance.web.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MonthlyPeriodForm {
  // 期
  private String period;

  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;
}
