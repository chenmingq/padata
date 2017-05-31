package com.mcin.reptile;

import com.mcin.dao.InfoMapper;
import com.mcin.dao.impl.InfoDaoImpl;
import com.mcin.model.Info;
import com.mcin.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mcin on 2017/5/23.
 *
 * 开始爬数据
 *
 */
public class Run {
    private static final Logger logger = Logger.getLogger(Run.class);
    private final String CHAR_SET = "GBK";

    public static void main(String[] args) throws InterruptedException {
        new Run().broom();
    }



    public void food (){

        String foodUrl = "http://cn.made-in-china.com/hot-search/食品-1.html";

        for (int i = 0; i <=300 ; i++) {
//            HttpClientUtil.get()
        }

    }















    //    @Test
    public void  broom () throws InterruptedException {
//        String broomBodyUrl = "http://s.hc360.com/?w=%C9%A8%B0%D1&mc=seller&ap=A&t=1&af=2&afadprenum=0&afadbeg="; //需要拼接一个分页数据量的页面  &ee=2&ap=A&t=1 //扫把页面的大分页url
        String broomBodyUrl = "http://s.hc360.com/?w=%B7%FE%D7%B0&mc=seller&ap=A&t=1&af=2&afadprenum=0&afadbeg="; //&ee=2&ap=A&t=1 //扫把页面的大分页url
        String bodys = ""; // 获取到所访问url的当前整体页面
        String detailsUrl =""; // 获取到详情的url
        String detailsBody = ""; //详情页面上边导航栏
        String contactUrl = ""; // 联系我们的url
        String contactBody = ""; // 联系我们的页面
        String companyName = ""; // 企业名称
        String userInfo = ""; // 用户信息
        String shopUrl = ""; //店铺的url
        String shopBody = ""; //我的店铺页面
        InfoMapper mapper = new InfoDaoImpl();


        int countNum = 1; // 总计次数
        List<String> detailsList = new LinkedList<String>();

        for (int i = 1; i <= 5981; i=i+20) {

            logger.info("访问的url为："+ (broomBodyUrl+i));

            try {
                bodys = HttpClientUtil.get(broomBodyUrl+i,CHAR_SET);
                Thread.sleep(500);
                Elements detailsHrefS = Jsoup.parse(bodys).select(".nImgBox");// 获取到所有class属性为 nImgBox的标签
                for ( Element detailsHref:detailsHrefS) {
                    detailsList.add(detailsHref.attr("href"));
                }
                int addCount = 1; // 当前页面第几次添加
                // 对每个详情的商品链接进行访问
                for (int detailsHrefNum = 6; detailsHrefNum < detailsList.size() ; detailsHrefNum++) {
                    detailsUrl = detailsList.get(detailsHrefNum);
                    detailsBody = HttpClientUtil.get(detailsUrl,CHAR_SET);
//                    logger.info("联系我们的页面"+Jsoup.parse(contactUsBody));
                    if (i < 21){
                        Elements tds = Jsoup.parse(detailsBody).select(".mainnav"); // 获取到详情页面上导航栏部分

//                        logger.info(tds.size()+"-----------------------");
                        // 如果没有获取到 mainnav 这个元素  就进入店铺获取联系方式
                        if (tds.size() < 1){
                            shopUrl = Jsoup.parse(detailsBody).select(".ItenmBtn").attr("href"); // 获取到详情页面上导航栏部分
                            shopBody = HttpClientUtil.get(shopUrl,CHAR_SET);
                            Elements  companyNameEl = Jsoup.parse(shopBody).select(".leftBox").select("li");
                            companyName = companyNameEl.get(0).text().trim();
                            StringBuffer sb = new StringBuffer();
                            sb.append(companyNameEl.get(1).text().trim());
                            sb.append("  ");
                            sb.append(companyNameEl.get(2).text().trim());
                            sb.append("  ");
                            sb.append(companyNameEl.get(3).text().trim());
                            userInfo = sb.toString();
//                            logger.info(companyName +"______"+userInfo);
                        } else if (tds.size() > 0) {
                            for (Element td: tds) {
                                contactUrl = td.select("td").get(15).select("a").attr("href");
//                                logger.info(contactUrl +"联系我们的页面的url ****");
                                contactBody = HttpClientUtil.get(contactUrl,CHAR_SET); // 联系我们的页面
                                Elements divss = Jsoup.parse(contactBody).select(".ContacCon1");
                                if (divss.size() > 0){
                                    for (Element dd: divss) {
                                        companyName = dd.select("h3").text().trim();
//                                        logger.info(companyName+"*************");
                                    }
                                } else {
                                    StringBuffer sb = new StringBuffer();
                                    Document document = Jsoup.parse(contactBody);
                                    companyName = document.select(".comName").select("a").text().trim();
                                    Elements divs = document.select(".content-inner").get(0).select("div");
                                    sb.append(divs.get(0).text().trim());
                                    sb.append(" ");
                                    sb.append(divs.get(1).text().trim());
                                    userInfo = sb.toString();
                                }
                            }
                        }
                    } else {
                        Thread.sleep(500);
                        StringBuffer sb = new StringBuffer();
                        //dNavCon
                        Elements lis = Jsoup.parse(detailsBody).select(".dNavCon");
                        contactUrl = lis.select("li").get(3).select("a").attr("href");
                        contactBody = HttpClientUtil.get(contactUrl,CHAR_SET);
                        Document document = Jsoup.parse(contactBody);
                        companyName = document.select(".com-name").text().trim();
                        String info = "";
                        Elements divs = document.select(".company-words").select(".key-message");
                        divs.get(0).text().trim();
                        sb.append(divs.get(0).text().trim());
                        sb.append("  ");
                        sb.append(divs.get(1).text().trim());
                        sb.append("  ");
                        sb.append(divs.get(2).text().trim());
                        sb.append("  ");
                        sb.append(divs.get(3).text().trim());
                        sb.append("  ");
                        sb.append(divs.get(4).text().trim());
                        sb.append("  ");
//                        logger.info("企业名称+"+ companyName);

                        userInfo = sb.toString();
                    }

                    logger.info("企业名称："+companyName);
                    logger.info("用户信息："+userInfo);
                    logger.info("联系我们地址："+contactUrl);

                    Info info = new Info();
                    info.setCompanyName(
                            companyName
                    );
                    info.setUserInfo(
                            userInfo
                    );
                    info.setHost(
                            contactUrl.trim()
                    );
                    info.setCreateTime(new Date());
                    int num = 0;
                    num = mapper.insertData(info);
                    if (num > 0 ){
                        logger.info("***添加成功***");
                    }
                    logger.info("当前起始子页面为 : "+i+ " ，页面详情的url: "+detailsUrl+" ，子页面中第 "+ addCount++ +" 次添加数据，"+"总共第："+countNum++ +" 次添加");
                }
            } catch (IOException e) {
                logger.error("出现异常信息："+e.getMessage());
            } finally {
                detailsList.clear();
                continue;
            }
        }
    }
}
