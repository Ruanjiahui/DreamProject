package com.example.administrator.dreamproject.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/14.
 */
public class Chat_Botton_Fragment extends Fragment {

    private View view = null;
    private GridView chat_bottom_gridview = null;
    private ArrayList<ListView_Object> objects = null;
    private Context context = null;
    private Activity activity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_bottom_fragment, null);

        context = getActivity();
        activity = (Activity) context;

        chat_bottom_gridview = (GridView) view.findViewById(R.id.chat_bottom_gridview);
        addItem();

        chat_bottom_gridview.setAdapter(new MyBaseAdapter(context, objects, 3));

        return view;
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "测试" + i));
        }
    }

    private ListView_Object addObject(Bitmap resid, String content) {
        ListView_Object object = new ListView_Object();

        object.setContent_center(content);
        object.setResid_center(resid);
        return object;
    }
}
