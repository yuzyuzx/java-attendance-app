package com.example.Attendance.web.user.form;

import lombok.Data;

@Data
public class MonthlyPeriodForm {
  // 期
  private String period;

  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;
}
