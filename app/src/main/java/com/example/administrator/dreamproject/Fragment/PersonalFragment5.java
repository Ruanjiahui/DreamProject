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

import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/20.
 */
public class PersonalFragment5 extends Fragment {

    private View view = null;
    private ArrayList<ListView_Object> objects = null;
    private MyListView personalfragment5_listview = null;
    private Context context = null;
    private Activity activity = null;

    private User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.personalfragment5, null);
        context = getActivity();
        activity = (Activity) context;

        user = PATH.user;

        personalfragment5_listview = (MyListView) view.findViewById(R.id.personalfragment5_listview);

        addItem();
        personalfragment5_listview.setAdapter(new MyBaseAdapter(context, objects, 1));

        return view;
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "成就", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), true, 0));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "", "", "-", "", DensityUtil.dip2px(context, 20), Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), true, 0));

        if (!user.getAutograph().equals("")) {
            objects.add(addObject(null, "个人签名", user.getAutograph(), "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        }
        if (!user.getBirthday().equals("")) {
            objects.add(addObject(null, "生日", user.getBirthday(), "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        }
        if (user.getUserheight() != 0) {
            objects.add(addObject(null, "身高", "" + user.getUserheight(), "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        }
        if (!user.getOccupation().equals("")) {
            objects.add(addObject(null, "职业", user.getOccupation(), "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        }
        if (!user.getEducation().equals("")) {
            objects.add(addObject(null, "学历", user.getEducation(), "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        }
        if (!user.getLive().equals("")) {
            objects.add(addObject(null, "现居住", user.getLive(), "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        }
//        objects.add(addObject(null, "个性签名", "测试", "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
//        objects.add(addObject(null, "身高", "175", "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
//        objects.add(addObject(null, "职业", "学生", "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
//        objects.add(addObject(null, "家乡", "阳江", "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
//        objects.add(addObject(null, "现住地", "广州", "", "", 0, null, false, DensityUtil.dip2px(context, 50)));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "", "", "-", "", DensityUtil.dip2px(context, 20), Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), true, 0));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "二维码", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), true, 0));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "分享", "", "", "", 0, Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), true, 0));
        objects.add(addObject(Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), "分享", "", "-", "", DensityUtil.dip2px(context, 50), Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher), true, 0));
    }

    private ListView_Object addObject(Bitmap resid, String content, String right_title, String Prompt, String Prompt_content, int Height, Bitmap right_resid, boolean Click, int item_height) {
        ListView_Object object = new ListView_Object();

        object.setResid(resid);
        object.setContent(content);
        object.setResid_right(right_resid);
        object.setPrompt(Prompt);
        object.setClick(Click);
        object.setPromptContent(Prompt_content);
        object.setHeight(Height);
        object.setRight_title(right_title);
        object.setItem_height(item_height);

        return object;
    }
}
