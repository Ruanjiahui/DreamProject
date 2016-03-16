package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.ui_sdk.View.MyWebView;
import com.example.administrator.ui_sdk.View.SystemManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public class WebViewActivity extends Activity implements View.OnClickListener {

    private AcitivityData object = null;

    private MyWebView webview = null;

    private View webview_top = null;
    private TextView base_top_title = null;
    private TextView base_top_text1 = null;
    private RelativeLayout base_top_relative = null;

    private Context context = null;
    private Activity activity = null;
    private TextView webview_bottom_text2 = null;
    private TextView webview_bottom_text3 = null;
    private View webview_bottom = null;
    private ArrayList<ListView_Object> objects = null;
    private MyDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webviewmain);

        context = WebViewActivity.this;
        activity = (Activity) context;
        //获取页面传输过来的对象
        object = getIntent().getParcelableExtra("data");

        id();

        DensityUtil.setRelHeight(webview_top, BaseActivity.height / 12);
        webview.setWebView(object.getUrl());
        webview_top.setBackgroundResource(R.color.Blue);
        base_top_title.setText(object.getTitle());

        //设置沉寂式状态栏
        SystemManager.ViewColor(webview_top, activity);

        base_top_relative.setOnClickListener(this);
        webview_bottom_text2.setOnClickListener(this);
        webview_bottom_text3.setOnClickListener(this);
    }

    private void id() {
        webview = (MyWebView) findViewById(R.id.webview);
        webview_top = findViewById(R.id.webview_top);
        base_top_relative = (RelativeLayout) findViewById(R.id.base_top_relative);
        base_top_title = (TextView) findViewById(R.id.base_top_title);
        base_top_text1 = (TextView) findViewById(R.id.base_top_text1);
        webview_bottom = findViewById(R.id.webview_bottom);
        webview_bottom_text2 = (TextView) findViewById(R.id.webview_bottom_text2);
        webview_bottom_text3 = (TextView) findViewById(R.id.webview_bottom_text3);
        //隐藏右边文字
        base_top_text1.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
            case R.id.webview_bottom_text2:
                Common.IntentActivity(context, Comment.class, addParcelable(webview_bottom_text2.getText().toString(), Identification.Article_Content));
                break;
            case R.id.webview_bottom_text3:
                createDialog().show();
                break;
        }
    }

    private Parcelable addParcelable(String title, int Identification) {
        AcitivityData object = new AcitivityData();
        object.setTitle(title);
        object.setIdentification(Identification);

        return object;
    }

    /**
     * 创建Dialog
     */
    private Dialog createDialog() {
        addItem();
        dialog = new MyDialog(context, R.style.mydialog);
        dialog.DialogState("", objects, 0, "", "");
        dialog.setWidth(BaseActivity.width / 3 * 2);
        dialog.setHeight(DensityUtil.dip2px(context, 55) * 4);
        dialog.setOnItemClick(new MyOnClickInterface.ItemClick() {
            @Override
            public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        SystemInfo.showToast(context, "分享微信朋友");
                        break;
                    case 1:
                        SystemInfo.showToast(context, "分享朋友圈");
                        break;
                    case 2:
                        SystemInfo.showToast(context, "分享QQ好友");
                        break;
                    case 3:
                        SystemInfo.showToast(context, "分享QQ空间");
                        break;
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private void addItem() {
        objects = new ArrayList<>();
//        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "转发朋友", DensityUtil.dip2px(context, 50)));
//        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "转发群组", DensityUtil.dip2px(context, 50)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.icon64_wx_logo), "微信朋友", DensityUtil.dip2px(context, 50)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.icon_comment), "朋友圈", DensityUtil.dip2px(context, 50)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.qq), "QQ好友", DensityUtil.dip2px(context, 50)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.qqzone), "QQ空间", DensityUtil.dip2px(context, 50)));
    }

    private ListView_Object addObject(Bitmap resid, String content, int item_height) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setResid(resid);
        object.setItem_height(item_height);

        return object;
    }

}
