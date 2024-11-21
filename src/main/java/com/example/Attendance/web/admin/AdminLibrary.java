package com.example.Attendance.web.admin;

import com.example.Attendance.web.user.UserLibrary;
import com.example.Attendance.domain.admin.AttendanceServiceAdmin;
import com.example.Attendance.domain.user.Approval;
import com.example.Attendance.domain.user.AttendanceService;
import com.example.Attendance.web.user.show.ShowMonthlyAttendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.example.Attendance.web.admin.show.ShowPeriod;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminLibrary {

    private final AttendanceService service;
    private final UserLibrary lib;

    // 勤怠表示範囲（前後6カ月設定）
    private final int PeriodRange = 6;

    /**
     * 承認更新種類
     */
    @Getter
    @AllArgsConstructor
    public enum ApprovalUpdate
    {
        APPROVAL_CANCEL("0", "承認を取り消しました"),
        APPROVAL_UPDATE("1", "承認しました");

        // フィールド
        private final String approvalStatus;
        private final String message;
    }

    /**
     * 現在日から期を計算する
     */
    public YearMonth getCurrentPeriod() {
        return lib.getCurrentPeriod();
    }

    /**
     * 承認更新のアクションを取得する
     * @param approvalAction
     * @return
     */
    public ApprovalUpdate getApprovalUpdate(String approvalAction) {
        ApprovalUpdate approvalUpdateResult = ApprovalUpdate.APPROVAL_CANCEL;
        switch (approvalAction)
        {
            case "approval-update" ->
            {
                approvalUpdateResult = ApprovalUpdate.APPROVAL_UPDATE;
            }
            case "approval-cancel" ->
            {
                approvalUpdateResult = ApprovalUpdate.APPROVAL_CANCEL;
            }
        }
        return approvalUpdateResult;
    }

    /**
     * 更新する承認ステータスの取得する
     * @param approvalUpdateResult
     * @return
     */
    public String getApprovalUpdateStatus(ApprovalUpdate approvalUpdateResult)
    {
        return approvalUpdateResult.getApprovalStatus();
    }

    /**
     * 更新後の完了メッセージを取得する
     * @param approvalUpdateResult
     * @return
     */
    public String getApprovalUpdateResultMsg(ApprovalUpdate approvalUpdateResult)
    {
        return approvalUpdateResult.getMessage();
    }

    /**
     * 登録されてる勤怠データの期(ピリオド)を取得する
     */
    public List<ShowPeriod> setShowPeriodData(AttendanceServiceAdmin service, YearMonth currentPeriod)
    {
        // フォーマット(yyyyMM)を定義
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

        // フォーマットを適用
        String startPeriod = currentPeriod.minusMonths(PeriodRange).format(formatter);
        String endPeriod = currentPeriod.plusMonths(PeriodRange).format(formatter);

        // 承認データをDBから取得
        List<Approval> approvalList = service.fetchApprovalWithinPeriod(startPeriod, endPeriod);

        // 承認勤務リスト設定
        List<ShowPeriod> periodList = new ArrayList<>();
        for(Approval approvalData : approvalList)
        {
            String year = approvalData.getPeriod().substring(0,4);
            String month = approvalData.getPeriod().substring(4);
            ShowPeriod periodData = new ShowPeriod(approvalData.getPeriod(), year + '/' + month, year + '-' + month, year, month);
            periodList.add(periodData);
        }

        return periodList;
    }

    /**
     * 画面表示に必要なデータをセットする
     */
    public ShowMonthlyAttendance setAttendanceData(YearMonth period) {
        return lib.setAttendanceData(this.service, period);
    }
}
