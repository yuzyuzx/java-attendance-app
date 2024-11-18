package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.*;
import com.example.Attendance.web.user.form.DailyAttendanceForm;
import com.example.Attendance.web.user.form.MonthlyAttendanceForm;
import com.example.Attendance.web.user.show.ShowMonthlyAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final AttendanceService service;
  private final UserLibrary lib;

  @GetMapping
  public String index(Model model) {
    YearMonth period = lib.getCurrentPeriod(lib.getLocalDate());
//    lib.debugDate(period);

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
//    return "user/test";
  }

  @GetMapping("/{period}")
  public String test(Model model, @PathVariable("period") YearMonth period) {
    System.out.println(period);
//    lib.debugDate(period);
    if(!(period instanceof YearMonth)) {
      YearMonth.of(2024, 11);
      System.out.println("error");
//      return "user/error";
    }

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

//    return "user/index";
    return "user/test";
  }

  @PostMapping
  public String postUserForm(MonthlyAttendanceForm form, Model model) {
    YearMonth period = lib.getCurrentPeriod(lib.getLocalDate());
    String strPeriod = lib.dateTimeFormatter(period, "yyyyMM");
//    strPeriod = "202412";

    // DBから該当期のデータを削除する
    // トランザクション処理が必要
    service.deleteApproval(strPeriod);
    service.deleteMonthlyPeriod(strPeriod);
    service.deleteAttendanceRecords(
      lib.getStartDate(period),
      lib.getEndDate(period)
    );
    // / 削除処理

    // 期データ登録
    // monthly_period table
    service.registerMonthlyPeriod(
      strPeriod,
      lib.getStartDate(period),
      lib.getEndDate(period),
      form.getMonthlyPeriodForm().getWorkHoursMonth(),
      form.getMonthlyPeriodForm().getWorkHoursMonthHoliday(),
      LocalDateTime.now()
    );


    // 承認登録
    char approvalStatus = '0';
    if(Objects.equals(form.getAction(), "approval-request")) {
      approvalStatus = '1';
    }

    // approval table
    service.registerApproval(
      strPeriod,
      approvalStatus,
      LocalDateTime.of(1900, 1, 1, 0, 0, 0),
      LocalDateTime.of(1900, 1, 1, 0, 0, 0),
      LocalDateTime.now()
    );

    // 勤怠日別データ登録
    for(DailyAttendanceForm obj : form.getDailyAttendanceList()) {
      if(obj.getStartTime() == null) {
        obj.setStartTime(LocalTime.of(0, 0, 0));
      }

      if(obj.getEndTime() == null) {
        obj.setEndTime(LocalTime.of(0, 0, 0));
      }

      if(obj.getStartTimeHoliday() == null) {
        obj.setStartTimeHoliday(LocalTime.of(0, 0, 0));
      }

      if(obj.getEndTimeHoliday() == null) {
        obj.setEndTimeHoliday(LocalTime.of(0, 0, 0));
      }

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

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
//    return "user/test";
  }

  @PostMapping("/{period}")
  public String postUserFormPeriod(MonthlyAttendanceForm form, Model model, @PathVariable("period") YearMonth period) {
    return "user/index";
  }

}
