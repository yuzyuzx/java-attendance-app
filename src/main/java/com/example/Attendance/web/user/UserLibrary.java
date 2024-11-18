package com.example.Attendance.web.user;


import com.example.Attendance.domain.user.*;
import com.example.Attendance.web.user.show.ShowApproval;
import com.example.Attendance.web.user.show.ShowDailyAttendance;
import com.example.Attendance.web.user.show.ShowMonthlyAttendance;
import com.example.Attendance.web.user.show.ShowMonthlyPeriod;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

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

    MonthlyPeriod monthlyPeriod = service.fetchMonthlyPeriod(strPeriod);

    // ▽空の処理
    if(monthlyPeriod == null) {
      showMonthlyAttendance.setYear(getYear(period));
      showMonthlyAttendance.setMonth(getMonth(period));
      showMonthlyAttendance.setCurrentPeriod(period.toString());
      showMonthlyAttendance.setPreviousPeriod(getPreviousPeriod(period).toString());
      showMonthlyAttendance.setNextPeriod(getNextPeriod(period).toString());
      showMonthlyAttendance.setMonthlyPeriod(setEmptyMonthlyPeriod(period));
      showMonthlyAttendance.setApproval(setEmptyShowApproval(period));
      showMonthlyAttendance.setDailyAttendanceList(setEmptyDailyAttendance(period));

      return showMonthlyAttendance;
    }
    // △空の処理

    ShowMonthlyPeriod showMonthlyPeriod = setShowMonthlyPeriod(monthlyPeriod);
    showMonthlyAttendance.setMonthlyPeriod(showMonthlyPeriod);

    Approval approval = service.fetchApproval(strPeriod);
    showMonthlyAttendance.setApproval(setShowApproval(approval));

    List<DailyAttendance> dailyAttendances = service.fetchAttendanceWithinPeriod(monthlyPeriod.getStartDate(), monthlyPeriod.getEndDate());
    List<ShowDailyAttendance> showDailyAttendanceList = new ArrayList<>();
    for(DailyAttendance obj : dailyAttendances) {
      ShowDailyAttendance showDailyAttendance = setShowDailyAttendance(obj);
      showDailyAttendanceList.add(showDailyAttendance);
    }
    showMonthlyAttendance.setDailyAttendanceList(showDailyAttendanceList);

    showMonthlyAttendance.setYear(getYear(period));
    showMonthlyAttendance.setMonth(getMonth(period));
    showMonthlyAttendance.setCurrentPeriod(period.toString());
    showMonthlyAttendance.setPreviousPeriod(getPreviousPeriod(period).toString());
    showMonthlyAttendance.setNextPeriod(getNextPeriod(period).toString());

    return showMonthlyAttendance;
  }

  /**
   * MonthlyPeriodクラスのプロパティをShowMonthlyPeriodクラスのプロパティに入れ替える
   */
  private ShowMonthlyPeriod setShowMonthlyPeriod(MonthlyPeriod obj) {
    ShowMonthlyPeriod show = new ShowMonthlyPeriod();
    show.setPeriod(obj.getPeriod());
    show.setStartDate(obj.getStartDate().toString());
    show.setEndDate(obj.getEndDate().toString());
    show.setCreatedAt(obj.getCreatedAt().toString());

    show.setWorkHoursMonth(
      obj.getWorkHoursMonth() == 0.0 ? "" : String.valueOf(obj.getWorkHoursMonth())
    );

    show.setWorkHoursMonthHoliday(
      obj.getWorkHoursMonthHoliday() == 0.0 ? "" : String.valueOf(obj.getWorkHoursMonthHoliday())
    );

    return show;
  }

  /**
   * ApprovalクラスのプロパティをShowApprovalクラスのプロパティに入れ替える
   */
  private ShowApproval setShowApproval(Approval obj) {
    ShowApproval show = new ShowApproval();
    show.setPeriod(obj.getPeriod());
    show.setStatus(String.valueOf(obj.getStatus()));
    show.setRequestedAt(String.valueOf(obj.getRequestedAt()));
    show.setReviewedAt(String.valueOf(obj.getReviewedAt()));

    return show;
  }

  /**
   * DailyAttendanceクラスのプロパティをShowDailyAttendanceクラスのプロパティに入れ替える
   */
  private ShowDailyAttendance setShowDailyAttendance(DailyAttendance obj) {
    ShowDailyAttendance show = new ShowDailyAttendance();
    show.setDate(String.valueOf(obj.getDate()));
    show.setMonth(obj.getMonth());
    show.setDay(obj.getDay());
    show.setDayOfWeek(obj.getDayOfWeek());
    show.setDayType(String.valueOf(obj.getDayType()));
    show.setComment(obj.getComment());
    show.setHolidayName(obj.getHolidayName());

    show.setStartTime(
      obj.getStartTime().equals(LocalTime.MIDNIGHT) ? "" : obj.getStartTime().toString()
    );

    show.setEndTime(
      obj.getEndTime().equals(LocalTime.MIDNIGHT) ? "" : obj.getEndTime().toString()
    );

    show.setWorkHours(
      obj.getWorkHours() == 0.0 ? "" : String.valueOf(obj.getWorkHours())
    );

    show.setStartTimeHoliday(
      obj.getStartTimeHoliday().equals(LocalTime.MIDNIGHT) ? "" : obj.getStartTimeHoliday().toString()
    );

    show.setEndTimeHoliday(
      obj.getEndTimeHoliday().equals(LocalTime.MIDNIGHT) ? "" : obj.getEndTimeHoliday().toString()
    );

    show.setWorkHoursHoliday(
      obj.getWorkHoursHoliday() == 0.0 ? "" : String.valueOf(obj.getWorkHoursHoliday())
    );

    return show;
  }

  private ShowMonthlyPeriod setEmptyMonthlyPeriod(YearMonth period) {
    ShowMonthlyPeriod show = new ShowMonthlyPeriod();

    show.setPeriod(dateTimeFormatter(period, "yyyyMM"));
    show.setStartDate(getStartDate(period).toString());
    show.setEndDate(getEndDate(period).toString());
    show.setWorkHoursMonth("");
    show.setWorkHoursMonthHoliday("");

    return show;
  }

  private ShowApproval setEmptyShowApproval(YearMonth period) {
    ShowApproval show = new ShowApproval();

    show.setPeriod(dateTimeFormatter(period, "yyyyMM"));
    show.setStatus("0");
    show.setReviewedAt("");
    show.setReviewedAt("");

    return show;
  }

  private List<ShowDailyAttendance> setEmptyDailyAttendance(YearMonth period) {
    LocalDate startDate = getStartDate(period);
    LocalDate endDate = getEndDate(period);
    long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

    List<ShowDailyAttendance> list = new ArrayList<>();
    for(int i = 0; i <= daysBetween; i++) {
      LocalDate currentDate = startDate.plusDays(i);

      ShowDailyAttendance show = new ShowDailyAttendance();

      show.setDate(currentDate.toString());
      show.setMonth(String.valueOf(currentDate.getMonthValue()));
      show.setDay(String.valueOf(currentDate.getDayOfMonth()));
      show.setStartTime("");
      show.setEndTime("");
      show.setWorkHours("");
      show.setStartTimeHoliday("");
      show.setEndTimeHoliday("");
      show.setWorkHoursHoliday("");
      show.setComment("");
      show.setHolidayName("");

      String dayOfWeek = switch(currentDate.getDayOfWeek().getValue()) {
        case 1 -> "月";
        case 2 -> "火";
        case 3 -> "水";
        case 4 -> "木";
        case 5 -> "金";
        case 6 -> "土";
        default -> "日";
      };
      show.setDayOfWeek(dayOfWeek);

      String dayType = switch(currentDate.getDayOfWeek().getValue()) {
        case 6 -> "1";
        case 7 -> "2";
        default -> "0";
      };
      show.setDayType(dayType);

      list.add(show);
    }

    return list;
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
