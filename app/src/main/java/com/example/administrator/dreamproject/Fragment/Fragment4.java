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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.Common;
import com.example.administrator.dreamproject.Activity.Setting;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.View.SystemManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/11.
 */
public class Fragment4 extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private View view = null;

    private ArrayList<ListView_Object> list = null;

    private Context context = null;
    private TextView base_top_title = null;
    private RelativeLayout base_top_relative = null;
    private View fragment3_top = null;
    private ImageView base_top_image1 = null;
    private TextView base_top_text1 = null;

    private Activity activity = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, null);

        context = getActivity();
        activity = (Activity) context;
        id();
        return view;
    }

    /**
     * 添加listview item子数据
     */
    private void addItem() {
        list = new ArrayList<>();

    }

    private ListView_Object addObject(Bitmap resid, String content, String subtitle, String Prompt, int height) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setSubtitle(subtitle);
        object.setResid(resid);
        object.setPrompt(Prompt);
        object.setHeight(height);

        return object;
    }

    /**
     * 获取组件ID
     */
    private void id() {
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        fragment3_top = view.findViewById(R.id.fragment3_top);
        base_top_image1 = (ImageView) view.findViewById(R.id.base_top_image1);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);


        base_top_title.setText("商城");
        base_top_text1.setVisibility(View.GONE);
        base_top_relative.setVisibility(View.GONE);
        fragment3_top.setBackgroundResource(R.color.Blue);
        base_top_image1.setImageResource(R.mipmap.setting);
        base_top_image1.setVisibility(View.VISIBLE);
        SystemManager.ViewColor(fragment3_top, activity);
        DensityUtil.setHeight(fragment3_top, BaseActivity.height / 12);

        base_top_image1.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_image1:
                Common.IntentActivity(activity, Setting.class, null, Identification.market);
                break;
        }
    }
}
