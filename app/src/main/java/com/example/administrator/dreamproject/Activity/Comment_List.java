package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.Fragment.Chat_Botton_Fragment;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.Other.MyViewPagerAdapter;
import com.example.administrator.ui_sdk.View.MyViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/19.
 */
public class Comment_List extends BaseActivity implements TextWatcher, OnItemClickListener {

    private Context context = null;
    private Activity activity = null;
    private View view = null;
    private ListView comment_list_listview = null;
    private ArrayList<ListView_Object> list = null;
    private ArrayList<Fragment> arrayList = null;
    private Bitmap logo = null;

    private AcitivityData acitivityData = null;
    private EditText chat_bottom_edit = null;
    private Button chat_bottom_but = null;
    private MyViewPager chat_bottom_viewpager = null;
    private ImageView chat_bottom_image = null;
    private MyBaseAdapter adapter = null;

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
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
                if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
                    chat_bottom_viewpager.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_bottom_but:
                list.add(setListView_Object(acitivityData.getTitle(), SystemInfo.getSystemTime(), chat_bottom_edit.getText().toString(), logo));
                adapter.ChangeData(list);
                chat_bottom_edit.setText("");
                break;
        }
    }

    @Override
    public void setcontentView() {
        acitivityData = getIntent().getParcelableExtra("data");
        context = this;
        activity = (Activity) context;

        logo = Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher);
        view = LayoutInflater.from(context).inflate(R.layout.comment_list, null);

        comment_list_listview = (ListView) view.findViewById(R.id.comment_list_listview);
        chat_bottom_edit = (EditText) view.findViewById(R.id.chat_bottom_edit);
        chat_bottom_but = (Button) view.findViewById(R.id.chat_bottom_but);
        chat_bottom_viewpager = (MyViewPager) view.findViewById(R.id.chat_bottom_viewpager);
        chat_bottom_image = (ImageView) view.findViewById(R.id.chat_bottom_image);


        contentView.addView(view);
    }

    @Override
    public void init() {
        TitleBarRight(false);
        setTitle(acitivityData.getTitle());
        Nav(0);
        setBackground(R.color.WhiteSmoke);
        addItem();
        instance();

        DensityUtil.setHeight(chat_bottom_viewpager, BaseActivity.height / 5);

        adapter = new MyBaseAdapter(context, list, 0);
        comment_list_listview.setAdapter(adapter);

        chat_bottom_image.setOnClickListener(this);
        chat_bottom_edit.addTextChangedListener(this);
        chat_bottom_edit.setOnClickListener(this);
        chat_bottom_but.setOnClickListener(this);
        comment_list_listview.setOnItemClickListener(this);
    }

    private void addItem() {
        list = new ArrayList<>();

        list.add(setListView_Object(acitivityData.getTitle(), acitivityData.getTime(), acitivityData.getContent(), logo));
    }

    private ListView_Object setListView_Object(String content, String time, String subtitle, Bitmap resid) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setRight_title(time);
        object.setResid(resid);
        object.setSubtitle(subtitle);

        return object;
    }

    private void instance() {
        arrayList = new ArrayList<>();

        arrayList.add(new Chat_Botton_Fragment());
        arrayList.add(new Chat_Botton_Fragment());

        chat_bottom_viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), arrayList, false));
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
            if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
                chat_bottom_viewpager.setVisibility(View.GONE);
            }
            SystemInfo.Closekeyboard(activity);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
            chat_bottom_viewpager.setVisibility(View.GONE);
        }
        SystemInfo.Closekeyboard(activity);
    }
}
