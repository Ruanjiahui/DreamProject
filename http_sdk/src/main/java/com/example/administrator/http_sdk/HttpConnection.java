package com.example.administrator.http_sdk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/2/15.
 */
public class HttpConnection {

    public static String HttpConnection(String uri, String data) {
        String result = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//设置输入采用字节流方式
            connection.setDoOutput(true);//设置输出采用字节流的方式
            connection.setUseCaches(false); //不使用缓存
            connection.setRequestMethod("POST");//设置POST请求
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);//设置请求时间
            connection.setRequestProperty("Charset", "utf-8");//设置字符编码

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            connection.connect();//发送请求链接

            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                //下面对获取到的输入流进行读取
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String HttpConnection(String uri) {
        String result = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);//设置输入采用字节流方式
            connection.setDoOutput(true);//设置输出采用字节流的方式
            connection.setRequestMethod("GET");//设置POST请求
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);//设置请求时间
            connection.setRequestProperty("Charset", "utf-8");//设置字符编码

            connection.connect();//发送请求链接

            if (connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                //下面对获取到的输入流进行读取
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                result = response.toString();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
