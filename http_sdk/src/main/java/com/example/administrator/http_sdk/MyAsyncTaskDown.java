package com.example.administrator.http_sdk;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/2/15.
 */
public class MyAsyncTaskDown extends AsyncTask {

    private HttpInterface.HttpConnect httpConnect = null;
    private HttpInterface.HttpHandler httpHandler = null;
    private Object object = null;

    public MyAsyncTaskDown(HttpInterface.HttpConnect httpConnect) {
        this.httpConnect = httpConnect;
    }

    public MyAsyncTaskDown(HttpInterface.HttpConnect httpConnect, HttpInterface.HttpHandler httpHandler) {
        this.httpConnect = httpConnect;
        this.httpHandler = httpHandler;
    }

    //正在执行线程
    @Override
    protected Object doInBackground(Object[] params) {
        object = httpConnect.downconnect();
        return null;
    }

    //被调用后立即执行，一般用来在执行后台任务前对UI做一些标记
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //此方法被执行，直接将进度信息更新到UI组件上。
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    //更新主界面
    @Override
    protected void onPostExecute(Object o) {
//        if (o != null) {
            httpHandler.handler((Bitmap)object);
//        } else {
//            httpHandler.handler(null);
//        }
        super.onPostExecute(o);
    }
}
