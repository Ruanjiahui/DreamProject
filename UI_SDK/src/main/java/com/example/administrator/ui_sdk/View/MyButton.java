package com.example.administrator.ui_sdk.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.administrator.ui_sdk.R;


/**
 * 自定义Button
 * Created by Administrator on 2016/1/7.
 */
public class MyButton extends Button {

    //判断按钮样式是否被修改过
    private boolean flag = false;

    public MyButton(Context context) {
        super(context);
        inti();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        inti();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti();
    }

    /**
     * 初始化配置
     */
    private void inti() {
//        this.setBackgroundResource(R.drawable.button_selector);
        this.setTextColor(0xffffffff);
    }

    /**
     * 判断按钮是否可以点击
     * * @param clickable
     */
    public void setMyClickable(boolean clickable) {
        if (!flag) {
            if (!clickable) {
                this.setBackgroundResource(R.drawable.button_down);
                this.setTextColor(0xff1cb3ee);
                this.setClickable(false);
                this.setEnabled(false);

            } else {
                this.setBackgroundResource(R.drawable.button_selector);
                this.setTextColor(0xffffffff);
                this.setClickable(true);
                this.setEnabled(true);
            }
        }
    }

    public void setSeclector(int resource) {
        this.setBackgroundResource(resource);
        flag = true;
    }
}
