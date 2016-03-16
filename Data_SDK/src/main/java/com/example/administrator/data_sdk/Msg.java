package com.example.administrator.data_sdk;

/**
 * Created by Administrator on 2016/2/1.
 */
public class Msg {

    //用户id
    private String id = "";
    //用户名字
    private String name = "";
    //用户信息
    private String content = "";
    //消息标志
    private String channelid = "";
    //用户发消息标志
    private boolean memsg = false;
    //标题
    private String read = "";

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public void setMemsg(boolean memsg) {
        this.memsg = memsg;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getChannelid() {
        return channelid;
    }

    public boolean getMemsg() {
        return memsg;
    }

    public String getRead() {
        return read;
    }

}
