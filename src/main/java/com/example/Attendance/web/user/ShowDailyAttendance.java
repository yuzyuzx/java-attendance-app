package com.example.Attendance.web.user;

import lombok.Data;

/**
 * 画面表示用クラス
 * 表示するデータはString型で定義する
 */
@Data
public class ShowDailyAttendance {
  // 年月日
  private String date;

  // 月
  private String month;

  // 日
  private String day;

  // 曜日
  private String dayOfWeek;

  // 勤務開始時刻
  private String startTime;

  // 勤務終了時刻
  private String endTime;

  // 実働稼働数
  private String workHours;

  // 休日勤務開始時刻
  private String startTimeHoliday;

  // 休日勤務終了時刻
  private String endTimeHoliday;

  // 休日実働稼働数
  private String workHoursHoliday;

  // 曜日タイプ 平日 土曜 日曜 祝日
  private String dayType;

  // 備考
  private String comment;

  // 祝日名称
  private String holidayName;
}
