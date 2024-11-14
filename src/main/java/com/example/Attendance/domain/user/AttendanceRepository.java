package com.example.Attendance.domain.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import  java.util.List;

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


}
