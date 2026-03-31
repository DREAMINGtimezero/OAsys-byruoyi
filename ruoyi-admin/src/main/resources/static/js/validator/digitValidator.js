$.validator.addMethod("maxDecimalLength", function(value, element, param) {
    // 允许为空，required 会处理
    if (!value) return true;

    // 移除非数字字符（允许小数点）
    let numStr = value.toString().replace(/[^0-9.]/g, '');
    // 分割整数和小数部分
    let parts = numStr.split('.');
    let decimalPart = parts.length > 1 ? parts[1] : '';
    return decimalPart.length<=param;
},"小数点后数字超过位数限制");
$.validator.addMethod("maxLength", function(value, element, param) {
    maxLen = param;
    // 允许为空，required 会处理
    if (!value) return true;

    // 移除非数字字符（允许小数点）
    let numStr = value.toString().replace(/[^0-9.]/g, '');

    // 分割整数和小数部分
    let parts = numStr.split('.');
    let integerPart = parts[0];
    let decimalPart = parts.length > 1 ? parts[1] : '';

    // 总位数 = 整数位数 + 小数位数
    let totalDigits = integerPart.length + decimalPart.length;
    return totalDigits <= param;
}, "数字总位数超过限制");