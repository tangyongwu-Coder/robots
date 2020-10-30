package com.sirius.robots.manager.util;

import com.sirius.robots.comm.enums.EmailTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/28
 */
@Slf4j
@Component
public class EmailUtil {
    @Autowired
    JavaMailSender mailSender;

    public void sendSimpleMail(String email,String msg,EmailTypeEnum emailType) {
        // 普通文本邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送方,需要写完整邮箱地址
        simpleMailMessage.setFrom("sgzmsrobots@163.com");
        //邮件接收方邮箱
        simpleMailMessage.setTo(email);
        //邮件标题
        simpleMailMessage.setSubject(emailType.getDesc());
        //邮件内容
        String mode = emailType.getMode();
        simpleMailMessage.setText(mode.replaceFirst("\\{}", msg));
        simpleMailMessage.setSentDate(new Date());
        //发送邮件
        mailSender.send(simpleMailMessage);
    }


    public void sendHtml() throws MessagingException {
        // html邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //邮件发送方
        helper.setFrom("authservice@126.com");
        //邮件接收方邮箱
        helper.setTo("1154365135@qq.com");
        //邮件标题
        helper.setSubject("测试HTML~~");
        //邮件内容
        helper.setText("<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>菜鸟教程(runoob.com)</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>我的第一个标题</h1>\n" +
                "    <p style=\"color: blue;\">我的第一个段落。</p>\n" +
                "</body>\n" +
                "</html>", true);
        //发送邮件
        mailSender.send(mimeMessage);
    }
}