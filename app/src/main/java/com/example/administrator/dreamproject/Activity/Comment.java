package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Fragment.Chat_Botton_Fragment;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.MyViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/15.
 */
public class Comment extends BaseActivity implements AdapterView.OnItemClickListener, MyOnClickInterface, TextWatcher {

    private View view = null;
    private Context context = null;
    private AcitivityData object = null;
    private ListView comment_listview = null;
    private ArrayList<ListView_Object> objects = null;
    private Activity activity = null;
    private RelativeLayout comment_relat = null;
    private ImageView chat_bottom_image = null;
    private MyViewPager chat_bottom_viewpager = null;
    private ArrayList<Fragment> list = null;
    private MyBaseAdapter adapter = null;
    private EditText chat_bottom_edit = null;
    private User user = null;
    private Button chat_bottom_but = null;


    //用户ID
    private String userid = "ID:" + 123456;
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
    //用户电话
    private String phone = "15119481373";
    //用户二维码
    private String qrcode = "一个链接";
    //用户生日
    private String birthday = "1994-7-4";
    //用户身高
    private int userheight = 175;
    //用户职业
    private String Occupation = "学生";
    //用户学历
    private String Education = "专科";
    //用户家乡
    private String Hometown = "广东阳江";
    //用户现居住地
    private String live = "广东广州";

//    @Override
//    public void setcontentView() {
//
//
//
//    }
//
//    @Override
//    public void init() {
//        addItem();
//        instance();
//        setBackground(R.color.WhiteSmoke);
//        setTitle(object.getTitle());
//        TitleBarRight(false);
//        Nav(0);
//
//        adapter = new MyBaseAdapter(context, objects, 1);
//        DensityUtil.setHeight(chat_bottom_viewpager, BaseActivity.height / 5);
//
//
//        comment_listview.setAdapter(adapter);
//        comment_listview.setOnItemClickListener(this);
//        comment_relat.setOnClickListener(this);
//        chat_bottom_image.setOnClickListener(this);
//        adapter.setIconClick(this);
//        chat_bottom_edit.setOnClickListener(this);
//        chat_bottom_but.setOnClickListener(this);
//        chat_bottom_edit.addTextChangedListener(this);
//    }

    @Override
    public void inti() {
        context = this;
        activity = (Activity) context;


        object = getIntent().getParcelableExtra("data");

        logo = Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher);
        identification = Identification.Comment;

        view = LayoutInflater.from(context).inflate(R.layout.comment, null);
        comment_listview = (ListView) view.findViewById(R.id.comment_listview);
        comment_relat = (RelativeLayout) view.findViewById(R.id.comment_relat);
        chat_bottom_image = (ImageView) view.findViewById(R.id.chat_bottom_image);
        chat_bottom_viewpager = (MyViewPager) view.findViewById(R.id.chat_bottom_viewpager);
        chat_bottom_edit = (EditText) view.findViewById(R.id.chat_bottom_edit);
        chat_bottom_but = (Button) view.findViewById(R.id.chat_bottom_but);

        contentView.addView(view);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
            case R.id.comment_relat:
                if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
                    chat_bottom_viewpager.setVisibility(View.GONE);
                }
                SystemInfo.Closekeyboard(activity);
                break;
            case R.id.chat_bottom_image:
                //判断viewpager是否显示
                if (chat_bottom_viewpager.getVisibility() == View.GONE) {
                    //判断软键盘是否显示
                    if (SystemInfo.KeyBoard(activity)) {
                        SystemInfo.Closekeyboard(activity);
                    }
                    chat_bottom_viewpager.setVisibility(View.VISIBLE);
                } else {
                    chat_bottom_viewpager.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_bottom_edit:
                chat_bottom_edit.setClickable(true);
                if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
                    chat_bottom_viewpager.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_bottom_but:
                objects.add(0 , addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), username, chat_bottom_edit.getText().toString(), SystemInfo.getSystemTime()));
                adapter.ChangeData(objects);
                chat_bottom_edit.setText("");

                break;
        }
    }

    private void instance() {
        list = new ArrayList<>();

        list.add(new Chat_Botton_Fragment());
        list.add(new Chat_Botton_Fragment());

//        chat_bottom_viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), list, false));
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试" + i, "" + i, "" + i));
        }
    }

    private ListView_Object addObject(Bitmap resid, String content, String subtitle, String right_title) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setResid(resid);
        object.setSubtitle(subtitle);
        object.setRight_title(right_title);

        return object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //判断viewpager是否显示
        if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
            chat_bottom_viewpager.setVisibility(View.GONE);
            //判断软键盘是否显示
        } else {
            Common.IntentActivity(context, Comment_List.class, addParcelable(position, objects.get(position).getSubtitle(), objects.get(position).getRight_title(), objects.get(position).getContent(), logo, Identification.Comment));
        }

    }

    private Parcelable addParcelable(int id, String content, String time, String person, Bitmap bitmap, int identification) {
        AcitivityData acitivityData = new AcitivityData();

        acitivityData.setContent(content);
        acitivityData.setTitle(person);
        acitivityData.setID(id);
        acitivityData.setTime(time);
        acitivityData.setIdentification(identification);

        return acitivityData;
    }

    @Override
    public void ItemOnClick(int position) {
        //跳转到个人信息
        user = addUser(userid, objects.get(position).getContent(), sex, logo, grade, autograph, Identification.Click_Logo, phone
                , qrcode, birthday, userheight, Occupation, Education, Hometown, live);
        Common.IntentActivity(context, Personal.class, user);
    }

    private User addUser(String userid, String username, int sex, Bitmap logo, String grade, String autograph, int Identification, String phone
            , String qrcode, String birthday, int height, String Occupation, String Education, String Hometown, String live) {
        return Common.addUser(userid, username, sex, logo, grade, autograph, Identification, phone
                , qrcode, birthday, height, Occupation, Education, Hometown, live);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (chat_bottom_edit.getText().toString().length() > 0) {
            chat_bottom_but.setClickable(true);
            chat_bottom_but.setEnabled(true);
            chat_bottom_but.setBackgroundResource(R.drawable.button_selector);
        } else {
            chat_bottom_but.setClickable(false);
            chat_bottom_but.setEnabled(false);
            chat_bottom_but.setBackgroundResource(R.drawable.button_down);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            SystemInfo.Closekeyboard(activity);
        }
        return super.onTouchEvent(event);
    }
}
