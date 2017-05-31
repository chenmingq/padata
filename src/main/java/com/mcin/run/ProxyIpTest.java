/*
package com.mcin.run;

import com.mcin.util.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

*/
/**
 * Created by Mcin on 2017/5/13.
 *//*

public class ProxyIpTest {
    public static void main(String[] args) {
//        String url_ = "https://www.baidu.com";
        String url_ = "http://www.xicidaili.com/nt/";
        String getUrl = "http://www.cxytiandi.com";
        List<Map<String,Integer>> list  = getIp(url_);
        String html = "";
        for (int i = 0; i <100; i++) {
            Map<String,Integer> map = random(list);
            String ip = new ArrayList<String>(map.keySet()).get(i);
            int port = map.get(ip);
            System.out.println(ip+"\t"+port);
            html = HttpClientUtil.get(getUrl,"UTF-8",ip,port);
            System.out.println(Jsoup.parse(html));
        }
//        System.out.println();
    }


    */
/**
     * 通过ip代理池中随机获取ip
     * @param list
     * @return
     *//*

    private static Map<String,Integer> random ( List<Map<String,Integer>> list){
        return list.get(new Random().nextInt(list.size()));
    }

    private static List<Map<String,Integer>> getIp (String url){
        List<Map<String,Integer>> list = new ArrayList<Map<String,Integer>>();
        Map<String,Integer> map = new HashMap<String, Integer>();
        String html = HttpClientUtil.get(url,"utf-8");
        Element table = Jsoup.parse(html).getElementById("ip_list");
        Elements trs =  table.select("tr");
        int i =0;
        for (Element tr : trs) {
            if (i > 0){
                Elements tds = tr.select("td");
                map.put(tds.get(1).text(),Integer.parseInt(tds.get(2).text()));
                list.add(map);
            }
            i++;
        }
        return list;
    }

}
*/
