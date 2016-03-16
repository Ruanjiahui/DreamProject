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
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.SystemManager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/11.
 */
public class Fragment2 extends Fragment {

    private View view = null;

    private ArrayList<ListView_Object> list = null;

    private ListView fragment2_listview = null;

    private Context context = null;
    private View fragment2_top = null;
    private Activity activity = null;
    private MyBaseAdapter adapter = null;

    private ImageView main_top_limage = null;
    private ImageView main_top_rimage = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, null);

        context = getActivity();
        activity = (Activity) context;
        id();
        addItem();

        main_top_limage.setImageBitmap(Transformation.Resource2Bitmap(activity, R.mipmap.menu));
        main_top_rimage.setImageBitmap(Transformation.Resource2Bitmap(activity, R.mipmap.search));


        adapter = new MyBaseAdapter(context, list, 1);
        DensityUtil.setHeight(fragment2_top, BaseActivity.height / 12);
        fragment2_top.setBackgroundResource(R.color.Blue);
        fragment2_listview.setAdapter(adapter);

        //设置沉寂式状态栏
        SystemManager.ViewColor(fragment2_top, getActivity());
        return view;
    }

    /**
     * 添加listview 的item的数据
     */
    private void addItem() {
        list = new ArrayList<>();

        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .cpu), 50, 50)), "I7-CPU", "￥2399", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .cpu1), 50, 50)), "I5-CPU", "￥1399", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .cpu2), 50, 50)), "I5-CPU", "￥1299", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .broad), 50, 50)), "技嘉", "￥799", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .broad1), 50, 50)), "华硕", "￥999", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .member), 50, 50)), "威刚", "￥399", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
        list.add(addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap
                .menber1), 50, 50)), "金士顿", "￥599", Transformation.Resource2Bitmap(activity, R.mipmap.shopping_up)));
    }

    private ListView_Object addObject(Bitmap resid, String content, String subtitle, Bitmap right_resid) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setResid(resid);
        object.setSubtitle(subtitle);
        object.setResid_right1(right_resid);

        return object;
    }

    private void id() {
        fragment2_listview = (ListView) view.findViewById(R.id.fragment2_listview);
        fragment2_top = view.findViewById(R.id.fragment2_top);
        main_top_limage = (ImageView) view.findViewById(R.id.main_top_limage);
        main_top_rimage = (ImageView) view.findViewById(R.id.main_top_rimage);
    }
}
