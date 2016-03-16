package com.example.administrator.dreamproject;

import android.os.Message;

import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.dreamproject.Activity.Load;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.websocket.Wamp;

/**
 * Created by Administrator on 2016/1/27.
 */
public class UserData {

    private static String result = null;

    public static String getUser(String json) {

        if (PATH.mConnection != null && PATH.mConnection.isConnected()) {
            PATH.mConnection.call("getuserinfo", String.class, new Wamp.CallHandler() {

                @Override
                public void onResult(Object result) {
                    //获取数据需要Base64转码
                    UserData.result = SystemData.Transcodingdecode(result.toString());
                    Message msg = new Message();
                    msg.obj = UserData.result;
                    Load.handler.sendMessage(msg);
//                    Log.e("hello" , "" + UserData.result.toString());
                }

                @Override
                public void onError(String errorUri, String errorDesc) {

                }
            }, json);
        }
        return UserData.result;
    }

    public static void ModifyInformation(String json) {

        if (PATH.mConnection != null && PATH.mConnection.isConnected()) {
            PATH.mConnection.call("sgc:updateuserinfo", String.class, new Wamp.CallHandler() {
                @Override
                public void onResult(Object result) {
                }

                @Override
                public void onError(String errorUri, String errorDesc) {
                }
            }, json);
        }
    }

    private HttpInterface.HttpHandler httpHandler = null;
    public UserData(HttpInterface.HttpHandler httpHandler){
        this.httpHandler = httpHandler;
    }

    public Wamp.EventHandler Pulish() {
        return new Wamp.EventHandler() {

            @Override
            public void onEvent(String topicUri, Object event) {
                httpHandler.handler(event.toString());
            }
        };
    }

    /**
     * 重新连接服务器
     */
    public static void Connect(HttpInterface.HttpHandler httpHandler) {
        UserData userData = new UserData(httpHandler);
        PATH.mConnection.subscribe("sgc:test", String.class, userData.Pulish());
    }
}
