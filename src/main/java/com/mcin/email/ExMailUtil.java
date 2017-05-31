package com.mcin.email;

import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.log4j.Logger;

import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * Created by Mcin on 2017/5/18.
 */
public class ExMailUtil {

    private static final Logger logger = Logger.getLogger(ExMailUtil.class);

    // 这是腾讯企业邮箱的  如果是其他邮箱 自行更换
    static String MAIL_TRANSPORT_PROTOCOL = "smtp"; //邮箱协议
    static String MAIL_SMTP_HOST = "smtp.exmail.qq.com"; //发件服务器地址
    static String MAIL_SMTP_PORT = "465"; // 端口
    static String MAIL_SMTP_AUTH = "true"; //使用smtp身份验证

    /**
     * 邮箱配置
     */
    public static Properties setTencentExEmail (){
        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", MAIL_TRANSPORT_PROTOCOL);
        //服务器
        prop.setProperty("mail.smtp.host", MAIL_SMTP_HOST);
        //端口
        prop.setProperty("mail.smtp.port", MAIL_SMTP_PORT);
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", MAIL_SMTP_AUTH);

        //开启安全协议 使用SSL，企业邮箱必需！
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        return prop;
    }
}
