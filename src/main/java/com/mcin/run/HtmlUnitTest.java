package com.mcin.run;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Mcin on 2017/5/13.
 */
public class HtmlUnitTest {
  public static void main(String[] args) throws IOException, InterruptedException {
    // 创建一个web客户端 使用 chrome浏览器
    WebClient webClient = new WebClient(BrowserVersion.CHROME);
    //设置超时时间  这里可以有禁用css等js属性
    webClient.getOptions().setTimeout(35000);
//    webClient.getOptions().setCssEnabled(false);//设置css是否生效
//    webClient.getOptions().setJavaScriptEnabled(true);//设置js是否生效
    webClient.waitForBackgroundJavaScript(5000);
    // 设置ajax参数  可以实现获取动态数据
    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    HtmlPage htmlPage = webClient.getPage("http://s.hc360.com/?w=服装&mc=seller");
//        htmlPage.getElementById("21").click(); // 获取id的参数 对某个参数点击操作 ，这里是没有现实场景的， 都是后台模拟的请求 在代码中执行
    Thread.sleep(1000);
    FileUtils.write(new File("hc.html"),htmlPage.asXml(),"utf-8");




/*
        WebClient webClient= new WebClient(BrowserVersion.CHROME);//设置浏览器
        webClient.getOptions().setCssEnabled(true);//设置css是否生效
        webClient.getOptions().setJavaScriptEnabled(true);//设置js是否生效
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置ajax请求
        webClient.getOptions().setTimeout(10000);
        webClient.waitForBackgroundJavaScript(5000);
        try{
            HtmlPage htmlPage=webClient.getPage("http://s.hc360.com/?w=%BC%D2%BE%DF&mc=seller");//访问路径设置
            FileUtils.write(new File("123.html"),htmlPage.asText(),"utf-8");
//            System.out.println(htmlPage.asXml());
        }catch (Exception e){
            System.out.println(e.getMessage()+"&************************");
        }*/
    webClient.close();











  }
}
