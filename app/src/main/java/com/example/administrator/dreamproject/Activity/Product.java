package com.example.administrator.dreamproject.Activity;

import android.view.View;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.Other.BaseActivity;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Product extends BaseActivity {

    private AcitivityData object = null;

    @Override
    public void setcontentView() {
        super.setcontentView();
    }

    @Override
    public void init() {
        //隐藏导航条
        Nav(0);
        object = getIntent().getParcelableExtra("data");
        setTitle(object.getTitle());
    }


    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                //退出该页面
                finish();
                break;
        }
    }
}
