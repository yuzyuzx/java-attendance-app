package com.example.Attendance.domain.admin;

import com.example.Attendance.domain.user.Approval;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceAdmin {

    private final AttendanceRepositoryAdmin repository;

    public List<Approval> fetchApprovalWithinPeriod(String startPeriod, String endPeriod) {
        return repository.fetchApprovalWithinPeriod(startPeriod, endPeriod);
    }

    public int updateApprovalStatus(String status, String period) {
        return repository.updateApprovalStatus(status, period);
    }
}
