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


function get(option){
    $.ajax({
        //接口地址
        url: option.url,
        type: 'GET',
        dataType: "json",//预期服务器返回的数据类型
        data: option.data,
        async: false,
        cache: false,
        contentType:"application/json",
        processData: false,
        success: option.callBack,
        error: errorCallBack
    });
}

function post(option){
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
        success: option.callBack,
        error: errorCallBack
    });
}


function ajaxFrom(option) {
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
function isNull(data) {
    return data ==null|| data =='';
}

function nonNull(data) {
    return !isNull(data);
}

function errorCallBack(data){
    SweetAlert.error('',"系统繁忙,请稍后再试");
}

function showErrorMsg(errorLabel,msg){
    errorLabel.style.display = 'block';
    errorLabel.innerText = msg;
}

function hideErrorMsg(errorLabel){
    errorLabel.style.display = 'none';
    errorLabel.innerText = '';
}