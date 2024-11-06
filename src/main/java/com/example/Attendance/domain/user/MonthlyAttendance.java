package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonthlyAttendance {
  /*
    {
      "年": "2024",
      "期": "11",
      "開始日": "20241021",
      "終了日": "20241120",
      "当月": "202411",
      "前月": "202410",
      "翌月": "202412",
      "実働時間数": "100",
      "休日時間数": "10",
      "承認ステータス": "0|1|2",
      [
        {DailyAttendance.class},
        {DailyAttendance.class},
        ...
      ]
    }
   */

  // 年
  private String year;

  // 期
  private String period;

  // 開始日
  private LocalDate startDate;

  // 終了日
  private LocalDate endDate;

  // 当月
  private String currentMonth;

  // 前月
  private String previousMonth;

  // 翌月
  private String nextMonth;

  // 実働稼働数
  private double workHoursMonth;

  // 休日実働稼働数
  private double workHoursMonthHoliday;

  // 承認ステータス
  private char applovalStatus;

  // 勤怠レコード
  private List<DailyAttendance> dailyAttendance;
}
