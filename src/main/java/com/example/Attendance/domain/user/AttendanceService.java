package com.example.Attendance.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final AttendanceRepository repository;

  public List<DailyAttendance> fetchAttendanceRecords() {
    return repository.fetchAttendanceRecords();
  }

  public List<DailyAttendance> fetchAttendanceWithInPeriod(LocalDate startDate, LocalDate endDate) {
    return repository.fetchAttendanceWithinPeriod(startDate, endDate);
  }

  public MonthlyPeriod fetchMonthlyPeriod(String period) {
    return repository.fetchMonthlyPeriod(period);
  }

  public Approval fetchApproval(String period) {
    return repository.fetchApproval(period);
  }

  @Transactional
  public void registerMonthlyPeriod(
    String period,
    LocalDate startDate,
    LocalDate endDate,
    double workHoursMonth,
    double workHoursMonthHoliday,
    LocalDateTime createdAt
  ) {
    repository.registerMonthlyPeriod(
      period,
      startDate,
      endDate,
      workHoursMonth,
      workHoursMonthHoliday,
      createdAt
    );
  }

  @Transactional
  public void registerApproval(
    String period,
    char status,
    LocalDateTime requestedAt,
    LocalDateTime reviewedAt,
    LocalDateTime createdAt
  ) {
    repository.registerApproval(period, status, requestedAt, reviewedAt, createdAt);
  }

  @Transactional
  public void registerDailyAttendanceRecords(
    LocalDate date,
    String month,
    String day,
    String dayOfWeek,
    String startTime,
    String endTime,
    double workHours,
    String startTimeHoliday,
    String endTimeHoliday,
    double workHoursHoliday,
    char dayType,
    String comment,
    String holidayName
  ) {
    repository.registerDailyAttendanceRecords(
      date,
      month,
      day,
      dayOfWeek,
      startTime,
      endTime,
      workHours,
      startTimeHoliday,
      endTimeHoliday,
      workHoursHoliday,
      dayType,
      comment,
      holidayName
    );
  }

  @Transactional
  public void deleteApproval(String period) {
    repository.deleteApproval(period);
  }

  @Transactional
  public void deleteMonthlyPeriod(String period) {
    repository.deleteMonthlyPeriod(period);
  }

  @Transactional
  public void deleteAttendanceRecords(LocalDate startDate, LocalDate endDate) {
    repository.deleteAttendanceRecords(startDate, endDate);
  }
}
