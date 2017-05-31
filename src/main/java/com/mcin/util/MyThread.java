package com.mcin.util;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mcin on 2017/5/10.
 */
public class MyThread implements Runnable {
    private static final Logger logger = Logger.getLogger(MyThread.class);
    public static String URL_ = "http://www.xianlife.com/mobile/index.php?act=login";
    private static final String USER_NAME = "15372042705";
    private static final String PASS_WORD = "123456";
    private static final String CHAR_SET = "UTF-8";

    public void run() {
        ru();
    }

    public void ru(){
        int count = 0;
//        synchronized (this){
            while (true) {
                count ++;
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", USER_NAME);
                map.put("password", PASS_WORD);
                map.put("Invitation_Code", "");
                map.put("client", "wap");
                map.put("activity_type", "");

                String cookie = HttpClientUtil.getCookie(map, URL_, CHAR_SET, "PHPSESSID=");
//                logger.info("*******使用cookie登录********"+cookie);

                String result =  HttpClientUtil.headPost(URL_, map,cookie, CHAR_SET);
//                logger.info("***************************************************");
                logger.info("jsoup转换后的格式"+ Jsoup.parse(result));
                logger.info( "访问第："+ count +"次");
                logger.info(Thread.currentThread().getName()+"---线程名称---");
//            }
        }
    }




}
