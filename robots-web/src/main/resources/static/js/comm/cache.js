
var enumCache = {};

function getCache(url){
    var cache = enumCache[url];
    if(Objects.nonNull(cache)){
        return cache;
    }
    return null;
}
function cacheEnum(data,url) {
    enumCache[url] = data;
}