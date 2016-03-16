package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.Fragment.Fragment1;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.MainActivity;
import com.example.administrator.dreamproject.Nativte;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.dreamproject.UserData;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.http_sdk.HttpInterface.HttpHandler;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.websocket.Wamp;

/**
 * Created by Administrator on 2016/1/20.
 */
public class Load extends BaseActivity implements View.OnFocusChangeListener, TextWatcher, HttpHandler {

    private View view = null;
    private static Context context = null;
    public static Activity activity = null;

    private EditText load_edit1, load_edit2 = null;
    private ImageView load_image, load_image1 = null;
    private Button load_but1 = null;
    private Button load_but = null;

    private static ContentValues contentValues = null;
    private static String user = null;
    private String password = null;

    private static HttpInterface.HttpHandler httpHandler = null;


    @Override
    public void inti() {
        setRightTitleVisiable(false);
        setTitle("登录");
        setTopTitleColor(R.color.white);
        setLeftTitleColor(R.color.white);
        setTopColor(R.color.blue);

        context = this;
        activity = (Activity) context;

        httpHandler = this;

        view = LayoutInflater.from(this).inflate(R.layout.load, null);

        load_edit1 = (EditText) view.findViewById(R.id.load_edit1);
        load_edit2 = (EditText) view.findViewById(R.id.load_edit2);
        load_image = (ImageView) view.findViewById(R.id.load_image);
        load_image1 = (ImageView) view.findViewById(R.id.load_image1);
        load_but = (Button) view.findViewById(R.id.load_but);
        load_but1 = (Button) view.findViewById(R.id.load_but1);

        load_edit1.setOnFocusChangeListener(this);
        load_edit2.setOnFocusChangeListener(this);
        load_edit1.addTextChangedListener(this);
        load_edit2.addTextChangedListener(this);
        load_but1.setOnClickListener(this);
        load_but.setOnClickListener(this);

//        load_but1.setClickable(true);
//        load_but1.setEnabled(true);

        setContent(view);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.load_but1:
                Common.IntentActivity(context, Register.class);//, "ws://10.0.3.2:8080/WebSocket/Main/@", Identification.Resgister));
                break;
            case R.id.load_but:
                user = load_edit1.getText().toString();
                password = load_edit2.getText().toString();

                contentValues = new ContentValues();
                contentValues.put("password", password);
                contentValues.put("name", user);
                contentValues.put("online", 1);
//                mConnection = new WampConnection();
                PATH.mConnection.connect(PATH.Load + user + "/" + password, new Wamp.ConnectionHandler() {
                    @Override
                    public void onOpen() {
                        //call ID将获取用户信息
                        UserData.getUser(user);
                        UserData.Connect(MainActivity.httpHandler);
                    }

                    @Override
                    public void onClose(int code, String reason) {
                        SystemInfo.showToast(context, "登录失败");
                    }

                    @Override
                    public void onBinaryMessage(byte[] payload) {

                    }
                });
                break;
        }
    }

    /**
     * 处理异步返回的数据
     */
    public static Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            //将数据插进本地数据库
            //判断服务表存不存在
            if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.serverinfo) == null) {
                SQLite_Table.createUserTable(context, PATH.ihuo);
            }
            SQLite_Table.insert_Table1(context, PATH.ihuo, PATH.serverinfo, contentValues);
            contentValues = AnalysisJSON.getUserinfo(msg.obj.toString());
            //将数据插进本地数据库

            //判断用户信息表存不存在
            if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userinfo) == null) {
                SQLite_Table.createUserTable(context, PATH.ihuo);
            }
            String newlogo = contentValues.getAsString("icon");
            String oldlogo = SQLite_Table.dbUser(SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userinfo)).getLogo_url();

            SQLite_Table.insert_Table1(context, PATH.ihuo, PATH.userinfo, contentValues);

            User user = SQLite_Table.dbUser(SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userinfo));

            if (FileWrite.readBitmapFile(PATH.Logo, user.getLogo_url()) == null) {
                //下载图片
                user.setLogo(Nativte.logo);
                user.setLogo_url(newlogo);
                new HTTP(httpHandler, PATH.downicon, user.getUserid() + "/" + newlogo, "1");


            } else {
                user.setLogo(Nativte.logo);
                if (newlogo.equals(oldlogo)) {//如果新的图片和就图片一样就直接加载本地
                    user.setLogo(FileWrite.readBitmapFile(PATH.Logo, oldlogo));
                } else {
                    //如果不一樣就下載服務器的
                    user.setLogo_url(newlogo);
                    new HTTP(httpHandler, PATH.downicon, user.getUserid() + "/" + newlogo, "1");
                }
            }
            user.setIdentification(Identification.Me);
            PATH.user = user;

            Applications.getInstance().removeOneActivity(Load.activity);
            SystemInfo.showToast(context, "登录成功");
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            SystemInfo.Closekeyboard(activity);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.load_edit1:
                if (hasFocus) {
                    load_image.setImageResource(R.mipmap.head);
                    load_image1.setImageResource(R.mipmap.password);
                } else {
                    load_image.setImageResource(R.mipmap.head_up);
                    load_image1.setImageResource(R.mipmap.password_up);
                }
                break;
            case R.id.load_edit2:
                if (hasFocus) {
                    load_image.setImageResource(R.mipmap.head_up);
                    load_image1.setImageResource(R.mipmap.password_up);
                } else {
                    load_image.setImageResource(R.mipmap.head);
                    load_image1.setImageResource(R.mipmap.password);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (load_edit1.getText().toString().length() > 0 && load_edit2.getText().toString().length() > 0) {
            load_but.setClickable(true);
            load_but.setEnabled(true);
            load_but.setBackgroundResource(R.drawable.button_selector_red);
        } else {
            load_but.setClickable(false);
            load_but.setEnabled(false);
            load_but.setBackgroundResource(R.drawable.button_down_red);
        }
    }

    /**
     * 点击系统退出按钮返回上一页
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Applications.getInstance().removeOneActivity(activity);
        }
        return false;
    }

    @Override
    public void handler(String result) {

    }

    @Override
    public void handler(Bitmap bitmap) {
        if (bitmap == null) {
        } else {
            load_edit2.setText("");
            PATH.user.setLogo(bitmap);

            //将图片写入SD卡
            FileWrite.saveBitmapFile(bitmap, PATH.user.getUserid(), PATH.Logo);
            //更新Main的界面
            Fragment1.setTileTxt(PATH.user);
        }
    }
}
