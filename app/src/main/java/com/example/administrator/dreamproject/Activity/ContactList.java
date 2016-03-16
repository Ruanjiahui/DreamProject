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
public class ContactList extends BaseActivity {

    private View view = null;
    private AcitivityData object = null;

    private Context context = null;

    @Override
    public void setcontentView() {
        context = this;

        object = getIntent().getParcelableExtra("data");

        view = LayoutInflater.from(context).inflate(R.layout.contactlist_main, null);
    }

    @Override
    public void init() {

        setTitle(object.getTitle());
        Nav(0);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
        }
    }
}
