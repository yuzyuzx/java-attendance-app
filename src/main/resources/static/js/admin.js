// ページ開始時処理
$(document).ready(function() {

    // 表示する勤務表の期を設定
    $('.attendance-period').text('');
    if ($('#year').val() !== '')
        $('.attendance-period').text($('#year').val() + '年 ' + $('#period').val() + '月 期');

});