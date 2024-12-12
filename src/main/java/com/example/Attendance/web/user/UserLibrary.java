package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.*;
import com.example.Attendance.web.user.form.DailyAttendanceForm;
import com.example.Attendance.web.user.form.MonthlyAttendanceForm;
import com.example.Attendance.web.user.show.ShowApproval;
import com.example.Attendance.web.user.show.ShowDailyAttendance;
import com.example.Attendance.web.user.show.ShowMonthlyAttendance;
import com.example.Attendance.web.user.show.ShowMonthlyPeriod;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserLibrary {

  /**
   * トップ画面（パラメータがない）へアクセスしたときに、
   * 現在日時を記録するプロパティ
   */
  private LocalDate now;

  private YearMonth getPreviousPeriod(YearMonth period) {
    return period.minusMonths(1);
  }

  private YearMonth getNextPeriod(YearMonth period) {
    return period.plusMonths(1);
  }

  private LocalDate getStartDate(YearMonth period) {
    YearMonth previousMonth = period.minusMonths(1);
    return LocalDate.of(previousMonth.getYear(), previousMonth.getMonthValue(), 21);
  }

  private LocalDate getEndDate(YearMonth period) {
    return LocalDate.of(period.getYear(), period.getMonthValue(), 20);
  }

  private String getYear(YearMonth period) {
    return String.valueOf(period).split("-")[0];
  }

  private String getMonth(YearMonth period) {
    return String.valueOf(period).split("-")[1];
  }

  private String dateTimeFormatter(YearMonth ym, String pettern) {
    DateTimeFormatter f = DateTimeFormatter.ofPattern(pettern);
    return f.format(ym);
  }

  /**
   * 現在日時をセットする
   */
  public void setLocalDateNow() {
    this.now = LocalDate.now();
  }

  /**
   * セットした現在日時を取得する
   */
  private LocalDate currentTime() {
    return now;
  }

  /**
   * メソッドを呼び出した時点での期（当月期）を取得する
   */
  private YearMonth getCurrentPeriod(LocalDate now) {
    YearMonth ym = YearMonth.of(now.getYear(), now.getMonthValue());

    int day = now.getDayOfMonth();
    if(20 < day) {
      ym = ym.plusMonths(1);
    }

    return ym;
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
      Objects.equals(obj.getStartTime(), "00:00:00") ? "" : formatTimeForDisplay(obj.getStartTime())
    );

    show.setEndTime(
      Objects.equals(obj.getEndTime(), "00:00:00") ? "" : formatTimeForDisplay(obj.getEndTime())
    );

    show.setWorkHours(
      obj.getWorkHours() == 0.0 ? "" : String.valueOf(obj.getWorkHours())
    );

    show.setStartTimeHoliday(
      Objects.equals(obj.getStartTimeHoliday(), "00:00:00") ? "" : formatTimeForDisplay(obj.getStartTimeHoliday())
    );

    show.setEndTimeHoliday(
      Objects.equals(obj.getEndTimeHoliday(), "00:00:00") ? "" : formatTimeForDisplay(obj.getEndTimeHoliday())
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

  /**
   * 時間を画面表示用に変換する
   * <p>
   * 表示用フォーマット
   * 9:00
   * 10:00
   */
  private String formatTimeForDisplay(String time) {
    String result = time;

    // `00:00:00`形式は末尾の`:00`を削除する
    if(time.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
      // 末尾の :00 を削除して返す
      result = time.substring(0, 5);
    }

    // 最初の`0`を削除する
    return result.replaceFirst("^0", "");
  }

  /**
   * 時間をDB登録用に変換する
   * <p>
   * 登録用フォーマット
   * 00:00:00
   */
  private String formatTimeForRegister(String time) {
    String result = time;

    if(result == null || result.isEmpty()) {
      return "00:00:00";
    }

    // `9:00`など時間が一桁の場合は先頭に`0`を付与する
    if(result.matches("^\\d:\\d{2}$")) {
      result = String.format("0%s", result);
    }

    if(result.matches("^\\d{2}:\\d{2}$")) {
      result = String.format("%s:00", result);
    }

    return result;
  }

  public YearMonth getCurrentPeriod() {
    YearMonth ym = YearMonth.of(currentTime().getYear(), currentTime().getMonthValue());

    int day = currentTime().getDayOfMonth();
    if(20 < day) {
      ym = ym.plusMonths(1);
    }

    return ym;
  }

  /**
   * 勤怠データが入力されているかチェックする
   * <p>
   * true: データが入力されていると判定
   * - 実働時間数が0ではない
   * - 休日稼働数が0ではない
   * - コメントが入力されている
   */
  public boolean isEmptyDailyAttendanceData(List<DailyAttendanceForm> list) {
    for(DailyAttendanceForm obj : list) {
      if(obj.getWorkHours() != 0.0) {
        return false;
      }

      if(obj.getWorkHoursHoliday() != 0.0) {
        return false;
      }

      if(!Objects.equals(obj.getComment(), "")) {
        return false;
      }
    }

    return true;
  }

  /**
   * 画面表示に必要なデータをセットする
   */
  public ShowMonthlyAttendance setAttendanceData(AttendanceService service, YearMonth period) {
    String strPeriod = dateTimeFormatter(period, "yyyyMM");

    ShowMonthlyAttendance showMonthlyAttendance = new ShowMonthlyAttendance();

    MonthlyPeriod monthlyPeriod = service.fetchMonthlyPeriod(strPeriod);

    // データが存在しない
    if(monthlyPeriod == null) {
      showMonthlyAttendance.setYear(getYear(period));
      showMonthlyAttendance.setMonth(getMonth(period));
      showMonthlyAttendance.setCurrentPeriod(getCurrentPeriod(LocalDate.now()).toString());
      showMonthlyAttendance.setPreviousPeriod(getPreviousPeriod(period).toString());
      showMonthlyAttendance.setNextPeriod(getNextPeriod(period).toString());
      showMonthlyAttendance.setMonthlyPeriod(setEmptyMonthlyPeriod(period));
      showMonthlyAttendance.setApproval(setEmptyShowApproval(period));
      showMonthlyAttendance.setDailyAttendanceList(setEmptyDailyAttendance(period));

      return showMonthlyAttendance;
    }

    ShowMonthlyPeriod showMonthlyPeriod = setShowMonthlyPeriod(monthlyPeriod);
    showMonthlyAttendance.setMonthlyPeriod(showMonthlyPeriod);

    Approval approval = service.fetchApproval(strPeriod);
    showMonthlyAttendance.setApproval(setShowApproval(approval));

    List<DailyAttendance> dailyAttendances = service.fetchAttendanceWithInPeriod(monthlyPeriod.getStartDate(), monthlyPeriod.getEndDate());
    List<ShowDailyAttendance> showDailyAttendanceList = new ArrayList<>();
    for(DailyAttendance obj : dailyAttendances) {
      ShowDailyAttendance showDailyAttendance = setShowDailyAttendance(obj);
      showDailyAttendanceList.add(showDailyAttendance);
    }
    showMonthlyAttendance.setDailyAttendanceList(showDailyAttendanceList);

    showMonthlyAttendance.setYear(getYear(period));
    showMonthlyAttendance.setMonth(getMonth(period));
    showMonthlyAttendance.setCurrentPeriod(getCurrentPeriod(LocalDate.now()).toString());
    showMonthlyAttendance.setPreviousPeriod(getPreviousPeriod(period).toString());
    showMonthlyAttendance.setNextPeriod(getNextPeriod(period).toString());

    return showMonthlyAttendance;
  }

  public void registerAttendanceData(
    YearMonth period,
    AttendanceService service,
    MonthlyAttendanceForm form
  ) {
    String strPeriod = dateTimeFormatter(period, "yyyyMM");

    // 該当期データ削除処理
    service.deleteApproval(strPeriod);
    service.deleteMonthlyPeriod(strPeriod);
    service.deleteAttendanceRecords(
      getStartDate(period),
      getEndDate(period)
    );

    // 期データ登録処理
    service.registerMonthlyPeriod(
      strPeriod,
      getStartDate(period),
      getEndDate(period),
      form.getMonthlyPeriodForm().getWorkHoursMonth(),
      form.getMonthlyPeriodForm().getWorkHoursMonthHoliday(),
      LocalDateTime.now()
    );


    // 承認登録処理
    char approvalStatus = '0';
    if(Objects.equals(form.getAction(), "approval-request")) {
      approvalStatus = '2';
    }

    service.registerApproval(
      strPeriod,
      approvalStatus,
      LocalDateTime.of(1900, 1, 1, 0, 0, 0),
      LocalDateTime.of(1900, 1, 1, 0, 0, 0),
      LocalDateTime.now()
    );

    // 勤務開始時間と終了時間がペアでセットされているか
    validateTimePairInput(form.getDailyAttendanceList());

    // 勤怠日別データ登録処理
    for(DailyAttendanceForm obj : form.getDailyAttendanceList()) {
      obj.setStartTime(formatTimeForRegister(obj.getStartTime()));
      obj.setEndTime(formatTimeForRegister(obj.getEndTime()));
      obj.setStartTimeHoliday(formatTimeForRegister(obj.getStartTimeHoliday()));
      obj.setEndTimeHoliday(formatTimeForRegister(obj.getEndTimeHoliday()));

      service.registerDailyAttendanceRecords(
        obj.getDate(),
        obj.getMonth(),
        obj.getDay(),
        obj.getDayOfWeek(),
        obj.getStartTime(),
        obj.getEndTime(),
        obj.getWorkHours(),
        obj.getStartTimeHoliday(),
        obj.getEndTimeHoliday(),
        obj.getWorkHoursHoliday(),
        obj.getDayType(),
        obj.getComment(),
        obj.getHolidayName()
      );
    }
  }

  /**
   * パラメータの値がYearMonth型の形式と一致しているかチェックする
   */
  public boolean isValidateYearMonthParam(String param) {
    Pattern p = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])$");
    Matcher m = p.matcher(param);

    return !m.matches();
  }

  /**
   * 承認済の月であるか確認する
   * <p>
   * false 未承認
   * true 承認済
   */
  public boolean isApproved(YearMonth period, AttendanceService service) {
    Approval approval = service.fetchApproval(
      dateTimeFormatter(period, "yyyyMM")
    );

    // データが存在していない
    if(approval == null) {
      return false;
    }

    // status = 1は承認済コード
    // それ以外は未承認コード
    return approval.getStatus() == '1';
  }

  /**
   * 勤務開始時間と終了時間がペアで入力されているかチェックする
   * ペアで入力されていない場合、空の値にセットし直す
   */
  public void validateTimePairInput(List<DailyAttendanceForm> list) {
    for(DailyAttendanceForm obj : list) {
      String s = obj.getStartTime();
      String e = obj.getEndTime();
      String hs = obj.getStartTimeHoliday();
      String he = obj.getEndTimeHoliday();

      // 平日判定
      // 平日は休日の開始時間と終了時間はnullになっている
      if(Objects.isNull(hs) && Objects.isNull(he)) {
        // 開始時間未入力 && 終了時間入力済
        if(s.isEmpty() && !e.isEmpty()) {
          obj.setStartTime("");
          obj.setEndTime("");
        }

        // 開始時間入力済 && 終了時間未入力
        if(!s.isEmpty() && e.isEmpty()) {
          obj.setStartTime("");
          obj.setEndTime("");
        }
      }

      // 休日判定
      // 休日は平日の開始時間と終了時間はnullになっている
      if(Objects.isNull(s) && Objects.isNull(e)) {
        // 休日開始時間未入力 && 休日終了時間入力済
        if(hs.isEmpty() && !he.isEmpty()) {
          obj.setStartTimeHoliday("");
          obj.setEndTimeHoliday("");
        }

        // 休日開始時間入力済 && 休日終了時間未入力
        if(!hs.isEmpty() && he.isEmpty()) {
          obj.setStartTimeHoliday("");
          obj.setEndTimeHoliday("");
        }
      }
    }
  }

}
