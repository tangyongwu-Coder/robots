var enumCache = {};

var Enums = {
    StatusEnum: [{
        enumKey:'NORMAL',
        enumValue:'正常',
    },{
        enumKey:'DISABLE',
        enumValue:'禁用',
    }],
}

function getEnumCode(key){
    if(isNull(key)){
        return 'enumKey';
    }
    return key;
}
function getEnumMsg(value){
    if(isNull(value)){
        return 'enumValue';
    }
    return value;
}

function getUrlData(url) {
    var cache = getCache(url);
    if(nonNull(cache)){
        return cache;
    }
    Query.post({
        url: url,
        data: {},
        success:function (data) {
            cache = data.data;
            cacheEnum(cache,url);
        }
    });
    return cache;
}
function getUrlOption(select,code,msg) {
    var url = select.attr("data-url");
    var value = select.attr("data-select");
    select.empty();
    if (typeof url !== typeof undefined) {
       var cache = getCache(url);
        if(nonNull(cache)){
            var html = getOption(cache,code,msg);
            writeOption(select,html,value);
            return;
        }

        Query.post({
            url: '/enums/type',
            data: {},
            success:function (data) {
                //由于后台传过来的json有个data，在此重命名
                var html = getOption(data.data,code,msg);
                writeOption(select,html,value);
                cacheEnum(data.data,url);
            }
        });
    }
}
function getCache(url){
    var cache = enumCache[url];
    if(nonNull(cache)){
        return cache;
    }
    return null;
}
function cacheEnum(data,url) {
    enumCache[url] = data;
}

function getResourceOption(select,code,msg) {
    var resource = select.attr("data-resource");
    var value = select.attr("data-select");
    select.empty();
    if (typeof resource !== typeof undefined) {
        var thisEnum = Enums[resource];
        var html = getOption(thisEnum,code,msg);
        writeOption(select,html,value);
    }
}
$(document).ready(function() {
    $("select[data-url]").each(function(index, value) {
        var select = $(this);
        getUrlOption(select,'typeCode','typeName');
    });
    $("select[data-resource]").each(function(index, value) {
        var select = $(this);
        getResourceOption(select,'enumKey','enumValue');
    });
} );

function writeOption(select,html,value) {
    select.append(html); //渲染
    if (typeof value !== typeof undefined) {
        select.val(value);
    }
}

function getOption(data,code,msg) {
    var html = "<option value=''>所有</option>";
    for (var e in data) {
        var one = data[e];
        html += '<option value=' + one[code] + '>' + one[msg] + '</option>';
    }
    return html;
}