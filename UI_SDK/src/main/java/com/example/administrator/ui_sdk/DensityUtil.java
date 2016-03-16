package com.example.administrator.ui_sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.ui_sdk.View.SystemManager;

/**
 * Created by Administrator on 2016/1/11.
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置组件的高度
     *
     * @param view
     * @param height
     */
    public static void setHeight(View view, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * 设置组件的高度
     *
     * @param view
     * @param width
     */
    public static void setWidth(View view, int width) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = width;
        view.setLayoutParams(params);
    }

    /**
     * 设置组件的高度
     *
     * @param view
     * @param height
     */
    public static void setItemHeight(View view, int height) {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = height;
        view.setLayoutParams(params);
    }


    /**
     * 设置Relayou布局的高度
     *
     * @param view
     * @param height
     */
    public static void setRelHeight(View view, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * 设置Relayou布局的高度
     *
     * @param view
     * @param width
     */
    public static void setRelWidth(View view, int width) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = width;
        view.setLayoutParams(params);
    }


    public static void setAbsSize(View view, int width, int height) {
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
    public static void setLinearSize(View view, int width, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void setRelatSize(View view, int width, int height) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }


    /**
     * 设置状态栏的颜色
     * 此方法是获取组件的背景颜色
     * 该方法为私有，现在仅提供内部使用
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void ViewColor(View view, Activity activity) {
        Drawable drawable = view.getBackground();
        ColorDrawable colorDrawable = (ColorDrawable) drawable;
        new SystemManager().setTitleBarColor(colorDrawable.getColor(), activity);
    }

    public static void setLinearSize(View view, int width, int height, int up, int down, int left, int right) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = width;
        params.setMargins(left, up, right, down);
        params.height = height;
        view.setLayoutParams(params);
    }

}
