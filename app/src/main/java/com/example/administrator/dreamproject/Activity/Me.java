package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Me extends BaseActivity implements AdapterView.OnItemClickListener {


    private View view = null;
    private ListView melistview = null;
    private View meperson = null;
    private Context context = null;
    private ImageView person_image = null;
    private TextView person_name = null;
    private TextView person_subtitle = null;

    private ArrayList<ListView_Object> objects = null;

    private User user = null;
    private Activity activity = null;


    /**
     * 添加布局到BaseActivity
     */
    @Override
    public void setcontentView() {
        user = getIntent().getParcelableExtra("data");

        view = LayoutInflater.from(this).inflate(R.layout.memain, null);
        person_image = (ImageView) view.findViewById(R.id.person_image);
        person_name = (TextView) view.findViewById(R.id.person_name);
        person_subtitle = (TextView) view.findViewById(R.id.person_subtitle);

        person_image.setImageBitmap(user.getLogo());
        person_name.setText(user.getUsername());
        person_subtitle.setText(user.getAutograph());

        contentView.addView(view);

        context = this;
        activity = (Activity) context;
    }

    /**
     * 初始化操作
     */
    @Override
    public void init() {
        id();

        addItem();

        melistview.setAdapter(new MyBaseAdapter(context, objects, 1));
//
//        //设置沉寂式状态栏
////        SystemManager.ViewColor(meperson);
        DensityUtil.setHeight(meperson, BaseActivity.height / 8);
//        DensityUtil.setRelWidth(person_image, BaseActivity.height / 10);
        //去掉导航条
        Nav(0);
        //设置标题文字
        setTitle("我");
        TitleBarRight(false);

        meperson.setOnClickListener(this);
        melistview.setOnItemClickListener(this);

    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                //退出该页面
                //回传数据
                Common.SetActivity(activity, user, Identification.Main_Logo);
                finish();
                break;
            case R.id.meperson:
                //跳转到个人信息
                Common.IntentActivity(activity, Personal.class, user, Identification.Me);
                break;
        }
    }

    /**
     * 获取组件ID操作
     */
    private void id() {
        meperson = view.findViewById(R.id.meperson);
        base_top_relative = (RelativeLayout) findViewById(R.id.base_top_relative);
        melistview = (ListView) findViewById(R.id.melistview);

    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.vip), "会员中心", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.eye), "谁看过我", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试", "", "-", "", DensityUtil.dip2px(context, 20), Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.person_information), "个人信息", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.article), "我的作品", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.dollar), "我的金币", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.briefcase), "我的背包", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试", "", "-", "", DensityUtil.dip2px(context, 20), Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.group), "我的群组", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.search_people), "我关注的人", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.fans), "我的粉丝", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试", "", "-", "", DensityUtil.dip2px(context, 20), Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.add_article), "我关注的产品", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.collection), "我收藏的产品", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.right)));
    }

    private ListView_Object addObject(Bitmap resid, String content, String subtitle, String Prompt, String Prompt_content, int Height, Bitmap right_resid) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setSubtitle(subtitle);
        object.setPrompt(Prompt);
        object.setPromptContent(Prompt_content);
        object.setHeight(Height);
        object.setResid(resid);
        object.setResid_right(right_resid);
//        object.setItem_height(DensityUtil.dip2px(context, 60));

        return object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 3:
                //跳转到个人信息
                Common.IntentActivity(activity, Personal_Information.class, user, Identification.Me);
                break;
            case 4:
                //跳转到作品列表
                Common.IntentActivity(context, Personal.class, user);
                break;
            case 9:
                //跳转到我关注的人
                Common.IntentActivity(context, People.class, addParcelable(objects.get(position).getContent(), Identification.Follow));
                break;
            case 10:
                //跳转到我的粉丝
                Common.IntentActivity(context, People.class, addParcelable(objects.get(position).getContent(), Identification.Fans));
                break;
            case 12:
                //跳转到作品列表
                Common.IntentActivity(context, ProductList.class, addParcelable(objects.get(position).getContent(), Identification.Product_Follow));
                break;
            case 13:
                //跳转到作品列表
                Common.IntentActivity(context, ProductList.class, addParcelable(objects.get(position).getContent(), Identification.Product_Collection));
                break;
        }
    }

    /**
     * 传递参数的标识
     *
     * @param title
     * @return
     */
    private Parcelable addParcelable(String title, int Identification) {
        AcitivityData data = new AcitivityData();
        data.setIdentification(Identification);
        data.setTitle(title);

        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 111:
                if (data != null) {
                    if (data.getParcelableExtra("data") != null) {
                        user = data.getParcelableExtra("data");
                        person_image.setImageBitmap(user.getLogo());
                        person_name.setText(user.getUsername());
                        person_subtitle.setText(user.getAutograph());
                    }
                }
                break;
        }
    }
}
