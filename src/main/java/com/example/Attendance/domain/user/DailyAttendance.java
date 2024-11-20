package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class DailyAttendance {
//  private long id;

  // 年月日
  private LocalDate date;

  // 月
  private String month;

  // 日
  private String day;

  // 曜日
  private String dayOfWeek;

  // 勤務開始時刻
  private LocalTime startTime;

  // 勤務終了時刻
  private LocalTime endTime;

  // 実働稼働数
  private double workHours;

  // 休日勤務開始時刻
  private LocalTime startTimeHoliday;

  // 休日勤務終了時刻
  private LocalTime endTimeHoliday;

  // 休日実働稼働数
  private double workHoursHoliday;

  // 曜日タイプ 平日 土曜 日曜 祝日
  private char dayType;

  // 備考
  private String comment;

  // 祝日名称
  private String holidayName;

}
