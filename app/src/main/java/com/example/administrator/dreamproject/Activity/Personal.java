package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.ConfigurationOperation;
import com.example.administrator.data_sdk.FileUtil.FileImageUpload;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Fragment.PersonalFragment1;
import com.example.administrator.dreamproject.Fragment.PersonalFragment2;
import com.example.administrator.dreamproject.Fragment.PersonalFragment3;
import com.example.administrator.dreamproject.Fragment.PersonalFragment4;
import com.example.administrator.dreamproject.Fragment.PersonalFragment5;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SystemImage;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.ui_sdk.View.SystemManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Personal extends FragmentActivity implements View.OnClickListener, HttpInterface.HttpHandler {

    private View personal_top = null;

    private LinearLayout personallieanr = null;
    private LinearLayout personal_content = null;
    private RelativeLayout personallinear1 = null;
    private ScrollView personal_scroll = null;

    private TextView personal_text, personal_text1, personal_text2, personal_text3, personal_text4;
    private RelativeLayout melinear_back = null;
    //    private ImageView mesetting = null;
    private LinearLayout personal_editdata_linear = null;
    private TextView personal_editdata = null;
    private TextView personal_chat = null;
    private TextView person_top_name = null;
    private ImageView person_top_image = null;
    private ImageView person_top_sex = null;
    private TextView person_top_id = null;
    private TextView personal_follow = null;

    private Context context = null;
    private ConfigurationOperation object = null;

    private TextView person_top_text1, person_top_text2, person_top_text3 = null;

    private String title = "";

    private User user = null;
    public static Activity activity = null;
    private final int IMAGE_CODE = 100;
    private Bitmap bitmap = null;
    private HttpInterface.HttpHandler httpHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personalmain);

        Applications.getInstance().addActivity(this);

        user = getIntent().getParcelableExtra("data");

        context = Personal.this;
        activity = (Activity) context;

        httpHandler = this;

        personal_top = findViewById(R.id.personal_top);
        personallieanr = (LinearLayout) findViewById(R.id.personallieanr);
        personal_content = (LinearLayout) findViewById(R.id.personal_content);
        personal_text = (TextView) findViewById(R.id.personal_text);
        personal_text1 = (TextView) findViewById(R.id.personal_text1);
        personal_text2 = (TextView) findViewById(R.id.personal_text2);
        personal_text3 = (TextView) findViewById(R.id.personal_text3);
        personal_text4 = (TextView) findViewById(R.id.personal_text4);
        personallinear1 = (RelativeLayout) findViewById(R.id.personallinear1);
        personal_scroll = (ScrollView) findViewById(R.id.personal_scroll);
//        mesetting = (ImageView) findViewById(R.id.mesetting);
        melinear_back = (RelativeLayout) findViewById(R.id.melinear_back);
        personal_editdata_linear = (LinearLayout) findViewById(R.id.personal_editdata_linear);
        person_top_text1 = (TextView) findViewById(R.id.person_top_text1);
        person_top_text2 = (TextView) findViewById(R.id.person_top_text2);
        person_top_text3 = (TextView) findViewById(R.id.person_top_text3);
        personal_editdata = (TextView) findViewById(R.id.personal_editdata);
        personal_chat = (TextView) findViewById(R.id.personal_chat);
        person_top_name = (TextView) findViewById(R.id.person_top_name);
        person_top_image = (ImageView) findViewById(R.id.person_top_image);
        person_top_sex = (ImageView) findViewById(R.id.person_top_sex);
        person_top_id = (TextView) findViewById(R.id.person_top_id);
        personal_follow = (TextView) findViewById(R.id.personal_follow);


        personal_scroll.smoothScrollTo(0, 20);
        //判断是否将状态栏设置透明
        SystemManager.setTitleBarColor(android.R.color.transparent, this);
        DensityUtil.setHeight(personal_top, BaseActivity.height / 8 * 3);
        DensityUtil.setHeight(personallieanr, BaseActivity.height / 15);
        DensityUtil.setRelHeight(personallinear1, BaseActivity.height / 15);


        personal_text.setOnClickListener(this);
        personal_text1.setOnClickListener(this);
        personal_text2.setOnClickListener(this);
        personal_text3.setOnClickListener(this);
        personal_text4.setOnClickListener(this);
//        mesetting.setOnClickListener(this);
        melinear_back.setOnClickListener(this);
        person_top_text1.setOnClickListener(this);
        person_top_text2.setOnClickListener(this);
        person_top_text3.setOnClickListener(this);
        personal_editdata.setOnClickListener(this);
        personal_chat.setOnClickListener(this);
        person_top_image.setOnClickListener(this);
        personal_follow.setOnClickListener(this);

        intentFragment(new PersonalFragment1());

        person_top_name.setText(user.getUsername());
        person_top_id.setText("" + user.getUserid());
        person_top_image.setImageBitmap(user.getLogo());
        if (user.getSex() == 1) {
            person_top_sex.setImageResource(R.mipmap.man);
        } else if (user.getSex() == 2) {
            person_top_sex.setImageResource(R.mipmap.women);
        }

        if (user.getIdentification() == Identification.Me || user.getIdentification() == Identification.Product) {
        } else {
            if (user.getIdentification() == Identification.Fans) {
                personal_follow.setText("相互关注中");
            } else if (user.getIdentification() == Identification.Follow) {
                personal_follow.setText("关注中");
            }
            personal_editdata.setText("更多");
            personal_follow.setVisibility(View.VISIBLE);
            personal_chat.setVisibility(View.VISIBLE);
        }

        new HTTP(httpHandler, PATH.Personal_article, "");

    }

    /**
     * 跳转Fragment
     *
     * @param targetFragment
     */
    private void intentFragment(Fragment targetFragment) {

        if (!targetFragment.isVisible()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", user);
            targetFragment.setArguments(bundle);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.personal_content, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_text:
                IntentFragment(0);
                break;
            case R.id.personal_text1:
                IntentFragment(1);
                break;
            case R.id.personal_text2:
                IntentFragment(2);
                break;
            case R.id.personal_text3:
                IntentFragment(3);
                break;
            case R.id.personal_text4:
                IntentFragment(4);
                break;
            case R.id.melinear_back:
                //退出该页面
                //回传数据
                if (user.getIdentification() != Identification.Firend) {
                    Common.SetActivity(activity, user, user.getIdentification());
                }
                finish();
                break;
            case R.id.personal_editdata:
                if (user.getIdentification() == Identification.Me) {
                    title = "个人信息";
                    Common.IntentActivity(activity, Personal_Information.class, user, Identification.Personal);
                } else {
                    ArrayList<ListView_Object> list = new ArrayList<>();

                    list.add(additem("拉入黑名单", DensityUtil.dip2px(context, 50)));
                    list.add(additem("投诉", DensityUtil.dip2px(context, 50)));
                    MyDialog myDialog = new MyDialog(context, R.style.mydialog);
                    myDialog.setHeight(DensityUtil.dip2px(context, 60) * 2);
                    myDialog.setWidth(BaseActivity.width / 3 * 2);
                    myDialog.DialogState("", list, 0, "", "");
                    myDialog.show();
                }
                break;
            case R.id.personal_chat:
                Common.IntentActivity(activity, Chat.class, addParcelable(person_top_name.getText().toString(), Identification.Personal), Identification.Personal, user);
                break;
            case R.id.person_top_text1:
                title = "TA关注的人";
                if (user.getIdentification() == Identification.Me) {
                    title = "我关注的人";
                }
                Common.IntentActivity(context, People.class, addParcelable(title, Identification.Personal));
                break;
            case R.id.person_top_text2:
                title = "TA的粉丝";
                if (user.getIdentification() == Identification.Me) {
                    title = "我的粉丝";
                }
                Common.IntentActivity(context, People.class, addParcelable(title, Identification.Personal));
                break;
            case R.id.person_top_text3:
                break;
            case R.id.person_top_image:
                if (user.getIdentification() == Identification.Me || user.getIdentification() == Identification.Product) {
                    Common.IntentActivity(context, SystemImage.class);
//                    SystemData.SystemImage(activity, IMAGE_CODE);
                } else {
                    intentFragment(new PersonalFragment3());
                }
                break;
            case R.id.personal_follow:
                //如果是关注的人或者是粉丝就可以取消关注
                if (user.getIdentification() == Identification.Follow || user.getIdentification() == Identification.Fans) {
                    personal_follow.setText("关注");
                    SystemInfo.showToast(context, "取消关注成功");
                }
                break;
        }
    }

    private ListView_Object additem(String content, int item_height) {
        ListView_Object object = new ListView_Object();
        object.setContent(content);
        object.setItem_height(item_height);

        return object;
    }

    private void IntentFragment(int flag) {
        switch (flag) {
            case 0:
                intentFragment(new PersonalFragment1());
                break;
            case 1:
                intentFragment(new PersonalFragment2());
                break;
            case 2:
                intentFragment(new PersonalFragment3());
                break;
            case 3:
                intentFragment(new PersonalFragment4());
                break;
            case 4:
                intentFragment(new PersonalFragment5());
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
            case IMAGE_CODE:
                if (resultCode != RESULT_OK) {
                    return;
                }
                bitmap = SystemData.handlerbitmap(context, data, 60, 60);
                if (bitmap == null) {
                    Toast.makeText(context, "图片更换失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                bitmap = SystemData.createRoundConerImage(Transformation.zoomImg(context, bitmap, 100, 100));
                user.setLogo(bitmap);
                person_top_image.setImageBitmap(bitmap);
                //回传数据
                Common.SetActivity(activity, user, user.getIdentification());
                break;

            case 11110:
                if (data != null) {
                    if (data.getParcelableExtra("data") != null) {
                        user = data.getParcelableExtra("data");
                        person_top_name.setText(user.getUsername());
                        person_top_id.setText("" + user.getUserid());
                        person_top_image.setImageBitmap(user.getLogo());
                    }
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        if (CommenData.bitmaps != null) {
            bitmap = Transformation.Path2Bitmap(CommenData.bitmaps);
            if (bitmap == null) {
                Toast.makeText(context, "图片更换失败", Toast.LENGTH_SHORT).show();
                return;
            }
            //上传头像
            //本来添加一张图片由于链表减一所以添加多一张图片
            ArrayList<String> iconlist = new ArrayList<>();
            iconlist.add(CommenData.bitmaps);
            iconlist.add(null);
            new HTTP(this, FileImageUpload.saveFile(iconlist), PATH.uploadicon, "userId=" + user.getUserid() , "icon");

            //保存头像到本地
            FileWrite.saveBitmapFile(bitmap, user.getUserid(), PATH.Logo);

            bitmap = SystemData.createRoundConerImage(Transformation.zoomImg(context, bitmap, 100, 100));
            user.setLogo(bitmap);
            person_top_image.setImageBitmap(bitmap);
        }
        super.onRestart();
    }

    @Override
    public void handler(String result) {

    }

    @Override
    public void handler(Bitmap bitmap) {

    }
}
