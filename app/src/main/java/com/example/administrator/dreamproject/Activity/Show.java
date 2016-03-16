package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.dreamproject.CommenData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.R;
import com.example.administrator.ui_sdk.Other.BaseActivity;

/**
 * Created by Administrator on 2016/2/17.
 */
public class Show extends BaseActivity {

    private View view = null;
    private Activity activity = null;
    private Context context = null;
    private ImageView show_image = null;
    private EditText show_edit = null;
    private AcitivityData acitivityData = null;
    private LinearLayout show_linear1 = null;
    private TextView show_group_private = null;
    private TextView show_group_public = null;

    @Override
    public void setcontentView() {
        super.setcontentView();

        context = this;
        activity = (Activity) context;

        acitivityData = getIntent().getParcelableExtra("data");

        view = LayoutInflater.from(context).inflate(R.layout.show, null);
        show_image = (ImageView) view.findViewById(R.id.show_image);
        show_edit = (EditText) view.findViewById(R.id.show_edit);
        show_linear1 = (LinearLayout) view.findViewById(R.id.show_linear1);
        show_group_private = (TextView) view.findViewById(R.id.show_group_private);
        show_group_public = (TextView) view.findViewById(R.id.show_group_public);

        contentView.addView(view);

    }

    @Override
    public void init() {
        super.init();

        setTitleRight("删除");
        setTitle("");

        if (acitivityData.getIdentification() == Identification.look_picture) {
            show_image.setVisibility(View.VISIBLE);
            if (acitivityData.getID() != 0) {
                show_image.setImageBitmap(Transformation.Path2Bitmap(CommenData.arrayList.get(acitivityData.getID())));
            }
        } else if (acitivityData.getIdentification() == Identification.person_picture) {
            show_image.setVisibility(View.VISIBLE);
            show_image.setImageBitmap(Transformation.Path2Bitmap(CommenData.arrayList.get(acitivityData.getID())));
        } else if (acitivityData.getIdentification() == Identification.Follow) {
            show_edit.setVisibility(View.VISIBLE);
            show_edit.setHint(R.string.people_hint);
            //隐藏右边的文字
            TitleBarRight(false);
            setTitle("搜索");
        } else if (acitivityData.getIdentification() == Identification.group) {
            show_linear1.setVisibility(View.VISIBLE);
            TitleBarRight(false);
            show_group_private.setOnClickListener(this);
            show_group_public.setOnClickListener(this);
            setTitle("创建群");
        }
        setBackground(R.color.WhiteSmoke);
        Nav(0);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                finish();
                break;
            //操作删除图片的操作
            case R.id.base_top_text1:
                //将标识修改成删除标识用于Release删除图片
                CommenData.FLAG = 1;
                finish();
                break;
            case R.id.show_group_private:
                break;
            case R.id.show_group_public:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
