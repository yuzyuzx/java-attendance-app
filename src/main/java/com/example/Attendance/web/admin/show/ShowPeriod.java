package com.example.Attendance.web.admin.show;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowPeriod {
    // 期
    private String period;

    // 期表示名
    private String periodText;

    // 期設定値
    private String periodValue;

    // 年
    private String year;

    // 月
    private String month;
}
