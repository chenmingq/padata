package com.mcin.run;

import com.mcin.dao.impl.InfoDaoImpl;
import com.mcin.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mcin on 2017/5/15.
 */
public class BaiDuTest {
    private static ExecutorService pool = Executors.newFixedThreadPool(1);
    public static void main(String[] args) {

        Map<String,String> resultMap = new HashMap<String, String>();
        InfoDaoImpl infoDao = new InfoDaoImpl();

        /*System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com");
        Thread.sleep(1000);
        driver.findElement(By.id("kw")).sendKeys("java");
        driver.findElement(By.id("su")).click();*/

        for (int i = 0; i < 10; i++) {
            resultMap = infoDao.roandIp();
            String ip = resultMap.get("ip");
            String prot = resultMap.get("prot");
            for (int j = 0; j < 10; j++) {
                try{
                    String html = HttpClientUtil.get("http://s.hc360.com/?w=%BC%D2%BE%DF&mc=seller&ee=3&ap=A&t=1","gbk","218.0.143.51",8998);
                    if (StringUtils.isBlank(html)){
                        System.out.println(html);
                        continue;
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage()+"************************");
                }
            }
        }


    }
}
