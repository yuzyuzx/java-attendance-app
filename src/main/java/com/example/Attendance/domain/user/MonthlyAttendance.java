package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonthlyAttendance {
  // 年
  private String year;

  // 期
//  private String period;

  // 開始日
  private LocalDate startDate;

  // 終了日
  private LocalDate endDate;

  // 当期
  private String currentPeriod;

  // 前期
  private String previousPeriod;

  // 翌期
  private String nextPeriod;

  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;

  // 承認ステータス
  private char applovalStatus;

  // 勤怠レコード
  private List<DailyAttendance> dailyAttendance;
}
