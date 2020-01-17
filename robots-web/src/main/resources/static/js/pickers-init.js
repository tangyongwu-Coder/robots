
//date picker start

if (top.location != location) {
    top.location.href = document.location.href ;
}
$(function(){
    window.prettyPrint && prettyPrint();
    $.fn.datepicker.defaults.language= "zh-CN";
    $.fn.datepicker.defaults.format = DateUtil.dayPattern;
    $("div[time-type='dayRange']").each(function(index, value) {
        var dayRange = $(this);
        var dateFormat = dayRange.attr('data-date-format');
        var start = dayRange.attr('start');
        var end = dayRange.attr('end');
        start = Objects.isNull(start) ? 'start' : start;
        end = Objects.isNull(end) ? 'end' : end;
        dateFormat = Objects.isNull(dateFormat) ? DateUtil.dayPattern : dateFormat;
        processRange(start,end,dateFormat);
    });
});

function processRange(start,end,dateFormat){
    var picker1Date;
    var picker2Date;
    var startEle = $('.'+start);
    var endEle = $('.'+end);
    var picker1 = startEle.datepicker({
        format : dateFormat,
        onRender: function(date) {
            return date.valueOf() < nowDay.valueOf() ? 'disabled' : '';
        }
    });

    picker1.on('changeDate', function(ev) {
        if(Objects.isNull(picker2Date.date)||ev.date.valueOf() > picker2Date.date.valueOf()){
            var newDate = DateUtil.addDate(ev.date,1);
            picker2Date.setValue(newDate);
        }
        picker1Date.hide();
        endEle[0].focus();
    })
    picker1Date = picker1.data('datepicker');

    var picker2 = endEle.datepicker({
        format : dateFormat,
        onRender: function(date) {
            return date.valueOf() <= picker1Date.date.valueOf() ? 'disabled' : '';
        }
    });
    picker2.on('changeDate', function(ev) {
        picker2Date.hide();
    })
    picker2Date = picker2.data('datepicker');
    startEle.val(DateUtil.formatDate(DateUtil.addDate(nowDay,-1),dateFormat));
    endEle.val(DateUtil.formatDate(nowDay,dateFormat));
}
