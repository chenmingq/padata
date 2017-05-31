package com.mcin.Test;

import com.mcin.util.SeleniumUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Mcin on 2017/5/15.
 */
public class SeleniumTest {
    private static final Logger logger = Logger.getLogger(SeleniumTest.class);
    static final String URL_ = "http://s.hc360.com/?w=%BC%D2%BE%DF&mc=seller";
    static final String  CHAR_SET = "GBK";


    @Test
    public void testSelenium() {
        SeleniumUtil.setChromeDeriver();
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://s.hc360.com/?w=%CA%B3%C6%B7&mc=seller&ap=A");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(Jsoup.parse(webElement.getAttribute("outerHTML")));
//        webDriver.close();
    }






    public static void main(String[] args) throws InterruptedException {
        SeleniumUtil.setChromeDeriver();
        WebDriver driver = new ChromeDriver();
        driver.get(URL_);
//         driver.findElement(By.className("bd")).findElements(By.tagName("ul")).get(0).findElements(By.tagName("li")).get(1);
//         logger.info(driver.getPageSource());
//         driver.findElement(By.className("bd")).findElements(By.tagName("ul")).get(0).findElements(By.tagName("li")).get(1).click();
        int liSize = driver.findElement(By.className("wrap-grid")).findElements(By.tagName("li")).size();
        logger.info(liSize);
        for (int i = 0 ;i< liSize ;i++) {
            try {
                Set<String> dw = driver.getWindowHandles(); //
                List<String> list = new ArrayList<String>(dw);
                logger.info(list.size() + " ------------------------------------------------" +
                        "");
                driver.switchTo().window(list.get(0));
                String setscroll = "document.documentElement.scrollTop=" + 3000;

                JavascriptExecutor jse=(JavascriptExecutor) driver;
                jse.executeScript(setscroll);
              /*  driver.findElement(By.className("wrap-grid")).findElements(By.tagName("li")).get(i).findElement(By.className("nImgBox")).click();
               logger.info(Jsoup.parse(driver.getPageSource()));
//                driver.findElement(By.className("wrap-grid"))
//                        .findElements(By.tagName("li"))
//                        .get(i)
//                        .findElement(By.className("nImgBox"))
//                driver .findElement(By.className("mainnav"))
//                        .findElements(By.tagName("td"))
//                        .get(15).findElement(By.tagName("a"))
//                        .click();
                logger.info("当前第 == " + i + " === 次");
                Thread.sleep(100000000);*/
            } catch (Exception e) {
                logger.error(e.getMessage() + "： 异常信息" + "\n");
            } finally {
                continue;
            }
        }
        logger.info(liSize);
    }
}
