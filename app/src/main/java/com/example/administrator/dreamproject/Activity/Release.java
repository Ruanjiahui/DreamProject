package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileImageUpload;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SystemImage;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface.HttpHandler;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/15.
 */
public class Release extends BaseActivity implements HttpHandler, AdapterView.OnItemClickListener {

    private View view = null;
    private Context context = null;
    private Activity activity = null;
    private AcitivityData acitivityData = null;
    private GridView release_gridview = null;

    private EditText release_edit = null;

    private ArrayList<ListView_Object> arrayList = null;
    private MyBaseAdapter adapter = null;
//    private ArrayList<String> list = null;
    private int DELETE = 1; //删除
    private int tmp = 0;    //记录点击图片的标识

//    @Override
//    public void setcontentView() {
//
//    }
//
//    @Override
//    public void init() {
//        super.init();
//
//        Nav(0);
//        setTitle("发表");
//        setTitleRight("提交");
//        setBackground(R.color.WhiteSmoke);
//
//    }

    @Override
    public void inti() {
        context = this;
        activity = (Activity) context;

        acitivityData = getIntent().getParcelableExtra("data");

        CommenData.arrayList = new ArrayList<>();

        //初始化标识
        CommenData.FLAG = 0;
        CommenData.position = 0;
        //初始化链表
        //如果不初始化链表会报错
        CommenData.arrayList.add(CommenData.position, null);

        view = LayoutInflater.from(context).inflate(R.layout.release, null);

        release_edit = (EditText) view.findViewById(R.id.release_edit);

        contentView.addView(view);

        //发表图文的
        if (acitivityData.getIdentification() == Identification.release_pir) {
            release_gridview = (GridView) view.findViewById(R.id.release_gridview);
            release_gridview.setVisibility(View.VISIBLE);

            arrayList = new ArrayList<>();

            arrayList.add(addItem(Transformation.Resource2Bitmap(activity, R.mipmap.release_add)));

            adapter = new MyBaseAdapter(context, arrayList, 3);
            release_gridview.setAdapter(adapter);

            release_gridview.setOnItemClickListener(this);
        }
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
            case R.id.base_top_text1:
                //如果是发表图文则使用AsyncTask
                if (acitivityData.getIdentification() == Identification.release_pir) {
                    new HTTP(this, FileImageUpload.saveFile(CommenData.arrayList), PATH.picture, "json=" + AnalysisJSON.ReleaseMsg(PATH.user.getUserid(), release_edit.getText().toString(), "0") , "pic");
                    //启动子线程开始请求
                }
                //如果仅仅是发表文字则使用Ruuable
                else {
                    new HTTP(this, PATH.Word, "json=" + AnalysisJSON.ReleaseMsg(PATH.user.getUserid(), release_edit.getText().toString(), "0"));
                }
                break;
        }
    }

    private ListView_Object addItem(Bitmap bitmap) {
        ListView_Object listView_object = new ListView_Object();
        listView_object.setResid_center(bitmap);
        listView_object.setResid_center_height(BaseActivity.width / 6);

        return listView_object;
    }

    @Override
    public void handler(String result) {
        finish();
        release_edit.setText("");
        release_edit.setHint(R.string.release_edit);
    }

    @Override
    public void handler(Bitmap bitmap) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == arrayList.size() - 1) {
            CommenData.bitmaps = null;
            //初始化标识
            CommenData.FLAG = 0;
            Common.IntentActivity(context, SystemImage.class);
        } else {
            AcitivityData acitivityData = new AcitivityData();
            acitivityData.setIdentification(Identification.look_picture);
            //因为是获取相反的标识所以用最后一个标识减去点击标识
            CommenData.bitmaps = null;
            //初始化标识
            CommenData.FLAG = 0;
            tmp = position;
            acitivityData.setID(tmp);

            Common.IntentActivity(context, Show.class, acitivityData);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (CommenData.bitmaps != null) {
            arrayList.add(arrayList.size() - CommenData.position, addItem(ThumbnailUtils.extractThumbnail(Transformation.Path2Bitmap(CommenData.bitmaps), BaseActivity.width / 6, BaseActivity.width / 6)));
            //将用户选择图片加载到list链表中
            CommenData.arrayList.add(0, CommenData.bitmaps);
            //如果图片数量达到8张则停止添加
            //原理是图片还是九张只是将最会一张隐藏
            if (CommenData.position == 8) {
                ArrayList<ListView_Object> objectArrayList = new ArrayList<>();
                for (int i = 0; i < arrayList.size() - 1; i++) {
                    objectArrayList.add(arrayList.get(i));
                }
                adapter.ChangeData(objectArrayList);
            } else {
                adapter.ChangeData(arrayList);
            }
        } else if (CommenData.FLAG == DELETE) {
            //删除则游标自动减一
            CommenData.position--;
            CommenData.arrayList.remove(tmp);
            arrayList.remove(tmp);
            adapter.ChangeData(arrayList);
        }
    }
}
