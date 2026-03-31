package com.ruoyi.website.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.website.domain.Email;
import com.ruoyi.website.domain.RawCustomerMessage;
import com.ruoyi.website.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.StringJoiner;

/**
 * @author GYX
 */
@Slf4j
@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Override
    public boolean recordCustomerMessage(RawCustomerMessage message) {
        String text = new StringJoiner("\n")
                .add("客户姓名: " + message.getName())
                .add("客户所属公司: " + message.getCompany())
                .add("客户电话: " + message.getTelephone())
                .add("客户邮箱: " + message.getEmail())
                .add("消息内容: " + message.getContent())
                .toString();
        Email email = new Email()
                .setFrom(Constants.DEFAULT_EMAIL_SENDER)
                .setTo(Constants.DEFAULT_EMAIL_SENDER).
                setAuthCode(Constants.EMAIL_AUTH_CODE).
                setSubject(message.getSubject()).
                setContent(text);
        return sendEmail(email);
    }

    @Override
    public boolean sendEmail(Email email) {
        // 配置 SMTP 属性
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.qiye.aliyun.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.qiye.aliyun.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // 创建会话
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getFrom(), email.getAuthCode());
            }
        });
        boolean result = true;
        try {
            // 创建邮件对象
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
            // 邮件标题
            message.setSubject(email.getSubject());
            // 邮件正文
            message.setText(email.getContent());

            // 发送邮件
            Transport.send(message);
            log.info("邮件发送成功！");
        } catch (MessagingException e) {
            result = false;
            log.error("邮件发送失败！", e);
        }
        return result;
    }
}
