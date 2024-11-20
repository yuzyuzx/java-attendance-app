package com.example.Attendance.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Approval {
  private String period;

  private char status;

  private LocalDateTime requestedAt;

  private LocalDateTime reviewedAt;

  private LocalDateTime createdAt;
}
