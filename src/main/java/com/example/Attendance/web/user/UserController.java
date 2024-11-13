package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final AttendanceService service;
  private final UserLibrary lib;

  @GetMapping
  public String index(Model model) {
    String period = "202411";

    MonthlyAttendance monthlyAttendance = new MonthlyAttendance();
    monthlyAttendance.setApproval(service.fetchApproval(period));
    monthlyAttendance.setMonthlyPeriod(service.fetchMonthlyPeriod(period));
    monthlyAttendance.setDailyAttendance(service.fetchAttendanceWithinPeriod());

//    monthlyAttendance.setYear("2024");
//    monthlyAttendance.setMonth("11");
//    monthlyAttendance.setCurrentPeriod("202411");

    model.addAttribute("data", monthlyAttendance);

    return "user/index";
  }

  @PostMapping
  public String userForm(MonthlyAttendanceForm form, Model model) {
    return "user/index";
  }
}
