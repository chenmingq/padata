package com.mcin.email;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Created by Mcin on 2017/5/18.
 * 发送企业邮箱
 */
public class SendMail {

    private static final Logger logger = Logger.getLogger(SendMail.class);

    final static String TO_EMAIL_ADDRESS = "fengniao@haikedao.com"; //收件人账号邮箱
    final static String USER_NAME = "yingwu@haikedao.com"; // 自己的企业邮箱
    final static String PASS_WORD = "520520cheN"; //密码
    final static String SUBJECT = "这是企业邮箱发送的主题"; //邮件主题
    final static String CONTENT = "这是企业邮箱发送的内容"; // 邮件内容

    static long startTime , endTime; // 用于计算发送的时间耗时

    public static void main(String[] args) throws Exception {
        //获取Session对象
        Session session = Session
                .getDefaultInstance(
                        ExMailUtil
                                .setTencentExEmail(),
                        new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(USER_NAME, PASS_WORD);
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
//        session.setDebug(true);

          /*
          // 如果实现群发的功能  比如 收件人方可以显示到多少个收件用户的
          MimeMessage mimeMessage = new MimeMessage(session);
          mimeMessage.setFrom(new InternetAddress(userName,userName));*/

        for (int i = 0; i <10 ; i++) {

            // 实现单独发送的功能 收件人方只显示自己的邮箱
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(USER_NAME,USER_NAME));

            mimeMessage.addRecipient(Message
                    .RecipientType
                    .TO,
                    new InternetAddress(
                            TO_EMAIL_ADDRESS
                    ));

            //设置主题
            mimeMessage.setSubject(SUBJECT);
            mimeMessage.setSentDate(new Date());
            //设置内容
            mimeMessage.setText(CONTENT);
            mimeMessage.saveChanges();
            logger.info("***开始发送第 "+(i+1)+" 个邮件***");
            startTime = System.currentTimeMillis();
            try {
                //发送
                Transport.send(mimeMessage);
                endTime = System.currentTimeMillis();
                logger.info("第 "+(i+1)+" 个发送成功***耗时："
                        +(endTime - startTime)/1000+" 秒");
                logger.info("-------------------------------------------------------------");
            } catch (MessagingException e) {
                logger.error(e.getMessage());
                continue;
            }
        }
    }
}
