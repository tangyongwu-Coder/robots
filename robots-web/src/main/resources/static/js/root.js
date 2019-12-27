
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
                SweetAlert.warning('',data.errorMsg);
            }
        },
        error: errorCallBack
    });
}


function errorCallBack(data){
    //请求异常的回调
    alert("失败"+JSON.stringify(data));
    SweetAlert.error('',"系统繁忙,请稍后再试");
}