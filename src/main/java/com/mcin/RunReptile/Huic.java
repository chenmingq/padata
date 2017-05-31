package com.mcin.RunReptile;

import com.mcin.dao.InfoMapper;
import com.mcin.dao.impl.InfoDaoImpl;
import com.mcin.model.Info;
import com.mcin.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mcin on 2017/5/16.
 */
public class Huic {
    private static final Logger logger = Logger.getLogger(Huic.class);
    private static ExecutorService pool = Executors.newFixedThreadPool(1);
    static final String CHAR_SET = "GBK";
    static String URL_ = "http://s.hc360.com/?w=%BC%D2%BE%DF&mc=seller&ee=14&ap=A&t=1";
    static String IP = "5.135.169.224";
    static int PORT = 2463;

//      int pageNum = j;

    public static void main(String[] args) throws IOException {
        InfoMapper mapper = new InfoDaoImpl();
        String contactUsHref = "";
        Info info = new Info();
        for (int j = 1; j <= 5981; j=j+20) {
//            pool.execute(new Runnable() {
//                public void run() {
//            String urls = "http://s.hc360.com/?w=%B7%FE%D7%B0&mc=seller&ee="+j+"&ap=A&t=1";
//            String html = PhantomJS.getAjaxCotnent(urls);

            String html = HttpClientUtil.get("http://s.hc360.com/?w=%B7%FE%D7%B0&mc=seller&ap=A&t=1&af=2&afadprenum=0&afadbeg="+j, CHAR_SET/*,IP,PORT*/);
            if (StringUtils.isNotBlank(html)) {
                Document doc = Jsoup.parse(html);
                // 获取到所以要访问的a链接url
                Elements ahrefS = doc.select(".nImgBox");
                logger.info(ahrefS.size());

                List<String> list = new ArrayList<String>();
                for (int i = 0; i < ahrefS.size(); i++) {
                    list.add(ahrefS.get(i).attr("href"));
                }
                logger.info(list.size());
                int num = 0;


                // 获取到公司的名称
                String companyName = "";
                String host = "";
                // 获取到用户的信息
                String userInfo =  "";



                for (int i = 0; i < list.size(); i++) {
                    try {
                        // 访问获取到的a连接的详情页面
                        String result = HttpClientUtil.get(
                                list.get(i),
                                CHAR_SET
                        );
                        logger.info(Jsoup.parse(list.get(i)));
                        // 通过详情页面获取联系我们的url
                        try{
                            contactUsHref = Jsoup
                                    .parse(result)
                                    .select(".mainnav")
                                    .select("td")
                                    .get(15)
                                    .select("a")
                                    .attr("href");
                            // 访问获取到联系我们的url
                              host = HttpClientUtil.get(
                                    contactUsHref,
                                    CHAR_SET
                            );

                            companyName = Jsoup
                                    .parse(host)
                                    .select(".blackbold")
                                    .text().trim();


                            userInfo = Jsoup
                                    .parse(host)
                                    .select(".content-inner")
                                    .text().trim();



                        } catch (Exception ee){
                            contactUsHref = Jsoup
                                     .parse(result)
                                     .select(".dNavCon")
                                     .select("li")
                                     .get(3)
                                     .select("a")
                                     .attr("href") ;
//                            contactUsHref = li.tagName("a").tagName("a").attr("href");

                            host = HttpClientUtil.get(
                                    contactUsHref,
                                    CHAR_SET
                            );

                            StringBuffer sb = new StringBuffer();
                              Elements divs = Jsoup
                                    .parse(host)
                                    .select(".company-words")
                                      .select("div");
                            companyName = Jsoup
                                    .parse(host)
                                    .select(".company-tit").select(".com-name").text().trim();

                            sb.append(
                                    divs
                                    .get(0)
                                    .text().trim()
                            );
                           /* sb.append(" ");
                            sb.append(
                                    divs
                                    .get(2)
                                    .text().trim()
                            );
                            sb.append(" ");
                            sb.append(
                                    divs
                                    .get(3)
                                    .text()
                                    .trim());
                            sb.append(" ");
                            sb.append(
                                        divs
                                    .get(4)
                                    .text().trim()
                            );
                            sb.append(" ");*/
                            userInfo = sb.toString();

                            logger.info(contactUsHref+"-----------------");
                        }


                        if (StringUtils.isBlank(companyName) || StringUtils.isBlank(userInfo)) {
                            companyName = Jsoup
                                    .parse(host)
                                    .select(".ContacCon1")
                                    .text().trim();
                            userInfo = Jsoup
                                    .parse(host)
                                    .select(".con3Rig")
                                    .text().trim();
                        }

                        logger.info("公司名称：" + companyName);
                        logger.info("用户信息：" + userInfo);

                        info.setCompanyName(
                                companyName
                        );
                        info.setUserInfo(
                                userInfo
                        );
                        info.setHost(
                                contactUsHref.trim()
                        );
                        info.setCreateTime(new Date());
                        num = mapper.insertData(info);
                        if (num > 0) {
                            logger.info("第：" + num++ + " 次添加成功");
                        }
                        Thread.sleep(1000);
                        logger.info("第 " + i + "次");
                        logger.info("当前第： " + j + " 页");
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    } finally {
                        continue;

                    }
                }
            }
//                }
//            });
        }
    }
}
