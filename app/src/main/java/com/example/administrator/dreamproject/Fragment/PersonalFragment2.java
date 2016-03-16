package com.example.administrator.dreamproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.dreamproject.R;

/**
 * Created by Administrator on 2016/1/13.
 */
public class PersonalFragment2 extends Fragment {

    private View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.personalfragment2 , null);
        return view;
    }
}
