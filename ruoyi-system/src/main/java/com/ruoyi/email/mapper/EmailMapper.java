package com.ruoyi.email.mapper;

import com.ruoyi.email.domain.Mail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailMapper {
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
}
