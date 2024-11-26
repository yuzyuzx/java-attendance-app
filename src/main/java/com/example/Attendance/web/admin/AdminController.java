package com.example.Attendance.web.admin;

import com.example.Attendance.domain.admin.AttendanceServiceAdmin;
import com.example.Attendance.web.admin.form.MonthlyAttendanceForm;
import com.example.Attendance.web.admin.show.ShowPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.YearMonth;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final AdminLibrary lib;

  private final AttendanceServiceAdmin service;

  /*
  */
  @GetMapping
  public String index(Model model) {

    // 期ドロップダウンリストのデータ設定
    YearMonth currentPeriod = lib.getCurrentPeriod();
    List<ShowPeriod> periodList = lib.setShowPeriodData(service, currentPeriod);
    model.addAttribute("periodList", periodList);

    // 勤務表データ設定(初期表示時は表示しない)
    model.addAttribute("data", null);

    return "admin/index";
  }

  /*
   */
  @PostMapping
  public String index(@RequestParam("periodOption") String periodOption, Model model) {

    // 期ドロップダウンリストのデータ設定
    YearMonth currentPeriod = lib.getCurrentPeriod();
    List<ShowPeriod> periodList = lib.setShowPeriodData(service, currentPeriod);
    model.addAttribute("periodList", periodList);

    // 勤務表データ設定
    if(periodOption == null || periodOption.isEmpty())
    {
      model.addAttribute("data", null);
      model.addAttribute("selectPeriodValue", "default");
    }
    else
    {
      // YearMonth型へ変換
      YearMonth period = YearMonth.parse(periodOption);

      // 勤務表データを取得
      com.example.Attendance.web.user.show.ShowMonthlyAttendance ShowMonthlyAttendance = lib.setAttendanceData(period);
      model.addAttribute("data", ShowMonthlyAttendance);
      model.addAttribute("selectPeriodValue", periodOption);
    }

    return "admin/index";
  }

  @PostMapping(params = "action")
  public String approvalUpdate(MonthlyAttendanceForm form, Model model) {

    try
    {
      // 承認更新処理
      String periodPrm = form.getMonthlyPeriodForm().getPeriod();
      String statusPrm = lib.getApprovalUpdateStatus(lib.getApprovalUpdate(form.getAction()));
      service.updateApprovalStatus(statusPrm, periodPrm);
    }
    catch (Exception ex)
    {
      // 例外発生時はエラーメッセージを画面に表示
      model.addAttribute("resultMsg", "データ更新に失敗しました。");
      return "admin/index";
    }

    // userの表示データを初期化
    model.addAttribute("data", null);

    // 期ドロップダウンリストのデータ設定
    YearMonth currentPeriod = lib.getCurrentPeriod();
    List<ShowPeriod> periodList = lib.setShowPeriodData(service, currentPeriod);
    model.addAttribute("periodList", periodList);
    model.addAttribute("selectPeriodValue", "default");

    // 更新メッセージ設定
    model.addAttribute("resultMsg", lib.getApprovalUpdateResultMsg(lib.getApprovalUpdate(form.getAction())));

    return "admin/index";
  }
}