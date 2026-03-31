package com.ruoyi.email.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.email.domain.Mail;
import com.ruoyi.email.mapper.EmailMapper;
import com.ruoyi.email.service.EmailService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.website.domain.Email;
import com.ruoyi.website.service.EmailSenderService;
import com.ruoyi.work.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl extends BaseServiceImpl implements EmailService {
    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public List<Mail> selectEmailList(Mail mail) {
        List<Mail> mails = emailMapper.selectEmailList((Mail) initDomainCreate(mail));
        createByReplace(mails);
        for (Mail m : mails) { //在查询中，使数据库中的loginName转变为userName
            if (StringUtils.isNotBlank(m.getEmailBy())) {
                List<String> loginList = Arrays.asList(m.getEmailBy().split(","));
                List<String> nameList = sysUserMapper.selectUserNamesByLogins(loginList);
                m.setEmailBy(StringUtils.join(nameList, ","));
            }
        }
        return mails;
    }

    @Override
    public Mail selectEmailById(Long id) {
        return emailMapper.selectEmailById(id);
    }

    @Override
    public int insertEmail(Mail mail) {
        return emailMapper.insertEmail((Mail)initDomainCreate(mail));
    }

    @Override
    public int updateEmail(Mail mail) {
        return emailMapper.updateEmail((Mail) initDomainUpdate(mail));
    }

    @Override
    public int deleteEmailByIds(Mail mail) {
        return emailMapper.deleteEmailByIds((Mail)initDomainUpdate (mail));
    }

    @Override
    public boolean sendEmailsByEmailId(Long id) {
        Mail mail = emailMapper.selectEmailById(id);
        // 根据发送人登录名字去找发送人的邮件和授权码
        Mail senderParam = new Mail();
        senderParam.setCreateBy(mail.getCreateBy());
        List<SysUser> senderUsers = sysUserMapper.selectEmailByLoginName(senderParam);
        if(StringUtils.isEmpty(senderUsers.get(Constants.ZERO).getRemark())){
            return false;
        }
        // 根据接收人登录名字去找接收人的邮件
        Mail emailParam = new Mail();
        emailParam.setEmailBy(mail.getEmailBy());
        List<SysUser> emailUsers = sysUserMapper.selectEmailByLoginName(emailParam);
        // 正式发送邮件
        Email sendEmail = new Email(senderUsers.get(0).getEmail()
                ,emailUsers.stream().map(SysUser::getEmail).filter(Objects::nonNull).collect(Collectors.joining(","))
                ,mail.getEmailSubject()
                ,mail.getEmailDetail()
                ,senderUsers.get(0).getRemark());
        // 发送成功
        if (emailSenderService.sendEmail(sendEmail)){
            Mail success = new Mail();
            success.setId(id);
            success.setStatus(2);
            success.setEmailTime(DateUtils.getNowDate());
            emailMapper.updateEmail(success);
            return true;
        }
        return false;
    }
}
