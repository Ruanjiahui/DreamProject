package com.example.administrator.http_sdk;

import android.graphics.Bitmap;

import com.example.administrator.data_sdk.FileUtil.FileImageUpload;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/19.
 */
public class HTTP implements HttpInterface.HttpConnect {

    private String url = null;
    private String data = null;
    private ArrayList<File> file = null;
    private String key = null;

    /**
     * POST请求的方法
     *
     * @param httpHandler
     * @param url
     * @param data
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, String url, String data) {
        this.url = url;
        this.data = data;
        new Thread(new MyRunnable(this , httpHandler)).start();
    }

    /**
     * GET请求的方法
     *
     * @param httpHandler
     * @param url
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, String url) {
        this.url = url;
        new Thread(new MyRunnable(this , httpHandler)).start();
    }

    /**
     * 上传图片的方法
     *
     * @param httpHandler
     * @param file
     * @param url
     * @param data
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, ArrayList<File> file, String url, String data , String key) {
        this.file = file;
        this.url = url;
        this.data = data;
        this.key = key;
        new MyAsyncTask(this , httpHandler).execute();
    }

    /**
     * 下载图片
     * @param httpHandler
     * @param url
     * @param data
     * @param flag
     */
    public HTTP(HttpInterface.HttpHandler httpHandler, String url, String data , String flag) {
        this.url = url;
        this.data = data;
        new MyAsyncTaskDown(this , httpHandler).execute();
    }

    @Override
    public String connect() {
        //如果数据为空则是GET请求
        if (data == null) {
            return HttpConnection.HttpConnection(url);
        } else {
            //如果数据不为空则分两种清空
            //第一种判断Array<File>是不是为空，空则是单单的少量的文字上传
            //第二种不为空则是上传文件数组
            if (file == null) {
                return HttpConnection.HttpConnection(url, data);
            } else {
                FileImageUpload.uploadFile(file, url, data , key);
            }
        }
        return null;
    }

    @Override
    public Bitmap downconnect() {
        return FileImageUpload.downFile(url , data);
    }
}
