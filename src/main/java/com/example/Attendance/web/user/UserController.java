package com.example.Attendance.web.user;

import com.example.Attendance.domain.user.AttendanceService;
import com.example.Attendance.domain.user.DailyAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private final AttendanceService attendanceService;

  @GetMapping
  public String index() {
    /*
    期を取得
    承認状態を取得
    勤怠レコードを取得
    URLパラーメータで年月取得
     */


    List<DailyAttendance> dailyAttendance = attendanceService.fetchAttendanceRecords();
    return "user/index";
  }
}
