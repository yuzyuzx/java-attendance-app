package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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

    MonthlyAttendance monthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", monthlyAttendance);

    return "user/index";
  }

  @PostMapping
  public String userForm(MonthlyAttendanceForm form, Model model) {
    return "user/index";
  }

  @GetMapping("/{period}")
  public String test(Model model, @PathVariable("period") YearMonth period) {
//    lib.debugDate(period);
    MonthlyAttendance monthlyAttendance = lib.setAttendanceData(service, period);
    model.addAttribute("data", monthlyAttendance);

    return "user/index";
  }

}
