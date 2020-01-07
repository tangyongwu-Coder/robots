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
            success: option.success,
            error: errorCallBack
        });
    },

    post : function(option){
        $.ajax({
            //接口地址
            url: option.url,
            type: 'POST',
            dataType: "json",//预期服务器返回的数据类型
            data: option.data,
            async: false,
            cache: false,
            contentType:"application/json",
            processData: false,
            success: option.success,
            error: errorCallBack
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
            error: errorCallBack
        });
    }
};

function pageQuery(aoData){
    var query = {};
    var pageDTO = {};
    var start = 0;
    var pageSize = 10;
    for(var i = 0 ;i<aoData.length-1 ;i++){
        var data = aoData[i];
        if('iDisplayStart'==data.name){
            start = data.value;
        }else if('iDisplayLength'==data.name){
            pageSize = data.value;
        }
    }
    pageDTO.page = start/pageSize + 1;
    pageDTO.page_size = pageSize;
    query.pageDTO = pageDTO;
    return JSON.stringify(query);
}

function pageResult(data){
    var result = {};
    result.result = data.result;
    result.draw = data.page;
    result.iTotalRecords = data.count;
    result.iTotalDisplayRecords = data.count;
    return result;
}


function isNull(data) {
    return data ==null|| data =='';
}

function nonNull(data) {
    return !isNull(data);
}

function errorCallBack(data){
    SweetAlert.error('',"系统繁忙,请稍后再试");
}


