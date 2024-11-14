package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonthlyAttendance {
  // 年
  private String year;

  // 月
  private String month;

  // 当期
  private YearMonth currentPeriod;

  // 前期
  private YearMonth previousPeriod;

  // 翌期
  private YearMonth nextPeriod;

  // 期のデータ
  private MonthlyPeriod monthlyPeriod;

  // 承認ステータス
  private Approval approval;

  // 勤怠レコード
  private List<DailyAttendance> dailyAttendance;
}
