package com.example.Attendance.web.user;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyAttendanceForm {
  // 月のデータ
  private MonthlyPeriodForm monthlyPeriodForm;

  // 日別勤怠データ
  private List<DailyAttendanceForm> dailyAttendance;

  // 処理ボタン
  private String action;
}
