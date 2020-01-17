//获取指定form中的所有的<input>对象
function getElements(formId) {
    var from = document.getElementById(formId);
    var data = [];
    processEle(from,data,'input');
    processEle(from,data,'select');
    return data;
}

function processEle(from,data,type) {
    var tagElements = from.getElementsByTagName(type);
    for (var j = 0; j < tagElements.length; j++){
        data.push(tagElements[j]);
    }
}

//获取单个input中的【name,value】数组
function inputSelector(element) {
    if (element.checked)
        return [element.name, element.value];
}

function input(element) {
    if (typeof element.type == typeof undefined) {
        return false;
    }
    var tagName = element.tagName.toLowerCase();
    if('input' ==tagName){
        switch (element.type.toLowerCase()) {
            case 'submit':
            case 'hidden':
            case 'password':
            case 'email':
            case 'text':
                return [element.name, element.value];
            case 'checkbox':
            case 'radio':
                return inputSelector(element);
        }
    }else if('select' == tagName){
        return [element.name, element.value];
    }
    return false;
}

//组合URL
function serializeElement(element) {
    var method = element.tagName.toLowerCase();
    var parameter = input(element);

    if (parameter) {
        var key = encodeURIComponent(parameter[0]);
        if (key.length == 0){
            return;
        }
        if(parameter[1].constructor != Array){
            parameter[1] = [parameter[1]];
        }
        var values = parameter[1];
        var results = [];
        for (var i=0; i<values.length; i++) {
            results.push(key + '=' + encodeURIComponent(values[i]));
        }
        return results.join('&');
    }
}

//调用方法
function serializeForm(formId) {
    var elements = getElements(formId);
    var queryComponents = [];

    for (var i = 0; i < elements.length; i++) {
        var queryComponent = serializeElement(elements[i]);
        if (queryComponent)
            queryComponents.push(queryComponent);
    }

    return queryComponents.join('&');
}

//调用方法
function fromDataArr(formId) {
    var elements = getElements(formId);
    var data = {};

    for (var i = 0; i < elements.length; i++) {
        var parameter = input(elements[i]);
        if (parameter) {
            var key = parameter[0];
            var encodeKey = encodeURIComponent(key);
            if (encodeKey.length == 0) {
                continue;
            }
            deep(data,key,parameter[1]);
        }
    }
    return data;
}

function deep(data,key,value){
    if(key.indexOf('[')!= -1){
        //数组
        processList(data,key,value);
    }else if(key.indexOf('.')!= -1){
        //对象
        processMap(data,key,value);
    }
    data[key] = value;
}



function processMap(data,key,value){
    var splits = key.split('.',2);
    var key1 = splits[0];
    var key2 = splits[1];
    var data1 = data[key1];
    if(Objects.isNull(data1)){
        data1 = {};
    }
    deep(data1,key2,value);
    data[key1] = data1;
}

function processList(data,key,value){
    var splits = key.split('[',2);
    var key1 = splits[0];
    var splits2 = splits[1].split(']');
    var idx = splits2[0];
    var key2 = splits2[1];
    var array = data[key1];
    var data1;
    if(Objects.isNull(array)){
        array = [];
    }else{
        data1 = array[idx];
    }
    if(Objects.isNull(data1)){
        data1 = {};
    }
    deep(data1,key2,value);
    array[idx] = data1;
    data[key1] = array;

}


/** 赋值 **/

function setModalVal(modalId,data){
    $("#"+modalId+" input").each(function(index, element) {
        var mapping = $(this).attr("data-mapping");
        if (typeof mapping === typeof undefined) {
            return;
        }
        var name = element.name;
        var value = data[name];
        if(Objects.isNull(value)){
            return;
        }
        switch (element.type.toLowerCase()) {
            case 'hidden':
            case 'password':
            case 'email':
            case 'text':
                element.value = value;
                break;
            case 'checkbox':
            case 'radio':
                element.value = value;
                break;
            default :
                break;
        }
    });
    $("#"+modalId+" select").each(function(index, element) {
        var select = $(this);
        var mapping = select.attr("data-mapping");
        if (typeof mapping === typeof undefined) {
            return;
        }
        var name = element.name;
        var value = data[name];
        if(Objects.isNull(value)){
            return;
        }
        select.val(value);
    });
}
