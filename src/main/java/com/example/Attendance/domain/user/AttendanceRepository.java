package com.example.Attendance.domain.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import  java.util.List;

@Mapper
public interface AttendanceRepository {

  @Select("select * from attendance_records")
  List<DailyAttendance> fetchAttendanceRecords();

  @Select("select * from attendance_records where period = #{period}")
  List<DailyAttendance> fetchAttendanceWithinPeriod(String period);
}
