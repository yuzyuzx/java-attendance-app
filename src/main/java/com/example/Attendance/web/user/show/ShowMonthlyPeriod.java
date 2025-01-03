package com.example.Attendance.web.user.show;

import lombok.Data;

/**
 * 画面表示用クラス
 * 表示用に型を変換する
 */
@Data
public class ShowMonthlyPeriod {
  // 期
  private String period;

  // 開始日
  private String  startDate;

  // 終了日
  private String  endDate;

  // 実働稼働数
  private String workHoursMonth;

  // 休日実働稼働数
  private String workHoursMonthHoliday;

  private String createdAt;
}
