package com.mcin.Test;

import com.mcin.util.SeleniumUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Mcin on 2017/5/16.
 */
public class BtSeleniumUtil {

    public static void main(String[] args) {
        SeleniumUtil.setChromeDeriver();

        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://s.hc360.com/?w=%E6%9C%8D%E8%A3%85&mc=seller");
        webDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        WebElement element = webDriver.findElement(By.cssSelector(".red_box"));
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].style.border = \"5px solid yellow\"",element);
        System.out.println(webDriver.getPageSource());
//        WebDriver driver = new ChromeDriver();
//        driver.get("http://s.hc360.com/?w=%BC%D2%BE%DF&mc=seller&ee=2&ap=A&t=1");
//        JavascriptExecutor driver_js= (JavascriptExecutor) driver;
//        //利用js代码键入搜索关键字
//        driver_js.executeScript("document.getElementById(\"kw\").value=\"yeetrack\"");
//
//        driver.findElement(By.id("su")).click();
//
//        //等待元素页面加载
//        waitForElementToLoad(driver, 10, By.xpath(".//*[@id='1']/h3/a[1]"));
//
//        //将页面滚动条拖到底部
//        driver_js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
//
//        //#将页面滚动条拖到顶部
//        driver_js.executeScript("window.scrollTo(0,0)");

    }
    /**
     * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
     * */
    public static void waitForElementToLoad(WebDriver driver, int timeOut, final By By) {
        try {
            (new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driver) {
                    WebElement element = driver.findElement(By);
                    return element.isDisplayed();
                }
            });
        } catch (TimeoutException e) {

        }
    }

}
