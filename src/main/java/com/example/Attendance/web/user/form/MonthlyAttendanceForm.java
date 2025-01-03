package com.example.Attendance.web.user.form;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyAttendanceForm {
  // 月のデータ
  private MonthlyPeriodForm monthlyPeriodForm;

  // 日別勤怠データ
  private List<DailyAttendanceForm> dailyAttendanceList;

  // 処理ボタン
  private String action;
}
