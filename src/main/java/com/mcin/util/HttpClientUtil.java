package com.mcin.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by Mcin on 2017/5/10.
 */
public class HttpClientUtil {

    private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

    //浏览器头信息
//    static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    static final String ACCEPT = "*/*";
    static final String ACCEPT_ENCODING = "gzip, deflate, br";
    static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";
    static final String CACHE_CONTROL = "max-age=0";
    static final String CONNECTION = "keep-alive";
    static final String CONTENT_LENGTH = "4124";
    //    static final String CONTENT_LENGTH = "136";
    static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
    static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
    //    static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36";
    static final String UPGRADE_INSECURE_REQUEST = "1";

    // 连接时间
    public static final int TIME_OUT = 60 * 1000;

    /**
     * http get请求数据
     * @param pageUrl 要访问的页面url地址
     * @param charSet 文本编码  utf-8 || gbk ....
     * @return
     */
    public static String get(String pageUrl,String charSet) throws IOException {
//        try {
        URL url = new URL(pageUrl);
        // 得到一个url数据
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 请求方式
        setHeader(conn);
        conn.connect();
        // 判断连接后的状态
        if (conn.getResponseCode() == 404) {
            return null;
        }
        BufferedReader reader = null;
        // 判断是否为gzip 压缩  (gzip 是nginx 返回的)
        reader = bfReader(conn, charSet);
        // 拿到网页的数据  设置编码
        String line = null;
        StringBuilder sb = new StringBuilder();
        // 开始读取数据
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.debug("HttpUtil中get请求出现异常："+e.getMessage());
//            return null;
//        }
    }

    public static String getHttpGet(String pageUrl ,String proxyIp,int propxyProt) throws IOException{ //get请求
        HttpHost proxy = new HttpHost(proxyIp, propxyProt);
//
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .build();
        HttpGet get=new HttpGet(pageUrl);
        CloseableHttpResponse response = httpclient.execute(get);

        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(get("http://www.le.com","utf-8"," 121.42.163.229",8000));
    }

    /**
     * 通过代理ip的get请求
     * @param pageUrl 要访问的页面url地址
     * @param charSet 文本编码  utf-8 || gbk ....
     * @param proxyIp 代理的ip
     * @param propxyProt 代理的端口
     * @return
     */
    public static String get(String pageUrl,String charSet,String proxyIp,int propxyProt) throws IOException {
//        try {
        URL url = new URL(pageUrl);
        // 得到一个url数据 设置代理类型为http 创建端口和ip
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyIp,propxyProt)));
        // 请求方式
        setHeader(conn);
        conn.connect();
        try {  Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        // 判断连接后的状态
        if (conn.getResponseCode() == 404) {
            return null;
        }
        BufferedReader reader = null;
        // 判断是否为gzip 压缩  (gzip 是nginx 返回的)
        reader = bfReader(conn, charSet);
        // 拿到网页的数据  设置编码
        String line = null;
        StringBuilder sb = new StringBuilder();
        // 开始读取数据
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        if (sb != null){
            return sb.toString();
        } else {
            return null;
        }
//        } catch (IOException e) {
//            e.printStackTrace();
//            logger.debug("HttpUtil中get请求出现异常："+e.getMessage());
//            return null;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    /**
     * httpPost提交获取数据
     * @param pageUrl 页面的url地址
     * @param params 请求参数
     * @param charSet 编码
     * @return
     */
    public static String post (String pageUrl , Map<String,String> params, String charSet) {
        try {
            URL url = new URL(pageUrl);
            // 得到一个url数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 请求方式
            conn.setRequestMethod("POST");
            // 参数要输出内容
            conn.setDoOutput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 设置连接时间
            conn.setConnectTimeout(TIME_OUT);
            //设置提交类型
            conn.setRequestProperty("Content-Type", CONTENT_TYPE);
            // 连接
            conn.connect();
            if (params != null && params.keySet().size() > 0) {
                // kw = java & name= cmq
                StringBuilder sb = new StringBuilder();
                // 遍历出所有的参数
                for (String key : params.keySet()) {
                    sb.append(key + "=" + URLEncoder.encode(params.get(key),charSet) + "&");
                }
                String param = sb.delete(sb.length()-1, sb.length()).toString();
                logger.info("**********post提交的数据******" + param );
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(param);
                out.flush();
                out.close();
            }

            // 判断是否为gzip 压缩  (gzip 是nginx 返回的)
            BufferedReader  reader = bfReader(conn, charSet);
            // 拿到网页的数据  设置编码
            String line = null;
            StringBuilder sb = new StringBuilder();

            // 开始读取数据
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conn.disconnect();
            logger.info("*************post数据获取结束**********");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("****HttpUtil中Post提交出现异常: "+e.getMessage());
            return null;
        }
    }

    /**
     * 携带头部参数的post提交登录
     * @param pageUrl
     * @param params
     * @param cookie
     * @param charSet
     * @return
     */
    public static String headPost (String pageUrl , Map<String,String> params,
                                   String cookie, String charSet){
        try {
            URL url = new URL(pageUrl);
            // 得到一个url数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 请求方式
            conn.setRequestMethod("POST");
            // 参数要输出内容
            conn.setDoOutput(true);
            // 不使用缓存
            conn.setUseCaches(false);
            // 设置连接时间
            conn.setConnectTimeout(TIME_OUT);
            // header头设置
            Map<String,String> headers = setHeaders(cookie);
            for (String header : headers.keySet()) {
                conn.setRequestProperty(header, headers.get(header));
            }
            //设置提交类型
            conn.setRequestProperty("Content-Type", CONTENT_TYPE);
            // 连接
            conn.connect();
            // 遍历出所有的参数
            keys(params,charSet,conn);

            BufferedReader reader = null;
            // 判断是否为gzip 压缩  (gzip 是nginx 返回的)
            reader = bfReader(conn, charSet);
            // 拿到网页的数据  设置编码
            String line = null;
            StringBuilder sb = new StringBuilder();

            // 开始读取数据
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            conn.disconnect();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过模拟登陆获取cookie
     * @param params
     * @param loginUrl
     * @param charSet
     * @param cookieId
     * @return
     */
    public static String getCookie (Map<String, String> params , String loginUrl , String charSet , String cookieId){
        logger.info("**********模拟登录获取cookie开始**********");
        if (null != params && params.keySet().size() > 0) {
            String str = post(loginUrl, params, charSet);
            try {
                URL url = new URL(loginUrl);
                // 得到一个url数据
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 请求方式
                conn.setRequestMethod("POST");
                // 参数要输出内容
                conn.setDoOutput(true);
                // 不使用缓存
                conn.setUseCaches(false);
                // 设置连接时间
                conn.setConnectTimeout(TIME_OUT);
                //设置提交类型
                conn.setRequestProperty("Content-Type", CONTENT_TYPE);
                // 连接
                conn.connect();
                // 遍历出所有的参数
                keys(params,charSet,conn);
                Map<String, List<String>> headers = conn.getHeaderFields();
                String cookresult = null;
                for (String key : headers.keySet()) {
                    if (key != null && key.equals("Set-Cookie")) {
                        List<String> cookies = headers.get(key);
                        for (String cookie : cookies) {
                            if (cookie.startsWith(cookieId)) {
                                cookresult = cookie;
                                break;
                            }
                        }
                        logger.info("********headers的key***** "+headers.get(key));
                    }
                }
                // 标记不为定时连接
                conn.disconnect();
                return cookresult;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        } else {
            return null;
        }
    }


    /**
     * 设置浏览器请求头参数
     * @param conn
     * @throws IOException
     */
    private static void setHeader (HttpURLConnection conn) throws IOException{
        conn.setRequestMethod("GET");
        // 这些都是浏览器里面复制的  步骤 netWork -- > Header
        // 设置浏览器请求头  主要用于模拟真实的请求  // 如果失败 需要添加一个cookie // 如果是客户端  需要进行抓包 （pc抓包工具 httpdebug，fidder）
        conn.setRequestProperty("Accept", ACCEPT);
        conn.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
        conn.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
        conn.setRequestProperty("Cache-Control", CACHE_CONTROL);
        conn.setRequestProperty("Upgrade-Insecure-Requests",UPGRADE_INSECURE_REQUEST);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Connection", CONNECTION);
        conn.setReadTimeout(TIME_OUT);
    }

    /**
     * post模拟请求根据cookie设置头
     * @param cookie
     * @return
     */
    public static Map<String, String> setHeaders(String cookie){
        Map<String, String> heraders = new HashMap<String, String>();
        heraders.put("Accept", ACCEPT);
        heraders.put("Accept-Encoding", ACCEPT_ENCODING);
        heraders.put("Accept-Language",ACCEPT_LANGUAGE);
        heraders.put("Cache-Control",CACHE_CONTROL);
        heraders.put("Connection", CONNECTION);
        heraders.put("Content-Length", CONTENT_LENGTH);
        heraders.put("Content-Type",CONTENT_TYPE);
        heraders.put("Cookie", cookie);
        heraders.put("User-Agent", USER_AGENT);
        return heraders;
    }

    /**
     * 判断接收返回的数据类型
     * @param conn
     * @param charSet
     * @return
     * @throws IOException
     */
    private static BufferedReader bfReader (HttpURLConnection conn, String charSet) throws IOException {
        BufferedReader reader = null;
        // gzip 是nginx 返回的对象数据
        if (conn.getHeaderField("Content-Encoding") != null
                && conn.getHeaderField("Content-Encoding").equals("gzip")) {
            reader = new BufferedReader(new InputStreamReader (new GZIPInputStream(conn.getInputStream()),charSet));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),charSet));
        }
        return reader;
    }

    /**
     * 遍历所有map的keys
     * @param params
     * @param charSet
     * @param conn
     * @throws IOException
     */
    public static void keys (Map<String,String> params , String charSet,HttpURLConnection conn) throws IOException {
        if (params != null && params.keySet().size() > 0) {
            // kw = java & name= cmq
            StringBuilder sb = new StringBuilder();
            // 遍历出所有的参数
            for (String key : params.keySet()) {
                sb.append(key + "=" + URLEncoder.encode(params.get(key),charSet) + "&");
            }
            String param = sb.delete(sb.length()-1, sb.length()).toString();
//            logger.info("请求所有的key == "+ param);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(param);
            out.flush();
            out.close();
        }
    }


    public static String httpGet(String urls,String ip,int port) {
//        String ip = "p04.xxxxx.cn";
        String content = null;
        DefaultHttpClient httpclient = null;
        try {
            httpclient = new DefaultHttpClient();
            /** 设置代理IP **/
            HttpHost proxy = new HttpHost(ip, port);
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,proxy);

            HttpGet httpget = new HttpGet(urls);

            httpget.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,1000*30);  //设置请求超时时间
            httpget.setHeader("Proxy-Authorization","Basic eXVsb3JlOll1bG9yZVByb3h5MjAxMzo=");
            httpget.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1");
            httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            HttpResponse responses = httpclient.execute(httpget);
            HttpEntity entity = responses.getEntity();
            content = EntityUtils.toString(entity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();   //关闭连接
        }
        return content;
    }

}
