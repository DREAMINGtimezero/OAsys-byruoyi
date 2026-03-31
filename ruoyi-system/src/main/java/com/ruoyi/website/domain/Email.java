package com.ruoyi.website.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GYX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Email {
    /**
     * 发件人邮箱
     */
    private String from;
    /**
     * 收件人
     */
    private String to;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

    /**
     * 发送方授权码(不是邮箱登录密码)
     */
    private String authCode;
}
