package com.mcin.Test;

import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by Mcin on 2017/5/22.
 */
public class Stest {
    public static WebDriver driver;

    @Test
    public void testUnit() {
        driver = new InternetExplorerDriver();
        driver.get("https://www.baidu.com");

        maxBrowser(driver);
        setScroll(driver,500);
//        driver.findElement(By.linkText("投诉与建议")).click();
//        driver.findElement(By.xpath("//input[@id='repName']")).sendKeys("1");
//        driver.findElement(By.xpath("//input[@id='repMail']")).sendKeys("1");
//        driver.findElement(By.xpath("//textarea[@id='repContent']")).sendKeys("hello");
//        driver.findElement(By.xpath("//a[@id='repBtn']")).click();

//        Assert.assertEquals("您输入的邮箱格式不正确", driver.findElement(By.xpath("//div[@id='floatBox_remind']/span")).getText());

    }


    //将IE最大化
    public static void  maxBrowser(WebDriver driver){
        try {
            String maxBroswer = "if (window.screen) {window.moveTo(0, 0);" +
                    "window.resizeTo(window.screen.availWidth,window.screen.availHeight);}";

            JavascriptExecutor jse=(JavascriptExecutor) driver;
            jse.executeScript(maxBroswer);
        } catch (Exception e) {
            System.out.println("Fail to  Maximization browser");
        }
    }

    //将滚动条滚到适合的位置
    public static void setScroll(WebDriver driver,int height){
        try {
            String setscroll = "document.documentElement.scrollTop=" + height;

            JavascriptExecutor jse=(JavascriptExecutor) driver;
            jse.executeScript(setscroll);
        } catch (Exception e) {
            System.out.println("Fail to set the scroll.");
        }
    }
}
