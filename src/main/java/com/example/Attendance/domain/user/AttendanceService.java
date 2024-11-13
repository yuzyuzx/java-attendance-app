package com.example.Attendance.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final AttendanceRepository repository;

  public List<DailyAttendance> fetchAttendanceRecords() {
    return repository.fetchAttendanceRecords();
  }

  public List<DailyAttendance> fetchAttendanceWithinPeriod(LocalDate startDate, LocalDate endDate) {
    return repository.fetchAttendanceWithinPeriod(startDate, endDate);
  }

  public MonthlyPeriod fetchMonthlyPeriod(String period) {
    return repository.fetchMonthlyPeriod(period);
  }

  public Approval fetchApproval(String period) {
    return repository.fetchApproval(period);
  }
}
