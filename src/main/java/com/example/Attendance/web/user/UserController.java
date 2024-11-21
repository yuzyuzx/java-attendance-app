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
    YearMonth period = lib.getCurrentPeriod();

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
//    return "user/test";
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
//    return "user/test";
  }

  @PostMapping
  public String postUserForm(MonthlyAttendanceForm form, Model model) {
    YearMonth period = lib.getCurrentPeriod();

    try {
      lib.registerAttendanceData(period, service, form);
    } catch(Exception e) {
      System.out.println("Error post");
    }

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
//    return "user/test";
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
