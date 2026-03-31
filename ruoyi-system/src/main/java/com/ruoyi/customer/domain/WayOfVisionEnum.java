package com.ruoyi.customer.domain;

import lombok.Getter;

/**
 * @author YJ
 */

@Getter
public enum WayOfVisionEnum {
    /**
     * 访问方式
     */
    PHONE("电话"),
    OFFLINE("线下拜访"),
    WECHAT("微信沟通"),
    OTHER("其它");
    private final String description;
    WayOfVisionEnum(String description) {
        this.description = description;
    }
}
