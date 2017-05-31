package com.mcin.run;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Mcin on 2017/5/11.
 */
public class SeleniumTest {
    private static final Logger logger = Logger.getLogger(SeleniumTest.class);
    static final String URL_ = "http://www.hc360.com/";
    static final String USER_NAME = "abc1832917";
    static final String PASS_WORD = "abc123456";

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver","src\\main\\resources\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(URL_);
        Thread.sleep(1000);
//        driver.quit();
        driver.findElement(By.className("navbar-nav")).findElements(By.tagName("li")).get(1).click();
//        driver.findElement(By.className("TopLogin")).click();//.sendKeys("java");
//        driver.findElement(By.id("su")).click();
        //得到所有窗口的集合
//        Set<String> dw = driver.getWindowHandles();
//        List<String> list = new ArrayList<String>(dw);
//        driver.switchTo().window(list.get(1));
        Thread.sleep(1000);

        WebElement loginID = driver.findElement(By.id("LoginID"));
        if (loginID != null){

        }
//        loginID.sendKeys("abc1832917");
//        driver.findElement(By.id("password")).sendKeys("abc123456");
//        driver.findElement(By.id("formsub")).click();

        System.out.println(driver.getPageSource());

//        loginHC(URL_,USER_NAME,PASS_WORD);
    }

    /**
     * 登录慧聪
     * @param url
     * @param userName
     * @param psd
     */
    private static void loginHC(String url,String userName,String psd) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(500);

        driver.findElement(By.className("TopLogin")).click();
        Thread.sleep(500);
        logger.info("--开始登录--");
        driver.findElement(By.id("LoginID")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(psd);
        driver.findElement(By.id("formsub")).click();
        logger.info("--登录成功--");
        driver.findElement(By.id("w")).sendKeys("家具");
        driver.findElement(By.id("navSearchBtn")).click();
        logger.info("----搜索完毕----");

        logger.info("--开始获取所有的div元素--");
//        int divs = driver.findElement(By.className("wrap-grid")).findElements(By.tagName("li")).size();
       Integer uls = driver.findElements(By.tagName("ul")).size();
        logger.info("--ul--"+uls);

    }

}
