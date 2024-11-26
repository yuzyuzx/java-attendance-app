// ページ開始時処理
$(document).ready(function() {
    // 時間要素に対してフォーカスアウトイベント追加
    handleBusinessDayBlur('.businessday-start', '.businessday-end', '.businessday-time', '.businessday-time-total', '.businessday-time-post', '#monthlyPeriodFormWorkHoursMonth', true);
    handleBusinessDayBlur('.businessday-end', '.businessday-start', '.businessday-time', '.businessday-time-total', '.businessday-time-post', '#monthlyPeriodFormWorkHoursMonth', false);
    handleBusinessDayBlur('.holiday-start', '.holiday-end', '.holiday-time', '.holiday-time-total', '.holiday-time-post', '#monthlyPeriodFormWorkHoursMonthHoliday', true);
    handleBusinessDayBlur('.holiday-end', '.holiday-start', '.holiday-time', '.holiday-time-total', '.holiday-time-post', '#monthlyPeriodFormWorkHoursMonthHoliday', false);

    // 表示する勤務表の期を設定
    $('.attendance-period').text($('#year').val() + '年 ' + $('#period').val() + '月 期');

    // Enterキーキャンセル
    $('.enter-cancel').on('keydown', function(event)
    {
        enterCancel(event);
    });

});

// blurイベントを設定
function handleBusinessDayBlur(mainClass, subClass, siblingClass, totalTimeClass, inputTimePostClass, calcResultId, isStart) {
    $(mainClass).blur(function() {
        const mainInputVal = $(this).val(); // 入力された値を取得
        const siblingElement = $(this).parent().siblings(siblingClass);
        const subInputVal = $(this).parent()[isStart ? 'next' : 'prev']().find(subClass).val();
        let inputTimePostElement = $(this).parent().siblings('.otherPostdata').find(inputTimePostClass);

        let w_mainInputVal = mainInputVal;

        // 数値チェック、時間型チェック、00:00～33:29チェック
        if (!inputFormatCheck(mainInputVal))
        {
            // チェックエラーは入力値を空にする
            $(this).val('');
            siblingElement.text('');
            $(inputTimePostElement).val('0.0');
        }
        else
        {
            // 先頭0埋め時刻の場合は先頭文字削除
            if ((w_mainInputVal.charAt(0) === '0' && w_mainInputVal.length >= 5) ||
                (w_mainInputVal.indexOf(":") === -1 && w_mainInputVal.charAt(0) === '0' && w_mainInputVal.length == 4))
            {
                w_mainInputVal = w_mainInputVal.slice(1);
            }

            // 入力値を時刻の形式に直す
            if (w_mainInputVal.indexOf(":") === -1)
            {
                w_mainInputVal = w_mainInputVal.length <= 3 ? w_mainInputVal.slice(0, 1) + ":" +  w_mainInputVal.slice(1) : w_mainInputVal.slice(0, 2) + ":" +  w_mainInputVal.slice(2);
            }

            // 対になる入力値チェック
            if (inputFormatCheck(subInputVal))
            {
                // 時間計算
                siblingElement.text(isStart ? workTimeCalc(w_mainInputVal, subInputVal) : workTimeCalc(subInputVal, w_mainInputVal));
            }
        }

        // 合計実働時間計算・設定
        let attendanceTimeTotal = 0;
        $(siblingClass).parent().each(function(index, element)
        {
            let attendanceTime = $(this).find(siblingClass).text();
            if (attendanceTime.match(/^\d+(\.\d+)?$/) !== null)
                attendanceTimeTotal = attendanceTimeTotal + Number(attendanceTime);
        });
        $(totalTimeClass).text(attendanceTimeTotal);
        $(calcResultId).val(attendanceTimeTotal);

        // 入力値が時刻形式でない場合に時刻形式の値を再設定
        if (mainInputVal !== w_mainInputVal)
            $(this).val(w_mainInputVal);

        // 入力値を登録用の項目へコピー
        if (siblingElement.text() != '')
            $(inputTimePostElement).val(siblingElement.text());

    });
}

// 入力値フォーマットチェック
function inputFormatCheck(inputVal)
{
    // NULLチェック
    if (!inputVal)
    {
        return false;
    }

    // 数値・時刻チェック
    if (((inputVal.match(/^([01]?[0-9]|[02]?[0-9]):([0-5][0-9])$/) !== null) || (inputVal.match(/^(3[0-2]):([0-5][0-9])$/) !== null || inputVal.match(/^(33):([0-2][0-9])$/) !== null))
    || ((inputVal.match(/^([01]?[0-9]|[02]?[0-9])([0-5][0-9])$/) !== null) || (inputVal.match(/^(3[0-2])([0-5][0-9])$/) !== null || inputVal.match(/^(33)([0-2][0-9])$/) !== null))
    )
      return true;
    else
      return false;
}

// 計算処理
function workTimeCalc(starttime, endtime)
{
    let start = starttime.split(':');
    let end = endtime.split(':')

    // startのほうがでかいときendに+24
    if (Number(start[0] + start[1]) > Number(end[0] + end[1]))
    {
      end[0] = Number(end[0]) + 24;
    }

    // 計算用に時間と分をコンバート
    let attendancestart = start[0] + '00';
    let attendanceend = end[0] + '00';
    if (start[1] >= 30)
        attendancestart = Number(attendancestart) + 50;
    if (end[1] >= 30)
        attendanceend = Number(attendanceend) + 50;

    // 対象外時間処理
    //12:00～13:00/18:00～18:30/22:00～22:30/2:00～3:00/6:30～7:30
    adjusttime = 0;

    // 12~13
    if (Number(start[0]) <= 12 && Number(end[0] + end[1]) >= 1230)
    {
        adjusttime = adjusttime + 0.5;
    }
    if (Number(start[0]) <= 12 && Number(end[0] + end[1]) >= 1300)
    {
        adjusttime = adjusttime + 0.5;
    }

    // 18~1830
    if (start[0] <= 18 && Number(end[0] + end[1]) >= 1830)
    {
        adjusttime = adjusttime + 0.5;
    }

    // 22~2230
    if (start[0] <= 22 && Number(end[0] + end[1]) >= 2230)
    {
        adjusttime = adjusttime + 0.5;
    }

    // 26~27
    if (Number(start[0]) <= 26 && Number(end[0] + end[1]) >= 2630)
    {
        adjusttime = adjusttime + 0.5;
    }
    if (Number(start[0]) <= 26 && Number(end[0] + end[1]) >= 2700)
    {
        adjusttime = adjusttime + 0.5;
    }

    // 3030~3130
    if (Number(start[0] + start[1]) <= 3030 && Number(end[0] + end[1]) >= 3100)
    {
        adjusttime = adjusttime + 0.5;
    }
    if (Number(start[0] + start[1]) <= 3030 && Number(end[0] + end[1]) >= 3130)
    {
        adjusttime = adjusttime + 0.5;
    }

    // 計算
    let time = attendanceend - attendancestart;
    return (time / 100 - adjusttime).toFixed(1);
}