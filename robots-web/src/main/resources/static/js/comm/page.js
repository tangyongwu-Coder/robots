var table;

var queryFromId;

function language(){
    return {
        "sProcessing" : "正在获取数据，请稍后...",
        "sLengthMenu" : "显示 _MENU_ 条",
        "sZeroRecords" : "没有您要搜索的内容",
        "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
        "sInfoEmpty" : "记录数为0",
        "sInfoFiltered" : "(全部记录数 _MAX_ 条)",
        "sInfoPostFix" : "",
        "sSearch" : "搜索",
        "sUrl" : "",
        "oPaginate": {
            "sFirst" : "第一页",
            "sPrevious" : "上一页",
            "sNext" : "下一页",
            "sLast" : "最后一页"
        }
    };
}
$(document).ready(function() {
    $("button[data-type='reset']").each(function(index, value) {
        var button = $(this);
        reset(button);
    });
    $("button[data-type='query']").each(function(index, value) {
        var button = $(this);
        query(button);
    });
});

function query(button) {
    button.click(function () {
        table.fnDraw();
    })
}

function reset(button){
    button.click(function () {
        if (typeof queryFromId !== typeof undefined) {
            document.getElementById(queryFromId).reset();
        }
        table.fnDraw();
    })
}
function pageQuery(aoData){
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
    var query = fromDataArr(queryFromId);
    query.pageDTO = pageDTO;
    return query;
}

function pageResult(data){
    var result = {};
    result.result = data.result;
    result.draw = data.page;
    result.iTotalRecords = data.count;
    result.iTotalDisplayRecords = data.count;
    return result;
}

//表格数据类型
var dataType = {
    STRING:'STRING',
    DATA_DAY:'DATA_DAY',
    DATA_TIME:'DATA_TIME',
    STATUS:'STATUS',
    ENUM_URL:'ENUM_URL',
    ENUM_RESOURCE:'ENUM_RESOURCE',
    OPERATE:'OPERATE',
}

function initTable(option){
     var tableConfig = {
        "sAjaxSource" : option.url,
        "sAjaxDataProp": Objects.isNull(option.result) ? 'result' : option.result,
        //服务器端，数据回调处理
        "fnServerData" : function(sSource, aoData, fnCallback) {
            Query.post({
                url: sSource,
                data: pageQuery(aoData),
                success: function (data) {
                    if (data.success) {
                        var result = pageResult(data.data);
                        fnCallback(result);
                    } else {
                        SweetAlert.warning('',data.errorMsg);
                    }
                }
            })
        },
        "bProcessing": true,
        "bServerSide": true,
        "bPaginate" : true,//分页工具条显示
        "bLengthChange" : false,
        "bFilter" : false, //搜索栏
        "bSort" : false, //是否支持排序功能
        "aaSorting": [[0, "desc"]],//默认使用第几列排序

    };
    //中文化界面显示
    tableConfig.oLanguage= language();
    //处理列
    var columns = option.columns;
    var aoColumns = [];
    if(columns){
        for (var i in columns) {
            var column = columns[i];
            var aoColumn = initColumn(column);
            aoColumns.push(aoColumn);
        }
    }
    tableConfig.aoColumns = aoColumns;

    var fromId = option.fromId;
    queryFromId = option.queryFromId;
    table = $('#'+fromId).DataTable(tableConfig);
}

function initColumn(column) {
    var name = column.name;
    var type = getType(column.type);
    var columnConfig = {};
    columnConfig.mDataProp = name;
    columnConfig.sDefaultContent = "";
    switch (type){
        case dataType.ENUM_URL:
            var enumUrl = column.enumUrl;
            var enumData = getUrlData(enumUrl);
            var enumCode = getEnumCode(column.enumCode);
            var enumMsg = getEnumMsg(column.enumMsg);
            columnConfig.mRender = function (data, type, full) {
                return getDescByCode(enumData, data, enumCode, enumMsg);
            };
            break;
        case dataType.ENUM_RESOURCE:
            var enumResource = column.enumResource;
            var enumData = getResourceData(enumResource);
            var enumCode = getEnumCode(column.enumCode);
            var enumMsg = getEnumMsg(column.enumMsg);
            columnConfig.mRender = function (data, type, full) {
                return getDescByCode(enumData, data, enumCode, enumMsg);
            };
            break;
        case dataType.DATA_DAY:
            columnConfig.mRender = function (data, type, full) {
                return DataUtil.formatDate(data,DataUtil.dayPattern);
            };
            break;
        case dataType.DATA_TIME:
            columnConfig.mRender = function (data, type, full) {
                return DataUtil.formatDate(data,DataUtil.allPattern);
            };
            break;
        case dataType.STATUS:
            columnConfig.fnCreatedCell = function (nTd, sData, oData, iRow, iCol) {
                nTd.innerHTML = '';
                var input = document.createElement('input');
                input.setAttribute('type','checkbox');
                input.className = 'js-switch';
                nTd.appendChild(input);
                var checked = sData == 'NORMAL';
                var chery = new Switchery(input,{
                    checked: checked
                });
                chery.switcher.addEventListener('click', function(event) {
                    statusChange(chery,column,oData);
                });
            };
            break;
        case dataType.OPERATE:
            columnConfig.fnCreatedCell = function (nTd, sData, oData, iRow, iCol) {
                nTd.innerHTML = '';
                var editBtn = document.createElement('input');
                editBtn.setAttribute('type','button')
                editBtn.setAttribute('data-toggle','modal');
                editBtn.value = '修改';
                editBtn.className = 'btn btn-primary';
                nTd.appendChild(editBtn);
                var deleteBtn = document.createElement('input');
                deleteBtn.setAttribute('type','button');
                deleteBtn.value = '删除';
                deleteBtn.className = 'btn btn-primary';
                nTd.appendChild(deleteBtn);
                editBtn.addEventListener('click', function(event) {
                    column.editFun(oData);
                });
                deleteBtn.addEventListener('click', function(event) {
                    column.deleteFun(oData);
                });
            };
            break;
        default:
            break;
    }
    return columnConfig;
}

function statusChange(chery,column,oData){
    var changeUrl = column.url;
    if(Objects.isNull(changeUrl)){
      return;
    }
    var data = Objects.deepCopy(oData);
    data[column.name] = chery.isChecked();
    Query.post({
        url: changeUrl,
        data: data,
        success: function (data) {
            if (data.success) {
                window.location.href = option.successUrl;
            } else {
                SweetAlert.warning('',data.errorMsg);
            }
        }
    })
}

function getDescByCode(enumData,code,enumCode,enumMsg){
    if(Objects.isNull(enumData)){
        return null;
    }
    for(var i in enumData){
        var data = enumData[i];
        if(code == data[enumCode]){
            return data[enumMsg];
        }
    }
    return code;
}

function getType(type){
    if(Objects.isNull(type)){
        return dataType.STRING;
    }
    return type;
}
