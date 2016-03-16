package com.example.administrator.ui_sdk.View;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.ui_sdk.Other.SystemBarTintManager;


/**
 * Created by Administrator on 2015/12/22.
 */
public class SystemManager {
    protected static SystemBarTintManager mTintManager;

    public static boolean TitleStatc = true;

    // 设置沉寂式状态栏
    public static void setTitleBarColor(int resid, Activity activity) {
        // if (Data.setup_chenjinshi.equals("false")) {
        // 判断版本号
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(TitleStatc, activity);
        }
        mTintManager = new SystemBarTintManager(activity);
        // 设置颜色
        // mTintManager.setStatusBarDarkMode(true, this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintColor(resid);
        // }
    }

    @SuppressLint("InlinedApi")
    public static void setTranslucentStatus(boolean on, Activity activity) {
        Window win = activity.getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置状态栏的颜色
     * 此方法是获取组件的背景颜色
     * 该方法为私有，现在仅提供内部使用
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void ViewColor(View view, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Drawable drawable = view.getBackground();
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            setTitleBarColor(colorDrawable.getColor(), activity);
        }
    }

    public static void setTitleStatc(boolean TitleStatc) {
        SystemManager.TitleStatc = TitleStatc;
    }



//    public static boolean BooleanKeyboard(){
//
//    }
}
