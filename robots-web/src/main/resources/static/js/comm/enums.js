function enumeration(namesToValues) {
    var enumeration = function() { throw "Can't Instantiate Enumeration"}

    var proto = enumeration.prototype = {
        constructor: enumeration,
        toString: function() { return this.name; },
        valueOf: function() { return this.value; },
        toJSON: function() { return this.name; }
    };

    enumeration.data = [];

    for(name in namesToValues) {
        var e = inherit(proto);
        e.name = name;
        e.value = namesToValues[name];
        enumeration[name] = e;
        enumeration.data.push(e);
    }

    enumeration.foreach = function(f, c) {
        for (var i = 0; i < this.data.length; i++) {
            f.call(c, this.data[i]);
        };
    };

    enumeration.values = function(){
        return this.data;
    }
    return enumeration;
}

function inherit(p) {
    if (p == null) throw TypeError();
    if (Object.create)
        return Object.create(p);
    var t = typeof p;
    if (t !== "object" && t !== "function") throw TypeError();
    function f() {};
    f.prototype = p;
    return new f();
}

var Enums = {
    StatusEnum : enumeration({NORMAL: '正常', DISABLE:'禁用'}),
};

function getEnumCode(key){
    if(Objects.isNull(key)){
        return 'enumKey';
    }
    return key;
}
function getEnumMsg(value){
    if(Objects.isNull(value)){
        return 'enumValue';
    }
    return value;
}

function getUrlData(url) {
    var cache = getCache(url);
    if(Objects.nonNull(cache)){
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
    var enumType = select.attr("data-type");
    var dataMode = select.attr("data-mode");
    select.empty();
    if (typeof url !== typeof undefined) {
       var cache = getCache(url);
        if(Objects.nonNull(cache)){
            var html = getOption(cache,code,msg,dataMode);
            writeOption(select,html,value);
            return;
        }

        Query.post({
            url: '/enums/type',
            data: {enumType : enumType},
            success:function (data) {
                //由于后台传过来的json有个data，在此重命名
                var html = getOption(data.data,code,msg,dataMode);
                writeOption(select,html,value);
                cacheEnum(data.data,url);
            }
        });
    }
}


function getResourceData(resource) {
   return Enums[resource];
}

function getResourceOption(select,code,msg) {
    var resource = select.attr("data-resource");
    var value = select.attr("data-select");
    var dataMode = select.attr("data-mode");
    select.empty();
    if (typeof resource !== typeof undefined) {
        var thisEnum = Enums[resource].data;
        var html = getOption(thisEnum,code,msg,dataMode);
        writeOption(select,html,value);
    }
}
$(document).ready(function() {
    $("select[data-url]").each(function(index, value) {
        var select = $(this);
        getUrlOption(select,'enumKey','enumValue');
    });
    $("select[data-resource]").each(function(index, value) {
        var select = $(this);
        getResourceOption(select,'name','value');
    });
} );

function writeOption(select,html,value) {
    select.append(html); //渲染
    if (typeof value !== typeof undefined) {
        select.val(value);
    }
}

function getOption(data,code,msg,dataMode) {
    var html = "";
    if(Objects.nonNull(dataMode) && 'query' ===dataMode){
        html += "<option value=''>所有</option>";
    }
    for (var e in data) {
        var one = data[e];
        html += '<option value=' + one[code] + '>' + one[msg] + '</option>';
    }
    return html;
}