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
    YearMonth period = lib.getCurrentPeriod(lib.getLocalDate());
//    lib.debugDate(period);

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
  }

  @PostMapping
  public String userForm(MonthlyAttendanceForm form, Model model) {
    return "user/index";
  }

  @GetMapping("/{period}")
  public String test(Model model, @PathVariable("period") YearMonth period) {
//    lib.debugDate(period);

    ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", ShowMonthlyAttendance);

    return "user/index";
  }

}
