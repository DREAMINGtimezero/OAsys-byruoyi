package com.ruoyi.website.service;

import com.ruoyi.website.domain.Email;
import com.ruoyi.website.domain.RawCustomerMessage;

/**
 * @author GYX
 */
public interface EmailSenderService {
    /**
     * 用邮件记录客户信息
     *
     * @param message 客户信息
     * @return 结果
     */
    boolean recordCustomerMessage(RawCustomerMessage message);

    /**
     * 发送邮件
     * @param email 来自客户的信息
     * @return 发送结果
     */
    boolean sendEmail(Email email);
}
