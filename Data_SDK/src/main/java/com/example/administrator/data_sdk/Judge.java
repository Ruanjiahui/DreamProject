package com.example.administrator.data_sdk;

/**
 * Created by Administrator on 2016/1/18.
 */
public class Judge {

    public static boolean Password(String oldpassword, String newpassword) {
        return oldpassword.equals(newpassword);
    }

    public static boolean Space(String str) {
        if (str.length() == 0 || str == null || str.equals("")) {
            return true;
        }
        return false;
    }
}
