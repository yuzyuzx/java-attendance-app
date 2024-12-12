package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.*;
import com.example.Attendance.web.user.form.MonthlyAttendanceForm;
import com.example.Attendance.web.user.show.ShowMonthlyAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.YearMonth;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final AttendanceService service;
  private final UserLibrary lib;

  @GetMapping
  public String index(Model model) {
    // アクセスごとに日時更新を行う
    lib.setLocalDateNow();
    YearMonth period = lib.getCurrentPeriod();

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
  }

  @GetMapping("/{period}")
  public String addPeriodParamIndex(Model model, @PathVariable("period") String pathPeriod) {
    // 不正なURLはユーザートップ画面にリダイレクトする
    if(lib.isValidateYearMonthParam(pathPeriod)) {
      return "redirect:/user";
    }

    YearMonth period = YearMonth.parse(pathPeriod);
    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
  }

  @PostMapping
  public String postUserForm(MonthlyAttendanceForm form, Model model) {
    // 画面を開いた日時の期で登録を行う
    YearMonth period = lib.getCurrentPeriod();

    // 承認済の月であればデータを返すだけで、登録処理は行わない
    if(lib.isApproved(period, service)) {
      ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
      model.addAttribute("data", ShowMonthlyAttendance);
      return "user/index";
    }

    // 勤怠データが入力されていなければ処理を終了する
    if(lib.isEmptyDailyAttendanceData(form.getDailyAttendanceList())) {
      ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
      model.addAttribute("data", ShowMonthlyAttendance);
      return "user/index";
    }

    try {
      lib.registerAttendanceData(period, service, form);
    } catch(Exception e) {
      System.out.println("Error post");
    }

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
  }

  @PostMapping("/{period}")
  public String postUserFormAddPeriodParam(
    MonthlyAttendanceForm form,
    Model model,
    @PathVariable("period") String pathPeriod
  ) {
    // 不正なURLはユーザートップ画面にリダイレクトする
    if(lib.isValidateYearMonthParam(pathPeriod)) {
      return "redirect:/user";
    }

    YearMonth period = YearMonth.parse(pathPeriod);

    // 承認済の月であればデータを返すだけで、登録処理は行わない
    if(lib.isApproved(period, service)) {
      ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
      model.addAttribute("data", ShowMonthlyAttendance);
      return "user/index";
    }

    // 勤怠データが入力されていなければ処理を終了する
    if(lib.isEmptyDailyAttendanceData(form.getDailyAttendanceList())) {
      ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
      model.addAttribute("data", ShowMonthlyAttendance);
      return "user/index";
    }

    try {
      lib.registerAttendanceData(period, service, form);
    } catch(Exception e) {
      System.out.println("Error post");
    }

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
  }

}
