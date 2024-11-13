package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MonthlyPeriod {
  // 期
  private String period;

  // 開始日
  private LocalDate startDate;

  // 終了日
  private LocalDate endDate;

  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;

  private LocalDateTime createdAt;
}
