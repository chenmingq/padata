package com.mcin.run;

import com.mcin.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mcin on 2017/5/10.
 */
public class Login {

    private static final Logger logger = Logger.getLogger(Login.class);
    public static String URL_ = "http://localhost:8080/meike-cms/free/login.do";
    //    public static String URL_ = "https://login.taobao.com/member/login.jhtml?redirectURL=https://www.taobao.com/";
//    public static String URL_ = "http://www.xianlife.com/mobile/index.php?act=login";
    private static final String USER_NAME = "admin";
    private static final String PASS_WORD = "admin";
    private static final String CHAR_SET = "UTF-8";
    private static final String COOKIE_ID = "JSESSIONID=";
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();

        map.put("userName",USER_NAME);
        map.put("password",PASS_WORD);

        String cookie = HttpClientUtil.getCookie(map, URL_, CHAR_SET, COOKIE_ID);
//        logger.info("*******使用cookie登录********"+cookie);
        String result =  HttpClientUtil.headPost(URL_, map,cookie, CHAR_SET);
//        logger.info("***************************************************");
//        logger.info("jsoup转换后的格式"+ Jsoup.parse(result));
        Document doc = Jsoup.parse(result);
        logger.info("**************************************************");
        String reqUrl = doc.select("frame").get(1).attr("src").toString(); // 得到要获取数据的url
        String centRespon  = HttpClientUtil.headPost(reqUrl,map,cookie,CHAR_SET);// 获取url所返回的数据
        doc = Jsoup.parse(centRespon);
        Element ul = doc.select("ul").get(2); // 获取指定某个ul的数据
        String href = ul.select("a").get(0).attr("href"); // 获取该ul的里面的某个url

        String hrefResult = "";
        hrefResult = HttpClientUtil.headPost(href,map,cookie,CHAR_SET);// 通过ul的里面的某个url获取数据
        doc = Jsoup.parse(hrefResult);
        Elements els = null;
        List<String > list = new ArrayList<String>();
        String centTitle = doc.select("th").text();
        logger.info(centTitle);
        String cent = null;
        for (int i = 0 ;i<doc.select("tr").size();i++){
            els =  doc.select("tr").get(i).getElementsByIndexEquals(i);
            cent = els.select("td").text();
            logger.info(cent);
            list.add(cent);
        }
        logger.info(list);
        ArrayList<String[]> objects = new ArrayList<String[]>();
        String[] str;
        String atrArrs = null;
        for (String l:list) {
            str = l.split(" ");
            objects.add(str);
//            logger.info(objects);
            for (int i = 0; i <str.length ; i++) {
                logger.info(str[i]+"**");
            }
        }
    }

    public static boolean regPhone (String phone){
        boolean flage = false;
        String reg ="\\d{11}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(phone);
        flage = matcher.matches();
        return flage;
    }
}
