package com.example.administrator.data_sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/12.
 */
public class Common {

    public static void IntentActivity(Context context, Class cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 传输Serializable对象
     *
     * @param context
     * @param cls
     * @param data
     */
    public static void IntentActivity(Context context, Class cls, Serializable data) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 传输Parcelable对象
     *
     * @param context
     * @param cls
     * @param data
     */
    public static void IntentActivity(Context context, Class cls, Parcelable data) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        intent.putExtras(bundle);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 传输Parcelable对象
     *
     * @param context
     * @param cls
     * @param data
     */
    public static void IntentActivity(Context context, Class cls, Parcelable data, Parcelable user) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }


    /**
     * 传输Parcelable对象附带返回值
     *
     * @param activity
     * @param cls
     * @param data
     * @param resultCode
     */
    public static void IntentActivity(Activity activity, Class cls, Parcelable data, int resultCode) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        intent.putExtras(bundle);
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, resultCode);
    }

    /**
     * 跳转页面
     *
     * @param activity
     * @param cls
     * @param data
     * @param resultCode
     * @param user
     */
    public static void IntentActivity(Activity activity, Class cls, Parcelable data, int resultCode, Parcelable user) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        bundle.putParcelable("user", user);
        intent.putExtras(bundle);
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, resultCode);
    }

    public static void SetActivity(Activity activity, Parcelable data, int resultCode) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        intent.putExtras(bundle);
        activity.setResult(resultCode, intent);
    }

    /**
     * 封装数据User
     *
     * @param userid
     * @param username
     * @param sex
     * @param logo
     * @param grade
     * @param autograph
     * @return
     */
    public static User addUser(String userid, String username, int sex, Bitmap logo, String grade, String autograph, int Identification) {
        User user = new User();

        user.setUserid(userid);
        user.setUsername(username);
        user.setSex(sex);
        user.setLogo(logo);
        user.setGrade(grade);
        user.setAutograph(autograph);
        user.setIdentification(Identification);

        return user;
    }

    /**
     * 用户全部信息
     *
     * @param userid
     * @param username
     * @param sex
     * @param logo
     * @param grade
     * @param autograph
     * @param Identification
     * @param phone
     * @param qrcode
     * @param birthday
     * @param height
     * @param Occupation
     * @param Education
     * @param Hometown
     * @param live
     * @return
     */
    public static User addUser(String userid, String username, int sex, Bitmap logo, String grade, String autograph, int Identification, String phone
            , String qrcode, String birthday, int height, String Occupation, String Education, String Hometown, String live) {
        User user = new User();

        user.setUserid(userid);
        user.setUsername(username);
        user.setSex(sex);
        user.setLogo(logo);
        user.setGrade(grade);
        user.setAutograph(autograph);
        user.setIdentification(Identification);
        user.setPhone(phone);
        user.setQrcode(qrcode);
        user.setBirthday(birthday);
        user.setHeight(height);
        user.setOccupation(Occupation);
        user.setEducation(Education);
        user.setHometown(Hometown);
        user.setLive(live);

        return user;
    }

}
