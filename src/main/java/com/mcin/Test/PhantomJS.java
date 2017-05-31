package com.mcin.Test;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mcin on 2017/5/22.
 */
public class PhantomJS {

    public static String getAjaxCotnent(String url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        Process p = rt.exec("phantomjs.exe C:\\develop\\phantomjs\\bin\\codes.js "+url);//这里我的codes.js是保存在c盘下面的phantomjs目录
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String tmp = "";
        while((tmp = br.readLine())!=null){
            sbf.append(tmp);
        }
        System.out.println(Jsoup.parse(sbf.toString()));
        //System.out.println(sbf.toString());
        return sbf.toString();
    }

    public static void main(String[] args) throws IOException {
        getAjaxCotnent("http://s.hc360.com/?w=%CA%B3%C6%B7&mc=seller&ap=A");
    }


    /*public static void main(String[] args) {
        System.setProperty("phantomjs.binary.path", "C:\\develop\\phantomjs\\bin\\phantomjs.exe");

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();

        WebDriver driver = new PhantomJSDriver(desiredCapabilities);
        driver.get("http://s.hc360.com/?w=%CA%B3%C6%B7&mc=seller&ap=A");
    }*/
}
