package com.mcin.email;


import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Mcin on 2017/5/17.
 * 邮箱发送
 */
public class Email {
    private static final Logger logger = Logger.getLogger(Email.class);


    public static final String HOST = "smtp.163.com"; // 发送邮件的服务器地址
    public static final String PROTOCOL = "smtp"; // 发送邮件的协议
    public static final int PORT = 25;
    public static final String EMAIL_FROM = "mcin123@163.com";//发件人的email
    public static final String EMAIL_PWD = "mcin123";//发件人密码


    /**
     * 获取Session
     * @return
     */
    public static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置服务器地址
        props.put("mail.store.protocol" , PROTOCOL);//设置协议
        props.put("mail.smtp.port", PORT);//设置端口
        props.put("mail.smtp.auth" , true);

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PWD);
            }
        };
        Session session = Session.getDefaultInstance(props , authenticator);

        return session;
    }





    /**
     * 发送邮件信息的内容
     * @param host
     * @param email
     * @param activeCode
     * @param ch
     * @return
     */
    public static String sendEmailcentot(String host,String email,String activeCode,char ch) {
        StringBuffer sb = new StringBuffer("点击下面链接激活账号，48小时内有效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=");
        sb.append(ch);
        sb.append(host);
        sb.append("/home/activeEmail.json?action=activate&email=");
        sb.append(email);
        sb.append("&activeCode=");
        sb.append(activeCode);
        sb.append("\">"+host+"/user/register.json?action=activate&email=");
        sb.append(email);
        sb.append("&activeCode=");
        sb.append(activeCode);
        sb.append("</a>");
        return sb.toString();
    }

    /**
     * 发送邮件
     * @param toEmail
     * @param host
     * @return
     */
    public static Integer sendEmail (String toEmail,String host,String activeCode){
        Integer sendResulr = 0;
        char ch = '"';
        String content = sendEmailcentot(host,toEmail,activeCode,ch);
        Session session = getSession();
        try {
            logger.info("--开始发送邮件 --"+"邮箱 == "+toEmail+" : "+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);


            msg.setFrom(new InternetAddress(EMAIL_FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("账号激活邮件");
            msg.setSentDate(new Date());
            msg.setContent(content , "text/html;charset=utf-8");
            //发送信息
            Transport.send(msg);
            sendResulr = 1 ; //邮件发送成功
        } catch (MessagingException e) {
            logger.error("邮件发送出现异常 ----------- ："+e.getMessage());
            e.printStackTrace();
        } finally {
            if(session != null){
                session = null;
            }
        }
        return sendResulr;
    }



    public static void main(String[] args) {
        sendEmail("1450291172@qq.com","231","213");
    }








}
