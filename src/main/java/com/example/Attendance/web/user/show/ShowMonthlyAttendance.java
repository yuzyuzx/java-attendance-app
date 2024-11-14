package com.example.Attendance.web.user.show;

import lombok.Data;

import java.util.List;

/**
 * 画面表示用クラス
 * 表示用に型を変換する
 */
@Data
public class ShowMonthlyAttendance {
  // 年
  private String year;

  // 月
  private String month;

  // 当期
  private String currentPeriod;

  // 前期
  private String previousPeriod;

  // 翌期
  private String nextPeriod;

  // 期のデータ
  private ShowMonthlyPeriod monthlyPeriod;

  // 承認ステータス
  private ShowApproval approval;

  // 勤怠レコード
  private List<ShowDailyAttendance> dailyAttendanceList;
}
