package com.example.Attendance.web.user;


import com.example.Attendance.domain.user.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

  /**
   * 画面表示に必要なデータをセットする
   */
  public ShowMonthlyAttendance setAttendanceData(AttendanceService service, YearMonth period) {
    String strPeriod = dateTimeFormatter(period, "yyyyMM");

    ShowMonthlyAttendance showMonthlyAttendance = new ShowMonthlyAttendance();

    // データ存在確認を行う
    // DBにデータが存在しない場合処理を続けるとエラーになる
    MonthlyPeriod monthlyPeriod = service.fetchMonthlyPeriod(strPeriod);
    ShowMonthlyPeriod showMonthlyPeriod = setShowMonthlyPeriod(monthlyPeriod);
    showMonthlyAttendance.setShowMonthlyPeriod(showMonthlyPeriod);

    Approval approval = service.fetchApproval(strPeriod);
    showMonthlyAttendance.setApproval(approval);

    List<DailyAttendance> dailyAttendances = service.fetchAttendanceWithinPeriod(monthlyPeriod.getStartDate(), monthlyPeriod.getEndDate());
    List<ShowDailyAttendance> showDailyAttendanceList = new ArrayList<>();
    for(DailyAttendance obj : dailyAttendances) {
      ShowDailyAttendance showDailyAttendance = setShowDailyAttendance(obj);
      showDailyAttendanceList.add(showDailyAttendance);
    }
    showMonthlyAttendance.setShowDailyAttendanceList(showDailyAttendanceList);

    showMonthlyAttendance.setYear(getYear(period));
    showMonthlyAttendance.setMonth(getMonth(period));
    showMonthlyAttendance.setCurrentPeriod(period.toString());
    showMonthlyAttendance.setPreviousPeriod(getPreviousPeriod(period).toString());
    showMonthlyAttendance.setNextPeriod(getNextPeriod(period).toString());

    return showMonthlyAttendance;
  }

  /**
   * MonthlyPeriodクラスのプロパティをShowMonthlyPeriodクラスのプロパティに入れ替える
   * String型に変換するため
   */
  private ShowMonthlyPeriod setShowMonthlyPeriod(MonthlyPeriod obj) {
    ShowMonthlyPeriod showMonthlyPeriod = new ShowMonthlyPeriod();
    showMonthlyPeriod.setPeriod(obj.getPeriod());
    showMonthlyPeriod.setStartDate(obj.getStartDate().toString());
    showMonthlyPeriod.setEndDate(obj.getEndDate().toString());
    showMonthlyPeriod.setWorkHoursMonth(
      obj.getWorkHoursMonth() == 0.0 ? "" : String.valueOf(obj.getWorkHoursMonth())
    );
    showMonthlyPeriod.setWorkHoursMonthHoliday(
      obj.getWorkHoursMonthHoliday() == 0.0 ? "" : String.valueOf(obj.getWorkHoursMonthHoliday())
    );

    return showMonthlyPeriod;
  }

  /**
   * DailyAttendanceクラスのプロパティをShowDailyAttendanceクラスのプロパティに入れ替える
   * String型に変換するため
   */
  private ShowDailyAttendance setShowDailyAttendance(DailyAttendance obj) {
    ShowDailyAttendance showDailyAttendance = new ShowDailyAttendance();
    showDailyAttendance.setDate(String.valueOf(obj.getDate()));
    showDailyAttendance.setMonth(obj.getMonth());
    showDailyAttendance.setDay(obj.getDay());
    showDailyAttendance.setDayOfWeek(obj.getDayOfWeek());
    showDailyAttendance.setDayType(String.valueOf(obj.getDayType()));
    showDailyAttendance.setComment(obj.getComment());
    showDailyAttendance.setHolidayName(obj.getHolidayName());

    showDailyAttendance.setStartTime(
      obj.getStartTime().equals(LocalTime.MIDNIGHT) ? "" : obj.getStartTime().toString()
    );

    showDailyAttendance.setEndTime(
      obj.getEndTime().equals(LocalTime.MIDNIGHT) ? "" : obj.getStartTime().toString()
    );

    showDailyAttendance.setWorkHours(
      obj.getWorkHours() == 0.0 ? "" : String.valueOf(obj.getWorkHours())
    );

    showDailyAttendance.setStartTimeHoliday(
      obj.getStartTimeHoliday().equals(LocalTime.MIDNIGHT) ? "" : obj.getStartTimeHoliday().toString()
    );

    showDailyAttendance.setEndTimeHoliday(
      obj.getEndTimeHoliday().equals(LocalTime.MIDNIGHT) ? "" : obj.getStartTimeHoliday().toString()
    );

    showDailyAttendance.setWorkHoursHoliday(
      obj.getWorkHoursHoliday() == 0.0 ? "" : String.valueOf(obj.getWorkHoursHoliday())
    );

    return showDailyAttendance;
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
