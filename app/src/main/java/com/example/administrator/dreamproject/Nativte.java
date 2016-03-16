package com.example.administrator.dreamproject;

import android.graphics.Bitmap;

import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;

/**
 * Created by Administrator on 2016/1/28.
 */
public class Nativte {

    //用户ID
    public static String userid = "" + 123456;
    //用户名字
    public static String username = ".....";
    //用户性别
    public static int sex = 1;
    //用户头像
    public static Bitmap logo = Transformation.Resource2Bitmap(MainActivity.activity, R.mipmap.logo);
    //用户等级
    public static String grade = "黄金钻石级";
    //用户个人签名
    public static String autograph = "个性签名";
    //标识
    public static int identification = Identification.Me;
    //用户电话
    public static String phone = "15119481373";
    //用户二维码
    public static String qrcode = "一个链接";
    //用户生日
    public static String birthday = "1994-7-4";
    //用户身高
    public static int userheight = 175;
    //用户职业
    public static String Occupation = "学生";
    //用户学历
    public static String Education = "专科";
    //用户家乡
    public static String Hometown = "广东阳江";
    //用户现居住地
    public static String live = "";

    public static User NativeUser() {
        User user = new User();
        user = addUser(Nativte.userid, Nativte.username, Nativte.sex, Nativte.logo, Nativte.grade, Nativte.autograph, Nativte.identification, Nativte.phone
                , Nativte.qrcode, Nativte.birthday, Nativte.userheight, Nativte.Occupation, Nativte.Education, Nativte.Hometown, Nativte.live);
        return user;
    }

    private static User addUser(String userid, String username, int sex, Bitmap logo, String grade, String autograph, int Identification, String phone
            , String qrcode, String birthday, int height, String Occupation, String Education, String Hometown, String live) {
        return Common.addUser(userid, username, sex, logo, grade, autograph, Identification, phone
                , qrcode, birthday, height, Occupation, Education, Hometown, live);
    }
}
