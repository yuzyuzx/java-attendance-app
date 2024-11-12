package com.example.Attendance.domain.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import  java.util.List;

@Mapper
public interface AttendanceRepository {

  @Select("select * from attendance_records")
  List<DailyAttendance> fetchAttendanceRecords();

  @Select("select * from attendance_records where date between '2024-10-21' and '2024-11-20' order by date")
  List<DailyAttendance> fetchAttendanceWithinPeriod();
}
