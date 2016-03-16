package com.example.administrator.dreamproject.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.dreamproject.Activity.Show;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/13.
 */
public class PersonalFragment3 extends Fragment implements AdapterView.OnItemClickListener, HttpInterface.HttpHandler {

    private View view = null;
    private GridView personalfragment3_gridview = null;
    private ArrayList<Drawable> list = null;
    private ArrayList<ListView_Object> objects = null;
    private Context context = null;
    private Activity activity = null;

    private String Bitmap = "";

    private String[] images = null;

    private HttpInterface.HttpHandler httpHandler = null;
    private MyBaseAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.personalfragment3, null);

        context = getActivity();
        activity = (Activity) context;

        httpHandler = this;

        CommenData.arrayList = new ArrayList<>();

        addItem();
        personalfragment3_gridview = (GridView) view.findViewById(R.id.personalfragment3_gridview);
        adapter = new MyBaseAdapter(context, objects, 3);

        personalfragment3_gridview.setAdapter(adapter);

        personalfragment3_gridview.setOnItemClickListener(this);

        CommenData.arrayList.add(PATH.userpicture + "16454829677245.jpg");

        new HTTP(httpHandler, PATH.userpicture, "userId=" + PATH.user.getUserid());

        return view;
    }

    private void addItem() {
        objects = new ArrayList<>();


//        for (int i = 0; i < 5; i++) {
//            CommenData.arrayList.add(PATH.article_Logo + "16454829677245.jpg");
//            objects.add(addItem(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher)));
//        }
    }

    private ListView_Object addItem(Bitmap bitmap) {
        ListView_Object listView_object = new ListView_Object();
        listView_object.setResid_center(bitmap);
        listView_object.setResid_center_height(BaseActivity.width / 3);

        return listView_object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AcitivityData acitivityData = new AcitivityData();
        acitivityData.setIdentification(Identification.person_picture);
        acitivityData.setID(position);
        Common.IntentActivity(context, Show.class, acitivityData);
    }


    private int tmp = 0;

    @Override
    public void handler(String result) {
        //获取数组的图片名称
        images = result.split("\\|");

        File[] file = FileWrite.getFilelist(PATH.userallpri);
        for (int i = 0; i < images.length; i++) {
            Bitmap bitmap = FileWrite.readBitmapFile(PATH.userallpri, images[i]);
            if (bitmap == null) {
                downpic(images[i]);
            } else {
                objects.add(addItem(bitmap));
                adapter.ChangeData(objects);
            }
        }
    }

    @Override
    public void handler(Bitmap bitmap) {
        objects.add(addItem(bitmap));
        FileWrite.saveBitmapFile(bitmap, images[tmp], PATH.userallpri);
        adapter.ChangeData(objects);
        tmp++;
    }

    /**
     * 加上线程锁防止乱开线程
     *
     * @param filename
     */
    private synchronized void downpic(String filename) {
        new HTTP(httpHandler, PATH.articleicon, filename, "1");
    }
}
