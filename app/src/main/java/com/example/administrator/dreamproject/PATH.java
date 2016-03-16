package com.example.administrator.dreamproject;

import android.os.Environment;

import com.example.administrator.data_sdk.User;
import com.example.administrator.websocket.Wamp;
import com.example.administrator.websocket.WampConnection;

/**
 * Created by Administrator on 2016/1/21.
 */
public class PATH {

    //这个路径是配置文件的路径
    public static String Properties = "/data/data/com.example.administrator.dreamproject/Properties.dat";
    //保存头像的路径
    public static String Logo = Environment.getExternalStorageDirectory() + "/Dream/Head/";
    //保存文章头像的路径
    public static String article_Logo = Environment.getExternalStorageDirectory() + "/Dream/article/";
    //登录的网址
//    public static String Load = "ws://10.0.3.2:8080/WebSocket/Main/";
    public static String Load = "ws://61.144.222.145:5414/sgc/auth/url/";
    //数据库名称
    public static String ihuo = "ihuo";
    //用户设置表
    public static String serverinfo = "serverinfo";
    //用户信息表
    public static String userinfo = "userinfo";
    //文章信息表
    public static String articleinfo = "article";
    //文章信息表
    public static String userarticleinfo = "userarticle";
    //用户消息
    public static String msg = "chatmsg";
    //链接状态
    public static Wamp mConnection = new WampConnection();
    //
    public static User user = null;
    //发表图文的IP
    public static String IP = "http://113.108.150.15:8888/";
    //测试
//    public static String IP = "http://192.168.1.60:8080/";
    //发表文字的链接
    public static String Word = PATH.IP + "UEditor/pasteplain!json";
    //发表图片的链接
    public static String picture = IP + "UEditor/imagetextjson";
    //获取发布的文章信息
    public static String information = IP + "UEditor/showarticle";
    //访问文章网址
    public static String article = IP + "UEditor/html/";
    //上传头像的链接
    public static String uploadicon = IP + "UEditor/usericon";
    //获取头像的链接
    public static String downicon = IP + "UEditor/image/icon";
    //获取文章的图片
    public static String articleicon = IP  + "UEditor/image/upload";
    //获取个人的发表文章的信息
    public static String Personal_article = IP + "UEditor/showuserarticle";
    //"WChat/WChat";//
    public static String acString = "";
    //
    public static String userallpri = Environment.getExternalStorageDirectory() + "/Dream/allpicture/";

    //获取个人的所有照片
    public static String userpicture = IP + "UEditor/userAllImg";
//    public static String te = "http://www.baidu.com";
}
