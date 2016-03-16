package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.administrator.data_sdk.Database.DatabaseHelper;
import com.example.administrator.data_sdk.Judge;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.Nativte;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.websocket.Wamp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/1/20.
 */
public class Register extends BaseActivity implements TextWatcher {

    private View view = null;
    private Context context = null;
    public static Activity activity = null;

    private EditText register_edit = null;
    private EditText register_edit1 = null;
    private Button register_but = null;
    private LinearLayout register_linear = null;
    private int time = 60;
    private Timer timer = null;
    private int STATE = -1;
    private int PHONE = 0;
    private String newpassword = "";
    private String oldpassword = "";
    //    private Wamp mConnection = null;
    private ContentValues contentValues = null;
    private String phone = "";

    @Override
    public void inti() {
        setTitle("注册");
        setTopColor(R.color.blue);
        setRightTitleColor(R.color.white);
        setLeftTitleColor(R.color.white);
        setTopTitleColor(R.color.White);
        setRightTitle("下一步");

        context = this;
        activity = (Activity) context;

        view = LayoutInflater.from(context).inflate(R.layout.register, null);
        register_edit = (EditText) view.findViewById(R.id.register_edit);
        register_but = (Button) view.findViewById(R.id.register_but);
        register_edit1 = (EditText) view.findViewById(R.id.register_edit1);
        register_linear = (LinearLayout) view.findViewById(R.id.register_linear);

        setContent(view);

        register_edit.addTextChangedListener(this);
        register_but.setOnClickListener(this);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.register_but:
                if (Judge.Space(register_edit.getText().toString()) && PHONE == 0){
                    SystemInfo.showToast(context, "手机号码不能为空");
                    break;
                }
                PHONE = 1;
                phone = register_edit.getText().toString();
                register_edit.setText("");
                register_edit.setHint("请输入验证码");
                register_but.setEnabled(false);
                register_but.setClickable(false);
                register_but.setBackgroundResource(R.drawable.button_down_blue);
                timer = new Timer();
                timer.schedule(new MyTimerTask(), 0, 1000);
                STATE = 0;
                break;
            case R.id.base_top_text1:
                //判断手机号码是否为空
                if (Judge.Space(register_edit.getText().toString()) && PHONE == 0) {
                    SystemInfo.showToast(context, "手机号码不能为空");
                    return;
                    //判断输入的格式是否为11位
                } else if (register_edit.getText().toString().length() < 11 && PHONE == 0) {
                    SystemInfo.showToast(context, "手机号码格式不对");
                    return;
                    //判断验证码师是否为空
                } else if (Judge.Space(register_edit.getText().toString()) && PHONE == 1) {
                    SystemInfo.showToast(context, "验证码不能为空");
                    return;
                }
                //判断有没有点击获取验证码
                if (STATE == -1) {
                    SystemInfo.showToast(context, "请获取手机验证码");
                }
                newpassword = register_edit1.getText().toString();
                switch (STATE) {
                    //如果已经点击获取验证码则显示输入密码的编辑框
                    case 0:
                        register_edit1.setVisibility(View.VISIBLE);
                        register_linear.setVisibility(View.GONE);
                        STATE = 1;
                        break;
                    //判断输入的密码是否为空
                    case 1:
                        if (Judge.Space(newpassword)) {
                            SystemInfo.showToast(context, "密码不能为空");
                            return;
                        }
                        STATE = 2;
                        register_edit1.setText("");
                        register_edit1.setHint("请再一次输入密码");
                        oldpassword = newpassword;
                        break;
                    //判断前两次输入的密码是否一样
                    case 2:
                        if (Judge.Password(newpassword, oldpassword)) {

                            contentValues = new ContentValues();
                            contentValues.put("phone", phone);
                            contentValues.put("name", phone);
                            contentValues.put("password", newpassword);
                            contentValues.put("online", 1);
                            //重写配置文件
                            //将登陆情况设置为已登录
                            //写入配置文件
                            //链接服务器进行注册
                            final DatabaseHelper db = new DatabaseHelper(context, PATH.ihuo);
//                            mConnection = new WampConnection();
                            PATH.mConnection.connect(PATH.Load + phone + "@" + phone + "/" + newpassword, new Wamp.ConnectionHandler() {
                                @Override
                                public void onOpen() {
//                                    PATH.mConnection.subscribe("sgc:test", String.class, UserData.Pulish());
                                    SystemInfo.showToast(context, "注册成功");
                                    //判断表是否有数据
                                    //拥有数据则更新
                                    //没有数据则插入
                                    SQLite_Table.insert_Table1(context, PATH.ihuo, PATH.serverinfo, contentValues);

                                    contentValues = new ContentValues();
                                    contentValues.put("nickname", phone);
//                                    contentValues.put("phone", phone);
                                    contentValues.put("userid", phone);
                                    contentValues.put("password", newpassword);


//                                    User user = SQLite_Table.dbUser(SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userinfo));
                                    User user = new User();
                                    user.setUsername(phone);
                                    user.setUsername(phone);
                                    user.setLogo(Nativte.logo);
                                    user.setIdentification(Identification.Me);
                                    PATH.user = user;
                                    //判断userinfo这个表有没有存在
                                    //如果表不存在则创建表并且添加数据
                                    //如果表存在则判断表里面有没有数据没有则插入
                                    //如果表里面有数据则更新
                                    SQLite_Table.insertTable(context, PATH.ihuo, PATH.userinfo, contentValues);
//                                    PATH.mConnection = mConnection;
                                    Applications.getInstance().removeOneActivity(Load.activity);
                                    Applications.getInstance().removeOneActivity(Register.activity);
                                }

                                @Override
                                public void onClose(int code, String reason) {
                                    Applications.getInstance().removeOneActivity(Register.activity);
                                    SystemInfo.showToast(context, "注册失败");
                                }

                                @Override
                                public void onBinaryMessage(byte[] payload) {

                                }
                            });
                        } else {
                            STATE = 1;
                            register_edit1.setText("");
                            register_edit1.setHint("请输入密码");
                            SystemInfo.showToast(context, "两次密码不一样，请重新输入");
                        }
                        break;
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
        if (register_edit.getText().toString().length() == 11) {
            register_but.setEnabled(true);
            register_but.setClickable(true);
            register_but.setBackgroundResource(R.drawable.button_selector_blue);
        } else if (register_edit.getText().toString().length() < 11) {
            register_but.setEnabled(false);
            register_but.setClickable(false);
            register_but.setBackgroundResource(R.drawable.button_down_blue);
        }
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            time--;
            Message msg = new Message();
            msg.what = time;
            handler.sendMessage(msg);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            SystemInfo.Closekeyboard(activity);
        }
        return super.onTouchEvent(event);
    }


    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            register_but.setText(msg.what + "秒后重新获取验证码");
            if (msg.what == 0) {
                register_but.setText("获取验证码");
                register_but.setEnabled(true);
                register_but.setClickable(true);
                time = 60;
                register_but.setBackgroundResource(R.drawable.button_selector_blue);
                timer.cancel();
            }
        }
    };
}
