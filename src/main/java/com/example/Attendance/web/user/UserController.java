package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.AttendanceService;
import com.example.Attendance.domain.user.DailyAttendance;
import com.example.Attendance.domain.user.MonthlyAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final AttendanceService attendanceService;
  private final UserLibrary lib;

  @GetMapping
  public String index(Model model) {
    MonthlyAttendance monthlyAttendance = new MonthlyAttendance();
    monthlyAttendance.setDailyAttendance(attendanceService.fetchAttendanceWithinPeriod());
    model.addAttribute("data", monthlyAttendance);

    return "user/index";
  }

  @PostMapping
  public String userForm(MonthlyAttendanceForm form, Model model) {
    return "user/index";
  }
}
