package com.example.administrator.dreamproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/2/16.
 */
public class SystemImage extends BaseActivity implements AdapterView.OnItemClickListener {

    private Context context = null;
    private Activity activity = null;
    private View view = null;
    private GridView systemimage_gridview = null;

    private ArrayList<ListView_Object> arrayList = null;

    private List<HashMap<String, String>> list = null;
    private MyBaseAdapter adapter = null;


//    @Override
//    public void init() {
//
//        arrayList.add(addItem(Transformation.Resource2Bitmap(activity, R.mipmap.camera)));
//        for (int i = 0; i < list.size(); i++) {
//            arrayList.add(addItem(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher)));
//        }
//
//        new Thread(new Run()).start();
//
//        setTitle("图片");
//        Nav(0);
//        setBackground(R.color.WhiteSmoke);
//
//        adapter = new MyBaseAdapter(context, arrayList, 3);
//        systemimage_gridview.setAdapter(adapter);
//
//        systemimage_gridview.setOnItemClickListener(this);
//
//    }

    @Override
    public void inti() {
        context = this;
        activity = (Activity) context;

        view = LayoutInflater.from(context).inflate(R.layout.systemimage, null);

        contentView.addView(view);

        systemimage_gridview = (GridView) view.findViewById(R.id.systemimage_gridview);

        arrayList = new ArrayList<>();
        list = SystemData.LocalImage(context);
//        Release.list = list;
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
        }
    }

    private ListView_Object addItem(Bitmap bitmap) {
        ListView_Object listView_object = new ListView_Object();
        listView_object.setResid_center(bitmap);
        listView_object.setResid_center_height(BaseActivity.width / 3);

        return listView_object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {

        } else {
//            Release.list1.add(Release.position , position);
            CommenData.position++;
            CommenData.bitmaps = list.get(position - 1).get("data");
//            Release.bitmaps = ThumbnailUtils.extractThumbnail(Transformation.Path2Bitmap(list.get(position - 1).get("data")), BaseActivity.width / 6, BaseActivity.width / 6);
            finish();
        }
    }

    private class Run implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < list.size(); i++) {
                Bitmap bitmap = ThumbnailUtils.extractThumbnail(Transformation.Path2Bitmap(list.get(i).get("data")), BaseActivity.width / 3, BaseActivity.width / 3);
                arrayList.set(i + 1, addItem(bitmap));
                Message msg = new Message();
                handler.sendMessage(msg);
            }
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            adapter.ChangeData(arrayList);
        }
    };

    public static String Min2Native(String url) {
        return url.substring(0, url.length() - 3) + "min." + url.substring(url.length() - 3, url.length());
    }
}
