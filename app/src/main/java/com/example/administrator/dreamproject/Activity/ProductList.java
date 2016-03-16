package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/14.
 */
public class ProductList extends BaseActivity implements AdapterView.OnItemClickListener {

    private AcitivityData object = null;
    private ListView product_listview = null;

    private View view = null;
    private Context context = null;

    private ArrayList<ListView_Object> objects = null;
    private Activity activity = null;

    @Override
    public void setcontentView() {
        object = getIntent().getParcelableExtra("data");

        context = this;
        activity = (Activity) context;
        view = LayoutInflater.from(context).inflate(R.layout.productlistmain, null);
        contentView.addView(view);

        id();
    }

    @Override
    public void init() {

        addItem();

        setTitle(object.getTitle());
        Nav(0);
        TitleBarRight(false);

        product_listview.setAdapter(new MyBaseAdapter(context, objects, 1));

        product_listview.setOnItemClickListener(this);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
        }
    }

    private void id() {
        product_listview = (ListView) view.findViewById(R.id.product_listview);
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试" + i, "大家好" + i));
        }

//        objects.add(addObject(R.mipmap.ic_launcher, "测试"));
//        objects.add(addObject(R.mipmap.ic_launcher, "测试"));
//        objects.add(addObject(R.mipmap.ic_launcher, "测试"));
//        objects.add(addObject(R.mipmap.ic_launcher, "测试"));
//        objects.add(addObject(R.mipmap.ic_launcher, "测试"));
    }

    private ListView_Object addObject(Bitmap resid, String content, String subtitle) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setResid(resid);
        object.setSubtitle(subtitle);

        return object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Common.IntentActivity(context, WebViewActivity.class, addParcelable(objects.get(position).getContent(), "http://mp.weixin.qq.com/s?__biz=MzIyNzA0NDkwOQ==&mid=401318416&idx=1&sn=0d3f474bd11da86ef962c6d409dcb6a3&scene=18#wechat_redirect", Identification.Product_List));
    }

    /**
     * 传递参数有信息,标识
     *
     * @param title
     * @param Identification
     * @return
     */
    private Parcelable addParcelable(String title, String url, int Identification) {
        AcitivityData object = new AcitivityData();

        object.setUrl(url);
        object.setIdentification(Identification);
        object.setTitle(title);

        return object;
    }
}
