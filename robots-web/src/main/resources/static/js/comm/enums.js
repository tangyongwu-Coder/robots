/**
 * 枚举类
 *
 * @author harris.xc
 * @param props  [{key: number|string, value: number|string, ...other}]
 * 栗子：
 *  const StepEnum = new Enum([
 *    { key: 'STEP1', name: '步骤1', value: 1 },
 *    { key: 'SETP2', name: '步骤2', value: 2 },
 *  ]);
 *
 * @class Enum
 *
 * @method get(value) 通过value获取当前列的值
 *                    return { key: 'SETP2', name: '步骤2', value: 2 }
 *
 * @returns {key1: number|string, key2: number|string}
 * {
 *   CREATE: 1,
 *   APPROVED: 2,
 * }
 */
export default class Enum {
    /**
     * 初始化
     * @param {Array} props []
     */
    constructor(props = []) {
        this.__props = {};
        if (props.length) {
            props.forEach((element) => {
                if (element.key && element.value) {
                    this[element.key] = element.value;
                    this.__props[element.value] = element;
                } else {
                    console.error('Enum缺少必要的key或value');
                }
            });
        }
    }

    /**
     * 根据value获取对象值
     * @param {string|number} value 状态值
     */
    get(value) {
        return this.__props[value];
    }

    /**
     * 获取枚举数组
     */
    getArray() {
        const arr = [];
        for (const key in this.__props) {
            if (Object.prototype.hasOwnProperty.call(this.__props, key)) {
                arr.push(this.__props[key]);
            }
        }
        return arr;
    }
}
let SizeEnum = new Enum([
    { key: 'STEP1', name: '步骤1', value: 1 },
    { key: 'SETP2', name: '步骤2', value: 2 }
]);
var Enums = {
    StatusEnum: [{
        enumKey:'NORMAL',
        enumValue:'正常',
    },{
        enumKey:'DISABLE',
        enumValue:'禁用',
    }],
    SizeEnum : new Enum([
        { key: 'STEP1', name: '步骤1', value: 1 },
        { key: 'SETP2', name: '步骤2', value: 2 }
    ]),
    StatusEnum1 :{
        NORMAL: '正常',
        DISABLE: '禁用',
        LARGE: 3,
        properties: {
            1: {code: "small", msg: '禁用'},
            2: {name: "medium", msg: 2},
        }
    }

}

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
    var enumType = select.attr("data-type");
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
            data: {enumType : enumType},
            success:function (data) {
                //由于后台传过来的json有个data，在此重命名
                var html = getOption(data.data,code,msg);
                writeOption(select,html,value);
                cacheEnum(data.data,url);
            }
        });
    }
}


function getResourceData(resource) {
    var cache = getCache(resource);
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
        getUrlOption(select,'enumKey','enumValue');
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