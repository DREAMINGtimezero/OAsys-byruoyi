/**
 * 防抖定时器
 */
let timer = undefined;

/**
 * 防抖函数
 * @param callback 回调函数
 * @param params 参数
 */
function antiShake(callback,params)
{
    if($.isFunction(callback)&&$.common.isEmpty(timer))
    {
        timer = setTimeout(function(){
            clearTimeout(timer);
            timer = undefined;
        },500);
        callback(params);
    }
}
