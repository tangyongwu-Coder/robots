var ValidatePatten = {
    /** 邮箱校验 */
    EMAIL_REGEX : /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/,
/** 身份证格式校验 */
    ID_CARD_REGEX : /(\\d{15})|(\\d{17}[0-9|X|x])$/,
/** 密码格式校验 */
    PASS_WORD_REGEX : /^(?![^a-zA-Z]+$)(?!\\D+$).{8,}$/,
/** 姓名格式校验 */
    NAME_REGEX : /^[\u0391-\uFFE5-\\·]{1,32}$/,

/** 手机号码格式校验 */
    MOBILE_REGEX : /([1]\\d{10}$)/,

/** 用户名格式校验 */
    USER_NAME_REGEX :  /[a-zA-Z]{1}[a-zA-Z0-9_]{6,15}/,
};


$.validator.addMethod("userNameCheck",function(value,element) {
    return this.optional(element) || ValidatePatten.USER_NAME_REGEX.test(value);
},"用户名格式不正确");

$.validator.addMethod("mobileCheck",function(value,element) {
    return this.optional(element) || ValidatePatten.MOBILE_REGEX.test(value);
},"手机号格式不正确");
$.validator.addMethod("nameCheck",function(value,element) {
    return this.optional(element) || ValidatePatten.NAME_REGEX.test(value);
},"姓名格式不正确");
$.validator.addMethod("passWordCheck",function(value,element) {
    return this.optional(element) || ValidatePatten.PASS_WORD_REGEX.test(value);
},"密码格式不正确");
$.validator.addMethod("idCardCheck",function(value,element) {
    return this.optional(element) || ValidatePatten.ID_CARD_REGEX.test(value);
},"身份证格式不正确");
$.validator.addMethod("emailCheck",function(value,element) {
    return this.optional(element) || ValidatePatten.EMAIL_REGEX.test(value);
},"邮箱格式不正确");

/**时间工具**/
var DataUtil = {
    /**
     * 日期格式:yyyy-MM
     */
     dateMonthPattern : "yyyy-MM",
    /**
     * 日期格式：yyyyMMdd
     */
    datePattern : "yyyyMMdd",
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    fullPattern : "yyyyMMddHHmmss",
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    readPattern : "yyyy-MM-dd HH:mm:ss,SSS",
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    allPattern : "yyyy-MM-dd HH:mm:ss",

    /**
     * 时间全格式
     */
    ALL_PATTERN : "yyyyMMddHHmmssSSS",

    /**
     * 日期格式
     */
    dayPattern : "yyyy-MM-dd",

    /**
     * 将日期格式化成指定格式的字符串
     * @param date 要格式化的日期，不传时默认当前时间，也可以是一个时间戳
     * @param fmt 目标字符串格式，支持的字符有：y,M,d,q,w,H,h,m,S，默认：yyyy-MM-dd HH:mm:ss
     * @returns 返回格式化后的日期字符串
     */
    formatDate : function (date, fmt) {
        date = date == undefined ? new Date() : date;
        date = typeof date == typeof 'number' ? new Date(date) : date;
        fmt = fmt || 'yyyy-MM-dd HH:mm:ss';
        var obj =
        {
            'y': date.getFullYear(), // 年份，注意必须用getFullYear
            'M': date.getMonth() + 1, // 月份，注意是从0-11
            'd': date.getDate(), // 日期
            'q': Math.floor((date.getMonth() + 3) / 3), // 季度
            'w': date.getDay(), // 星期，注意是0-6
            'H': date.getHours(), // 24小时制
            'h': date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, // 12小时制
            'm': date.getMinutes(), // 分钟
            's': date.getSeconds(), // 秒
            'S': date.getMilliseconds() // 毫秒
        };
        var week = ['天', '一', '二', '三', '四', '五', '六'];
        for(var i in obj)
        {
            fmt = fmt.replace(new RegExp(i+'+', 'g'), function(m)
            {
                var val = obj[i] + '';
                if(i == 'w') return (m.length > 2 ? '星期' : '周') + week[val];
                for(var j = 0, len = val.length; j < m.length - len; j++) val = '0' + val;
                return m.length == 1 ? val : val.substring(val.length - m.length);
            });
        }
        return fmt;
    }
}

var Query = {
    get : function(option) {
        $.ajax({
            //接口地址
            url: option.url,
            type: 'GET',
            dataType: "json",//预期服务器返回的数据类型
            data: option.data,
            async: false,
            cache: false,
            contentType: "application/json",
            processData: false,
            success: function(data) {
                option.success(data);
            },
            error:function(data) {
                this.errorCallBack(data);
            }
        });
    },

    post : function(option){
        $.ajax({
            //接口地址
            url: option.url,
            type: 'POST',
            dataType: "json",//预期服务器返回的数据类型
            data: JSON.stringify(option.data),
            async: false,
            cache: false,
            contentType:"application/json",
            processData: false,
            success: function(data) {
                option.success(data);
            },
            error:function(data) {
                this.errorCallBack(data);
            }
        });
    },

    ajaxFrom : function(option) {
        var formData = fromDataArr(option.fromId);
        $.ajax({
            //接口地址
            url: option.url,
            type: 'POST',
            dataType: "json",//预期服务器返回的数据类型
            data: JSON.stringify(formData),
            async: false,
            cache: false,
            contentType:"application/json",
            processData: false,
            success: function(data) {
                //成功的回调
                if (data.success) {
                    window.location.href = option.successUrl;
                } else {
                    if(option.errorFun){
                        option.errorFun(data);
                    }else{
                        SweetAlert.warning('',data.errorMsg);
                    }
                }
            },
            error:function(data) {
                this.errorCallBack(data);
            }
        });
    },

    errorCallBack :function (data){
        SweetAlert.errorFun('',"系统繁忙,请稍后再试");
    }
};

var Objects ={
    isNull :function(data) {
    return  typeof data == typeof undefined || data ==null|| data =='';
    },

    nonNull : function(data) {
    return !this.isNull(data);
    },

    deepCopy :function(obj){
    if(typeof obj != 'object'){
        return obj;
    }
    var newObj = {};
    for ( var attr in obj) {
        newObj[attr] = this.deepCopy(obj[attr]);
    }
    return newObj;
    }
}




var NodeUtil = {
    getChildNodesByClass :function(ele,className){
        var childs = ele.childNodes;
        var result = [];
        for(var i in childs){
            var child = childs[i];
            var cName = child.className;
            if(Objects.isNull(cName)){
                continue;
            }
            if(cName.search(className) != -1){
                result.push(child);
            }
        }
        return result;
    }
}






