
var Enums = {
    StatusEnum: [
    {
        code:'NORMAL',
        msg:'正常',
    },{
        code:'DISABLE',
        msg:'禁用',
    }
    ],
}

function getUrlOption(select) {
    var url = select.attr("data-url");
    var value = select.attr("data-selected");
    select.empty();
    if (typeof url !== typeof undefined) {
        Query.post({
            url: '/enums/type',
            data: {},
            success:function (data) {
                //由于后台传过来的json有个data，在此重命名
                var html = writeOption(data.data,'typeCode','typeName');
                select.append(html); //渲染
                if (typeof value !== typeof undefined) {
                    select.val(value);
                }
            }
        });
    }
}

function getResourceOption(select) {
    var resource = select.attr("data-resource");
    var value = select.attr("data-selected");
    select.empty();
    if (typeof resource !== typeof undefined) {
        var thisEnum = Enums[resource];
        var html = writeOption(thisEnum,'code','msg');
        select.append(html); //渲染
        if (typeof value !== typeof undefined) {
            select.val(value);
        }
    }
}
$(document).ready(function() {
    $("select[data-url]").each(function(index, value) {
        var select = $(this);
        getUrlOption(select);
    });
    $("select[data-resource]").each(function(index, value) {
        var select = $(this);
        getResourceOption(select);
    });
} );

function writeOption(data,code,msg) {
    var html = "<option value='ALL'>所有</option>";
    for (var e in data) {
        var one = data[e];
        html += '<option value=' + one[code] + '>' + one[msg] + '</option>';
    }
    return html;
}