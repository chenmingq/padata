package com.mcin.run;

import com.mcin.dao.impl.InfoDaoImpl;
import com.mcin.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Mcin on 2017/5/17.
 */
public class GetIpTest {
    private static final Logger logger = Logger.getLogger(GetIpTest.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        List<Map<String ,Integer>> maps = new ArrayList<Map<String, Integer>>();
            maps = getIps();
//        for (int i = 0; i <maps.size() ; i++) {
//            logger.info(maps.get(i));
//        }
        for (int i = 0; i < 5 ; i++) {
            Map<String ,Integer> map = randomIp(maps);
            String ip = new ArrayList<String>(map.keySet()).get(i);
            int prot = map.get(ip);

           String baidu =  HttpClientUtil.get("https://www.baidu.com","utf-8",ip,prot);

            logger.info(ip+":"+prot);
            logger.info(baidu+"********************"+i);
        }
    }

    /**
     * 开始构建动态ip代理池
     * @param listMaps
     * @return
     */
    private static Map<String ,Integer> randomIp (List<Map<String ,Integer>> listMaps){
//        Map<String ,Integer> map = new HashMap<String, Integer>();
        return listMaps.get(new Random().nextInt(listMaps.size()));
    }

    /**
     * 获取所有的ip
     * @return
     */
    private static List<Map<String ,Integer>> getIps () throws IOException {
        List<Map<String, Integer>> listMap = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, Object> ipMap = new HashMap<String, Object>();
        InfoDaoImpl infoDao = new InfoDaoImpl();
        for (int i = 2; i < 100; i++) {
            logger.info("当前第 "+i+" 页");
            String html = HttpClientUtil.get("http://www.kuaidaili.com/free/inha/" + i + "", "utf-8");
            Document document = Jsoup.parse(html);
            Elements eles = document.select("tr");
            for (int j = 1; j < eles.size(); j++) {
                String ip = eles.get(j).select("td").get(0).text();
                String prot = eles.get(j).select("td").get(1).text();
                map.put(ip, Integer.parseInt(prot));
                ipMap.put("ip", ip);
                ipMap.put("prot",prot );
                listMap.add(map);
                infoDao.insertIp(ipMap);
                try {
                    logger.info("ip===：" + ip+" : "+prot);
                    Thread.sleep(2000);
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }
        }
        return listMap;
    }
}
