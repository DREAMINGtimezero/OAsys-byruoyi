package com.ruoyi.email.service;

import com.ruoyi.email.domain.Mail;

import java.util.List;

public interface EmailService {
    /**
     * 查询发送邮件
     *
     * @param mail
     * @return
     */
    public List<Mail> selectEmailList(Mail mail);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    public Mail selectEmailById(Long id);

    /**
     * 新增要发送邮件
     *
     * @param mail
     * @return
     */
    public int insertEmail(Mail mail);

    /**
     * 修改要发送邮件
     *
     * @param mail
     * @return
     */
    public int updateEmail(Mail mail);

    /**
     * 删除要发送邮件
     *
     * @param mail
     * @return
     */
    public int deleteEmailByIds(Mail mail);

    /**
     * 根据 email 表主键 id 查询所有 email_by，
     * 再逐个找到对应 user 的邮箱并发送邮件
     *
     * @param id email 表主键
     */
    boolean sendEmailsByEmailId(Long id);
}
