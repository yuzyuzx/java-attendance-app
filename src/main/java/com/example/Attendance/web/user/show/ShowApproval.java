package com.example.Attendance.web.user.show;

import lombok.Data;

@Data
public class ShowApproval {
  private String period;

  private String status;

  private String requestedAt;

  private String reviewedAt;
}
