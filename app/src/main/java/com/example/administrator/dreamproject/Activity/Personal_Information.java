package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileImageUpload;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.Other;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.dreamproject.SystemImage;
import com.example.administrator.dreamproject.UserData;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.MyDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Personal_Information extends BaseActivity implements AdapterView.OnItemClickListener, MyOnClickInterface.Click , HttpInterface.HttpHandler{

    private View view = null;
    private Context context = null;
    private ListView personal_information_listview = null;

    private ArrayList<ListView_Object> list = null;

    private String[] height = new String[]{"小学", "初中", "高中", "中专", "大专", "本科", "研究生", "硕士", "博士", "博士后", "院士"};


    private MyDialog dialog = null;
    private User user = null;
    private final int IMAGE_CODE = 100;   //这里的IMAGE_CODE是自己任意定义的
    private MyBaseAdapter adapter = null;
    private int STATE = 0;

    private AcitivityData data = null;
    private Activity activity = null;
    private int RESULT = 0;
    private Bitmap bitmap = null;
    private boolean upload = false;

//    private DatabaseHelper databaseHelper = null;
//    private Cursor cursor = null;


    @Override
    public void setcontentView() {

        user = getIntent().getParcelableExtra("data");


        context = this;
        activity = (Activity) context;
        view = LayoutInflater.from(context).inflate(R.layout.personal_information_main, null);

        personal_information_listview = (ListView) view.findViewById(R.id.personal_information_listview);

        contentView.addView(view);
    }

    @Override
    public void init() {
        additem();

        //设置标题
        setTitle("个人信息");
        setTitleRight("上传");
        Nav(0);
        adapter = new MyBaseAdapter(context, list, 1);
        personal_information_listview.setAdapter(adapter);

        personal_information_listview.setOnItemClickListener(this);
    }

    /**
     * 添加item子项
     */
    private void additem() {
        list = new ArrayList<>();

        list.add(addObject("头像", "", user.getLogo(), "", "", 0, 80));
        list.add(addObject("", "", null, "-", "", DensityUtil.dip2px(context, 20), 0));
        list.add(addObject("名字", user.getUsername(), null, "", "", 0, 50));
        list.add(addObject("", "", null, "-", "", DensityUtil.dip2px(context, 20), 50));
        list.add(addObject("手机号", user.getPhone(), null, "", "", 0, 50));
        list.add(addObject("二维码", "", BitmapFactory.decodeResource(getResources(), R.mipmap.qrcode), "", "", 0, 50));
        list.add(addObject("", "", null, "-", "", DensityUtil.dip2px(context, 20), 50));
        list.add(addObject("生日", user.getBirthday(), null, "", "", 0, 50));
        list.add(addObject("身高", "" + user.getUserheight(), null, "", "", 0, 50));
        list.add(addObject("职业", user.getOccupation(), null, "", "", 0, 50));
        list.add(addObject("学历", user.getEducation(), null, "", "", 0, 50));
        list.add(addObject("现居住", user.getLive(), null, "", "", 0, 50));
        list.add(addObject("个人签名", user.getAutograph(), null, "", "", 0, 50));
        list.add(addObject("", "", null, "-", "", DensityUtil.dip2px(context, 20), 0));
        list.add(addObject("修改密码", "", BitmapFactory.decodeResource(getResources(), R.mipmap.right), "", "", 0, 50));


    }

    private ListView_Object addObject(String content, String right_title, Bitmap resid_right, String Prompt, String Prompt_Content, int height, int Item_height) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setRight_title(right_title);
        object.setResid_right(resid_right);
        object.setHeight(height);
        object.setPromptContent(Prompt_Content);
        object.setPrompt(Prompt);
        object.setItem_height(DensityUtil.dip2px(context, Item_height));

        return object;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        STATE = position;
        switch (position) {
            case 0:
                //设置头像
//                SystemData.SystemImage(Personal_Information.this, IMAGE_CODE);
                CommenData.bitmaps = null;
                CommenData.position = 0;
                CommenData.FLAG = 0;
                Common.IntentActivity(context, SystemImage.class);
                break;
            case 2:
                //修改名字
                setData("修改名字", Identification.Name);
                Common.IntentActivity(activity, Other.class, data, Identification.Name);
                break;
            case 7:
                //修改生日
                dialog = new MyDialog(context, R.style.mydialog);
                dialog.setHeight(BaseActivity.height / 3);
                dialog.setWidth(BaseActivity.width / 3 * 2);
                dialog.DialogState();
                dialog.setOnClick(this);
                dialog.show();
                break;
            case 8:
                //设置身高
                dialog = new MyDialog(context, R.style.mydialog);
                dialog.setHeight(BaseActivity.height / 4);
                dialog.setWidth(BaseActivity.width / 3 * 2);
                dialog.DialogState(null, 230, 130, user.getUserheight());
                dialog.setOnClick(this);
                dialog.show();
                break;
            case 9:
                //设置职业
                setData("职业", Identification.Occupation);
                Common.IntentActivity(activity, Other.class, data, Identification.Occupation);
                break;
            case 10:
                //设置学历
                dialog = new MyDialog(context, R.style.mydialog);
                dialog.setHeight(BaseActivity.height / 4);
                dialog.setWidth(BaseActivity.width / 3 * 2);
                dialog.DialogState(height, height.length - 1, 0, 4);
                dialog.setOnClick(this);
                dialog.show();
                break;
            case 11:
                //设置现居住地址
                setData("现居住", Identification.Live);
                Common.IntentActivity(activity, Other.class, data, Identification.Live);
                break;
            case 12:
                //设置个性签名
                setData("个人签名", Identification.Autograph);
                Common.IntentActivity(activity, Other.class, data, Identification.Autograph);
                break;
            case 14:
                setData("修改密码", Identification.Password);
                Common.IntentActivity(activity, Other.class, data, Identification.Password);
                break;
        }
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                //回传数据
                CommenData.bitmaps = null;
                CommenData.position = 0;
                CommenData.FLAG = 0;
                Common.SetActivity(activity, user, user.getIdentification());
                finish();
                break;
        }
    }

    @Override
    public void OnRightClick() {
        switch (STATE) {
            case 7:
                user.setBirthday(dialog.getData());
                ModfigUpload("birthday", user.getBirthday(), 0);
                break;
            case 8:
                user.setHeight(dialog.getNumberPicker());
                ModfigUpload("height", null, user.getUserheight());
                break;
            case 10:
                user.setEducation(height[dialog.getNumberPicker()]);
                ModfigUpload("education", user.getEducation(), 0);
                break;
        }
        //重新加载listview
        additem();
        //更新Adapter
        adapter.ChangeData(list);
        dialog.dismiss();
    }

    @Override
    public void OnLeftClick() {
        dialog.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case IMAGE_CODE:
//                if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
//                    return;
//                }
//                bitmap = SystemData.handlerbitmap(context, data, 60, 60);
//                if (bitmap == null) {
//                    Toast.makeText(context, "图片更换失败", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                bitmap = SystemData.createRoundConerImage(Transformation.zoomImg(context, bitmap, 100, 100));
//                user.setLogo(bitmap);
//                break;
            case 1142:
                if (data != null) {
                    if (data.getStringExtra("data") == null || data.getStringExtra("data").length() == 0) {
                        SystemInfo.showToast(context, "取消修改");
                    } else {
                        user.setUsername(data.getStringExtra("data"));
                        ModfigUpload("nickname", user.getUsername(), 0);
                    }
                }
                //处理修改名字.
                break;
            case 1147:
                if (data != null) {
                    if (data.getStringExtra("data") == null || data.getStringExtra("data").length() == 0) {
                        SystemInfo.showToast(context, "取消修改");
                    } else {
                        user.setOccupation(data.getStringExtra("data"));
                        ModfigUpload("occupation", user.getOccupation(), 0);
                    }
                }
                //处理职业
                break;
            case 11510:
                if (data != null) {
                    if (data.getStringExtra("data") == null || data.getStringExtra("data").length() == 0) {
                        SystemInfo.showToast(context, "取消修改");
                    } else {
                        user.setLive(data.getStringExtra("data"));
                        ModfigUpload("address", user.getLive(), 0);
                    }
                }
                //处理现居住
                break;
            case 11511:
                if (data != null) {
                    if (data.getStringExtra("data") == null || data.getStringExtra("data").length() == 0) {
                        SystemInfo.showToast(context, "取消修改");
                    } else {
                        user.setAutograph(data.getStringExtra("data"));
                        ModfigUpload("autograph", user.getAutograph(), 0);
                    }
                }
                //处理个人签名
                break;
            case 11512:
                if (data != null) {
                    if (data.getStringExtra("data") == null || data.getStringExtra("data").length() == 0) {
                        SystemInfo.showToast(context, "取消修改");
                    } else {
                        SystemInfo.showToast(context, "密码修改成功");
                    }
                }
                //处理密码
                break;

        }
        //重新加载listview
        additem();
        //更新Adapter
        adapter.ChangeData(list);
    }

    private Parcelable setData(String title, int Identification) {
        data = new AcitivityData();
        data.setIdentification(Identification);
        data.setTitle(title);
        data.setContent(list.get(STATE).getRight_title());

        return data;
    }

    /**
     * 修改服务器和本地数据库的数据
     *
     * @param key
     * @param values
     * @param number
     */
    private void ModfigUpload(String key, String values, int number) {
        //修改服务器数据
        if (values != null) {
            UserData.ModifyInformation(AnalysisJSON.getModifyInformation(key, values));
        } else {
            UserData.ModifyInformation(AnalysisJSON.getModifyInformation(key, number + ""));
        }
        PATH.user = user;
        //修改本地数据
        SQLite_Table.insertTable(context, PATH.ihuo, PATH.userinfo, key, values, number);
    }

    @Override
    protected void onRestart() {
        if (CommenData.bitmaps != null) {
            bitmap = Transformation.Path2Bitmap(CommenData.bitmaps);
            if (bitmap == null) {
                Toast.makeText(context, "图片更换失败", Toast.LENGTH_SHORT).show();
                return;
            }
            bitmap = SystemData.createRoundConerImage(Transformation.zoomImg(context, bitmap, 100, 100));
            user.setLogo(bitmap);
            //重新加载listview
            additem();
            //更新Adapter
            adapter.ChangeData(list);
            //上传头像
            //本来添加一张图片由于链表减一所以添加多一张图片
            ArrayList<String> iconlist = new ArrayList<>();
            iconlist.add(CommenData.bitmaps);
            iconlist.add(null);
            new HTTP(this, FileImageUpload.saveFile(iconlist), PATH.uploadicon, "userId=" + PATH.user.getUserid() , "icon");

            //保存头像到本地
            FileWrite.saveBitmapFile(bitmap, user.getUserid(), PATH.Logo);

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
