package com.mcin.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * Created by Mcin on 2017/5/21.
 */
public class AjaxUtils {

    private static String url = "http://10.129.39.149:8090/Ajax/loginMgt/login.action";

    public static void method(HttpClient client, String url, String body){
        PostMethod  method = new PostMethod(url);
        //"count":10,"ignoreCase":"false","paras":["a%"],"queryId":"getMenu"
        NameValuePair[] postData = new NameValuePair[]{};
        //postData[0] = new NameValuePair("count", 10);
        method.setRequestBody(body);//addParameters(postData);


        // Provide custom retry handler is necessary
        /*method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));*/

        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            System.out.println(new String(responseBody,"utf-8"));
        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }

    public static void main(String[] args) {
        // Create an instance of HttpClient.
        HttpClient client = new HttpClient();

        String body ="[{\"userId\":1,\"passWord\":1}]";
        // Create a method instance.
        method(client,url,body);

        url = "http://s.hc360.com/?w=%CD%E2%C7%BD%CD%BF%C1%CF&mc=seller";

        body = "[{\"count\":10,\"ignoreCase\":\"false\",\"paras\":[\"a%\"],\"queryId\":\"getMenu\"}]";
        method(client,url,body);
    }
}
