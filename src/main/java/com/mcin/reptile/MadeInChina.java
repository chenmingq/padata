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
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * Created by Mcin on 2017/5/25.
 *
 * 中国制造网数据
 *
 */
public class MadeInChina {
    private  final Logger logger = Logger.getLogger(Run.class);
    // HttpHost proxy = new HttpHost("124.166.180.48", 8118);
    String ip = "61.232.254.39";
    int prot = 3128;
    @Test
    public void food () throws InterruptedException {
        String charSet = "GBK";
        String pageBody = ""; //获取当前页面数据
        String host = "http://cn.made-in-china.com";
        List<String> list = new ArrayList<String>();
        String detailsBody = ""; //商品详情页面
        Set<String> set = new HashSet<String>();
        String contactWay = ""; //用于添加到set的联系方式
        String contactUrl = "";// set遍历出来的联系方式url
        String contactBody = ""; //联系我们的页面
        StringBuilder sb = new StringBuilder(); // 添加企业信息
        Info info = new Info();
        InfoMapper mapper = new InfoDaoImpl();
        int num = 0;
        for (int pageNum = 11 ; pageNum <= 300; pageNum++) {
            String urls = "http://cn.made-in-china.com/hot-search/食品-"+pageNum+".html";

            // 获取每个页面的元素
            try {
                pageBody = HttpClientUtil.get(urls,charSet,ip,prot);
//                pageBody = HttpClientUtil.getHttpGet(urls,ip,prot);
                Document document = Jsoup.parse(pageBody);

                // 获取主页面所以的li标签
                Elements bodyLis = document.select(".pro-item");

                // 获取每个页面的 详情链接
                for ( Element ahref : bodyLis) {
//                    logger.info("商品详情地址：  "+host+ahref.select(".pro-data").select("h3").select("a").attr("href"));
                    list.add(host+ ahref.select(".pro-data").select("h3").select("a").attr("href"));
                    num ++;
                }

                // 对每个商品详情的链接进行访问
                for (int i = 0; i <list.size() ; i++) {
                    logger.info(list.get(i)+"list.get(i)");
                    detailsBody = HttpClientUtil.get(list.get(i),charSet,ip,prot);
//                    detailsBody = HttpClientUtil.getHttpGet(list.get(i),ip,prot);

                    Document detalisDoc = Jsoup.parse(detailsBody);
                    Elements lis = detalisDoc.select(".nav-wrap").select(".grid").select(".top_nav").select(".nav-item");
//                    logger.info(lis.get(4));
                    if ("联系方式".equals(lis.get(4).text().trim())) {
                        contactWay = lis.get(4).select("a").attr("href");
                    } else if ("商情列表".equals(lis.get(4).text().trim())) {
                        contactWay = lis.get(5).select("a").attr("href");
                    } else if ("发联系信".equals(lis.get(4).text().trim())) {
                        contactWay = lis.get(3).select("a").attr("href");
                    }
                    set.add(contactWay);
                    Thread.sleep(2000);
                }

                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    contactUrl = it.next();
                    logger.info(contactUrl);
                    contactBody = HttpClientUtil.get(contactUrl,charSet,ip,prot);
//                    contactBody = HttpClientUtil.getHttpGet(contactUrl,ip,prot);
                    Document contactBodyDoc = Jsoup.parse(contactBody);
                    String company =  contactBodyDoc.select(".card-company-info").select("h3").text().trim();
                    logger.info(company);
                    Elements dls = contactBodyDoc.select(".card-info-bd").select("dl");
                    sb.append(contactBodyDoc.select(".card-name").text().trim());
                    sb.append(" ");
                    sb.append(dls.get(0).text().trim());
                    sb.append(" ");
                    sb.append(dls.get(1).text().trim());
                    sb.append(" ");
                    sb.append(dls.get(2).text().trim());
                    sb.append(" ");
                    sb.append(dls.get(3).text().trim());
                    info.setCompanyName(
                            company
                    );
                    info.setUserInfo(
                            sb.toString()
                    );
                    info.setHost(
                            contactUrl.trim()
                    );
                    info.setCreateTime(new Date());
                    int nums = 0;
                    nums = mapper.insertData(info);

                    if (nums > 0){
                        logger.info("***添加成功***");
                    }

                    logger.info(sb.toString());
                    sb.delete(0,sb.length());
                    logger.info(contactUrl);
                }




            } catch (IOException e) {
                logger.info("出现异常信息：" + e.getMessage());
            } finally {
                logger.info("第 "+pageNum+" 页 共 "+num+" 条商品详情 "+set.size()+" 条不重复数据");
                Thread.sleep(1000);
                continue;
            }
        }









    }

















}
