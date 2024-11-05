package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Data
public class Attendance {
  private long id;

  // 年月日
  private LocalDate date;

  // 期

  // 勤務開始時刻
  private LocalTime startTime;

  // 勤務終了時刻
  private LocalTime endTime;

  // 実働稼働数
  private float workHours;

  // 休日勤務開始時刻
  private LocalTime startTimeHoliday;

  // 休日勤務終了時刻
  private LocalTime endTimeHoliday;

  // 休日実働稼働数
  private float workHoursHoliday;

  // 曜日
  private char dayType;

  // 祝日名称
  private String holidayName;
}
