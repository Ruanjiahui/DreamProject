package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/14.
 */
public class People extends BaseActivity implements AdapterView.OnItemClickListener {


    private Context context = null;
    private View view = null;
    private AcitivityData data = null;

    private ListView people_listview = null;
    private ArrayList<ListView_Object> objects = null;

    private User user = null;

    //用户ID
    private String userid = "ID:";
    //用户名字
    private String username = "阮家辉";
    //用户性别
    private int sex = 1;
    //用户头像
    private Bitmap logo = null;
    //用户等级
    private String grade = "黄金钻石级";
    //用户个人签名
    private String autograph = "个性签名";
    //标识
    private int identification = Identification.Me;
    private Activity activity = null;
    private MyBaseAdapter adapter = null;

    private EditText people_edit = null;

    @Override
    public void setcontentView() {
        context = this;
        activity = (Activity) context;

        logo = Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher);

        data = getIntent().getParcelableExtra("data");
        view = LayoutInflater.from(context).inflate(R.layout.people, null);

        people_listview = (ListView) view.findViewById(R.id.people_listview);
        people_edit = (EditText) view.findViewById(R.id.people_edit);

        contentView.addView(view);
    }

    @Override
    public void init() {

        addItem();
        TitleBarRight(false);
        setTitle(data.getTitle());
        setBackground(R.color.WhiteSmoke);
        Nav(0);

        adapter = new MyBaseAdapter(context, objects, 0);
        people_listview.setAdapter(adapter);
        people_listview.setOnItemClickListener(this);
        people_edit.setOnClickListener(this);

    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
            case R.id.people_edit:
                AcitivityData acitivityData = new AcitivityData();
                acitivityData.setIdentification(Identification.Follow);
                Common.IntentActivity(context, Show.class, acitivityData);
                break;
        }
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试" + i));
        }
    }

    private ListView_Object addObject(Bitmap resid, String content) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setResid(resid);

        return object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        user = addUser(userid + objects.get(position).getContent(), objects.get(position).getContent(), sex, logo, grade, autograph, data.getIdentification(), "", "", "", 0, "", "", "", "");
        Common.IntentActivity(context, Personal.class, user);
    }

    private User addUser(String userid, String username, int sex, Bitmap logo, String grade, String autograph, int Identification, String phone
            , String qrcode, String birthday, int height, String Occupation, String Education, String Hometown, String live) {
        return Common.addUser(userid, username, sex, logo, grade, autograph, Identification, phone
                , qrcode, birthday, height, Occupation, Education, Hometown, live);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
