package com.qcloud.weapp.demo.util;

import org.apache.log4j.Logger;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

/**
 * Created by mrzsh on 2017/10/30.
 */
public class EmailUtil {
    private static final Logger L = Logger.getLogger(EmailUtil.class);
    private Properties props ;
    private Session session;
    private static EmailUtil singleSelf = null;
    public static final String PROPERTIES_PATH ="/mail.properties";
    public EmailUtil(){
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(new File(EmailUtil.class.getClassLoader().getResource("").getPath()+PROPERTIES_PATH)));
            props = new Properties();
            props.load(is);
            props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", props.getProperty("mail.smtp.host"));   // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
            session = Session.getInstance(props);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.error("mail FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
            L.error("mail IOException");
        }
    }

    public void sendEmail(String topic,String text){
        MimeMessage message = null;
        try {
            message = createMimeMessage(props.getProperty("mail.host"), props.getProperty("mail.receiver"),topic,text);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();
            transport.connect(props.getProperty("mail.host"), props.getProperty("mail.password"));
            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());
            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            L.error("mail erro",e);
        }
    }

    public MimeMessage createMimeMessage(String sendMail, String receiveMail,String topic,String text) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "admin", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject(topic, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(text, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    public static EmailUtil getInstance(){
        if(singleSelf == null){
            singleSelf = new EmailUtil();
        }
        return singleSelf;
    }

}
