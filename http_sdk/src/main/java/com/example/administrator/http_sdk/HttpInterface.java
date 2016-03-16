package com.example.administrator.http_sdk;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/2/15.
 */
public interface HttpInterface {

    public interface HttpConnect {

        public String connect();

        public Bitmap downconnect();
    }

    public interface HttpHandler {

        public void handler(String result);

        public void handler(Bitmap bitmap);
    }
}
