package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.AttendanceService;
import com.example.Attendance.domain.user.DailyAttendance;
import com.example.Attendance.domain.user.MonthlyAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final AttendanceService attendanceService;

  @GetMapping
  public String index(Model model) {

    // 仮データ作成
    MonthlyAttendance monthlyAttendance = new MonthlyAttendance();
    monthlyAttendance.setYear("2024");
    monthlyAttendance.setPeriod("11");
    monthlyAttendance.setStartDate(LocalDate.of(2024, 10, 21));
    monthlyAttendance.setEndDate(LocalDate.of(2024, 11, 20));
    monthlyAttendance.setCurrentMonth("202411");
    monthlyAttendance.setPreviousMonth("202410");
    monthlyAttendance.setNextMonth("202412");
    monthlyAttendance.setWorkHoursMonth(100.0);
    monthlyAttendance.setWorkHoursMonthHoliday(10.5);
    monthlyAttendance.setApplovalStatus('0');

    List<DailyAttendance> dailyAttendance = attendanceService.fetchAttendanceRecords();
//    for(DailyAttendance obj : dailyAttendance) {
//      if(Objects.equals(obj.getDayOfWeek(), "6")) {
//        obj.setDayOfWeek("土");
//      }
//    }
    monthlyAttendance.setDailyAttendance(dailyAttendance);

    model.addAttribute("data", monthlyAttendance);

    return "user/index";
  }

  @PostMapping
  public String userForm(MonthlyAttendanceForm form, Model model) {
    MonthlyAttendance monthlyAttendance = new MonthlyAttendance();
    monthlyAttendance.setYear("2024");
    monthlyAttendance.setPeriod("11");
    monthlyAttendance.setStartDate(LocalDate.of(2024, 10, 21));
    monthlyAttendance.setEndDate(LocalDate.of(2024, 11, 20));
    monthlyAttendance.setCurrentMonth("202411");
    monthlyAttendance.setPreviousMonth("202410");
    monthlyAttendance.setNextMonth("202412");
//    monthlyAttendance.setWorkHoursMonth(100.0);
    monthlyAttendance.setWorkHoursMonth(form.getWorkHoursMonth());
    monthlyAttendance.setWorkHoursMonthHoliday(10.5);
    monthlyAttendance.setApplovalStatus('0');

    List<DailyAttendance> dailyAttendance = attendanceService.fetchAttendanceRecords();
//    for(DailyAttendance obj : dailyAttendance) {
//      if(Objects.equals(obj.getDayOfWeek(), "6")) {
//        obj.setDayOfWeek("土");
//      }
//    }
    monthlyAttendance.setDailyAttendance(dailyAttendance);

    model.addAttribute("data", monthlyAttendance);
    return "user/index";
  }
}
