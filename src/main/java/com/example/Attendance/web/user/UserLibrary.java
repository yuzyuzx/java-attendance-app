package com.example.Attendance.web.user;


import com.example.Attendance.domain.user.Approval;
import com.example.Attendance.domain.user.AttendanceService;
import com.example.Attendance.domain.user.MonthlyAttendance;
import com.example.Attendance.domain.user.MonthlyPeriod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    YearMonth ym = YearMonth.of(now.getYear(), now.getMonthValue());

    int day = now.getDayOfMonth();
    if(20 < day) {
      ym.plusMonths(1);
    }

    return ym;
  }

  public YearMonth getPreviousPeriod(YearMonth period) {
    return period.minusMonths(1);
  }

  public YearMonth getNextPeriod(YearMonth period) {
    return period.plusMonths(1);
  }

  public LocalDate getStartDate(YearMonth period) {
    YearMonth previousMonth = period.minusMonths(1);
    return LocalDate.of(previousMonth.getYear(), previousMonth.getMonthValue(), 21);
  }

  public LocalDate getEndDate(YearMonth period) {
    return LocalDate.of(period.getYear(), period.getMonthValue(), 20);
  }

  public String getYear(YearMonth period) {
    return String.valueOf(period).split("-")[0];
  }

  public String getMonth(YearMonth period) {
    return String.valueOf(period).split("-")[1];
  }

  public String dateTimeFormatter(YearMonth ym, String pettern) {
    DateTimeFormatter f = DateTimeFormatter.ofPattern(pettern);
    return f.format(ym);
  }

  public MonthlyAttendance setAttendanceData(AttendanceService service, YearMonth period) {
    String strPeriod = dateTimeFormatter(period, "yyyyMM");

    MonthlyAttendance monthlyAttendance = new MonthlyAttendance();

    // データ存在確認
    MonthlyPeriod monthlyPeriod = service.fetchMonthlyPeriod(strPeriod);

    Approval approval = service.fetchApproval(strPeriod);

    monthlyAttendance.setApproval(approval);
    monthlyAttendance.setMonthlyPeriod(monthlyPeriod);
    monthlyAttendance.setDailyAttendance(service.fetchAttendanceWithinPeriod(monthlyPeriod.getStartDate(), monthlyPeriod.getEndDate()));
    monthlyAttendance.setYear(getYear(period));
    monthlyAttendance.setMonth(getMonth(period));
    monthlyAttendance.setCurrentPeriod(period);
    monthlyAttendance.setPreviousPeriod(getPreviousPeriod(period));
    monthlyAttendance.setNextPeriod(getNextPeriod(period));

    return monthlyAttendance;
  }

  public void debugDate(YearMonth period) {
    System.out.println("debugDate\n");
    System.out.printf("現在期: %s%n", period);
    System.out.printf("前期: %s%n", getPreviousPeriod(period));
    System.out.printf("翌期: %s%n", getNextPeriod(period));
    System.out.printf("開始日: %s%n", getStartDate(period));
    System.out.printf("終了日: %s%n", getEndDate(period));
  }
}
