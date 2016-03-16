package com.example.administrator.dreamproject.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.Other.BaseActivity;

/**
 * Created by Administrator on 2016/1/14.
 */
public class Suggest extends BaseActivity {

    private Context context = null;
    private View view = null;
    private AcitivityData object = null;

    @Override
    public void setcontentView() {
        context = this;
        object = getIntent().getParcelableExtra("data");
        view = LayoutInflater.from(context).inflate(R.layout.suggest, null);

        contentView.addView(view);
    }

    @Override
    public void init() {
        setTitleRight("发送");
        setTitle(object.getTitle());
        setBackground(R.color.WhiteSmoke);
        Nav(0);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()){
            case  R.id.base_top_relative:
                finish();;
                break;
        }
    }
}
