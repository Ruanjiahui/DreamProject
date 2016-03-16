package com.example.administrator.dreamproject.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.dreamproject.Activity.WebViewActivity;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;

/**
 * Created by Administrator on 2016/1/11.
 */
@SuppressLint("ValidFragment")
public class FragmentNews extends Fragment implements View.OnClickListener {

    private View view = null;
    private int resid;
    private String title = null;

    private RelativeLayout fragment4_linear = null;

    private ImageView fragment4_image = null;
    private TextView fragment4_title = null;

    private Context context = null;

    @SuppressLint("ValidFragment")
    public FragmentNews(int resid, String title) {
        this.resid = resid;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentnews, null);

        context = getActivity();
        id();
        fragment4_image.setBackgroundResource(resid);
        fragment4_title.setText(title);
        fragment4_linear.setOnClickListener(this);

        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }

    private void id() {
        fragment4_image = (ImageView) view.findViewById(R.id.fragment4_image);
        fragment4_title = (TextView) view.findViewById(R.id.fragment4_title);
        fragment4_linear = (RelativeLayout) view.findViewById(R.id.fragment4_linear);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment4_linear:
                //暂时写死
                if (Fragment1.position == 0) {
                    Common.IntentActivity(context, WebViewActivity.class, addParcelable("http://mp.weixin.qq.com/s?__biz=MzIyNzA0NDkwOQ==&mid=401318416&idx=1&sn=0d3f474bd11da86ef962c6d409dcb6a3&scene=18#wechat_redirect", title, Identification.News));
                }
                if (Fragment1.position == 1) {
                    Common.IntentActivity(context, WebViewActivity.class, addParcelable("http://mp.weixin.qq.com/s?__biz=MzIyNzA0NDkwOQ==&mid=401318276&idx=1&sn=5e93734e4d7208677b90a8eedb144ea5&scene=18#wechat_redirect", title, Identification.News));
                }
                if (Fragment1.position == 2) {
                    Common.IntentActivity(context, WebViewActivity.class, addParcelable("http://mp.weixin.qq.com/s?__biz=MzIyNzA0NDkwOQ==&mid=401319582&idx=1&sn=81f959cae86db13ee28cb5d5c3ec9b99&scene=18#wechat_redirect", title, Identification.News));
                }
                break;
        }
    }

    public Parcelable addParcelable(String url, String title , int Identification) {
        AcitivityData object = new AcitivityData();
        object.setUrl(url);
        object.setTitle(title);
        object.setIdentification(Identification);

        return object;
    }
}
