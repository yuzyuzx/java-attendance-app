package com.example.Attendance.domain.user;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AttendanceRepository {

  @Select("select * from attendance_records")
  List<DailyAttendance> fetchAttendanceRecords();

  @Select("select * from attendance_records where date between #{startDate} and #{endDate} order by date")
  List<DailyAttendance> fetchAttendanceWithinPeriod(LocalDate startDate, LocalDate endDate);

  @Select("select * from monthly_period where period = #{period}")
  MonthlyPeriod fetchMonthlyPeriod(String period);

  @Select("select * from approval where period = #{period}")
  Approval fetchApproval(String period);

  @Insert(
    "insert into " +
      " approval " +
      " (`period`, `status`, `requested_at`, `reviewed_at`, `created_at`) " +
      " values " +
      " (#{period}, #{status}, #{requestedAt}, #{reviewedAt}, #{createdAt}) "
  )
  void registerApproval(
    String period,
    char status,
    LocalDateTime requestedAt,
    LocalDateTime reviewedAt,
    LocalDateTime createdAt
  );

  @Insert(
    "insert into " +
      " monthly_period " +
      " (`period`, `start_date`, `end_date`, `work_hours_month`, `work_hours_month_holiday`, `created_at`) " +
      " values " +
      " (#{period}, #{startDate}, #{endDate}, #{workHoursMonth}, #{workHoursMonthHoliday}, #{createdAt}) "
  )
  void registerMonthlyPeriod(
    String period,
    LocalDate startDate,
    LocalDate endDate,
    double workHoursMonth,
    double workHoursMonthHoliday,
    LocalDateTime createdAt
  );

  @Insert(
    "insert into " +
      " attendance_records " +
      " (`date`, `month`, `day`, `day_of_week`, `start_time`, `end_time`, `work_hours`, `start_time_holiday`, `end_time_holiday`, `work_hours_holiday`, `day_type`, `comment`, `holiday_name`) " +
      " values " +
      " (#{date}, #{month}, #{day}, #{dayOfWeek}, #{startTime}, #{endTime}, #{workHours}, #{startTimeHoliday}, #{endTimeHoliday}, #{workHoursHoliday}, #{dayType}, #{comment}, #{holidayName} )"
  )
  void registerDailyAttendanceRecords(
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
  );

  @Delete("delete from approval where period = #{period}")
  void deleteApproval(String period);

  @Delete("delete from monthly_period where period = #{period}")
  void deleteMonthlyPeriod(String period);

  @Delete("delete from attendance_records where date between #{startDate} and #{endDate}")
  void deleteAttendanceRecords(LocalDate startDate, LocalDate endDate);
}
