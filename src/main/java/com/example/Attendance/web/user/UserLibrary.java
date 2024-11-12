package com.example.Attendance.web.user;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Component
public class UserLibrary {
  /**
   * 現在の日付を取得する
   */
  public LocalDate getLocalDate() {
    return LocalDate.now();
  }

  /**
   * 現在日から期を計算する
   */
  public YearMonth getCurrentPeriod(LocalDate now) {
    // パラメータがある場合

    // パラメータがない場合
    YearMonth ym = YearMonth.of(now.getYear(), now.getMonthValue());

    int day = now.getDayOfMonth();
    if(20 < day) {
      ym.plusMonths(1);
    }

    return ym;
  }

  public YearMonth getPreviousPeriod(LocalDate now) {
    // パラメータがある場合

    // パラメータがない場合
    return getCurrentPeriod(now).minusMonths(1);
  }

  public YearMonth getNextPeriod(LocalDate now) {
    // パラメータがある場合

    // パラメータがない場合
    return getCurrentPeriod(now).plusMonths(1);
  }

  public LocalDate getStartDate(LocalDate now) {
    YearMonth ym = getCurrentPeriod(now);
    YearMonth previousMonth = ym.minusMonths(1);
    return LocalDate.of(previousMonth.getYear(), previousMonth.getMonthValue(), 21);
  }

  public LocalDate getEndDate(LocalDate now) {
    YearMonth ym = getCurrentPeriod(now);
    return LocalDate.of(ym.getYear(), ym.getMonthValue(), 20);
  }

  public String dateTimeFormatter(YearMonth d, String pettern) {
    DateTimeFormatter f = DateTimeFormatter.ofPattern(pettern);
    return f.format(d);
  }
}
