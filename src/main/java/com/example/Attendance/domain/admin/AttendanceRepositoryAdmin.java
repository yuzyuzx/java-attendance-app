package com.example.Attendance.domain.admin;

import com.example.Attendance.domain.user.Approval;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AttendanceRepositoryAdmin {

    @Select("select * from approval where period between #{startPeriod} and #{endPeriod} order by period")
    List<Approval> fetchApprovalWithinPeriod(String startPeriod, String endPeriod);

    @Update("update approval set `status` = #{status}, reviewed_at =  FORMATDATETIME(CURRENT_TIMESTAMP, 'yyyy-MM-dd HH:mm:ss') where `period` = #{period}")
    int updateApprovalStatus(String status, String period);

}
