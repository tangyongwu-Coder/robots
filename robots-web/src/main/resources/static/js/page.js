var table;

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
function initTableConfig(option){
     var tableConfig = {
        "sAjaxSource" : option.url,
        "sAjaxDataProp": isNull(option.result) ? 'result' : option.result,
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
                        alert("Fail to get data!")
                    }
                }
            })
        },
        "bProcessing": true,
        "bServerSide": true,
        "bPaginate" : true,//分页工具条显示
        "bLengthChange" : false,
        "bFilter" : false, //搜索栏
        "bSort" : true, //是否支持排序功能
        "aaSorting": [[0, "desc"]],//默认使用第几列排序
    };
    //中文化界面显示
    tableConfig.oLanguage= language();
    //处理列
    var columns = option.columns;
    var aoColumns = [];
    if(columns){
        for (var i in columns) {
            var columnName = columns[i];
            var aoColumn = initColumns(columnName);
            aoColumns.push(aoColumn);
        }
    }
    tableConfig.aoColumns = aoColumns;
    return tableConfig;
}

function initColumns(columnName) {
   return {
        "mDataProp" : columnName,
        "sDefaultContent" : ""
    };
}
