package com.example.administrator.dreamproject;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.FileUtil.FileWrite;
import com.example.administrator.data_sdk.Msg;
import com.example.administrator.data_sdk.ServerData;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Activity.Chat;
import com.example.administrator.dreamproject.Activity.Release;
import com.example.administrator.dreamproject.Fragment.Fragment1;
import com.example.administrator.dreamproject.Fragment.Fragment2;
import com.example.administrator.dreamproject.Fragment.Fragment3;
import com.example.administrator.dreamproject.Fragment.Fragment4;
import com.example.administrator.http_sdk.HTTP;
import com.example.administrator.http_sdk.HttpInterface;
import com.example.administrator.ui_sdk.CreateDialog;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.BaseActivity;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.websocket.Wamp;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MyOnClickInterface.ItemClick, HttpInterface.HttpHandler {

    private RelativeLayout nav1, nav2, nav3, nav4 = null;

    //Fragment适配器
    private User user = null;
    private Fragment fragment1 = null;
    private Fragment fragment2 = null;
    private Fragment fragment3 = null;
    private Fragment fragment4 = null;

    public static Activity activity = null;

    public static Context context = null;
    private String url = null;
    private Cursor cursor = null;
    private ServerData serverData = null;
    public static boolean publsh = true;
    public static String sendid = "";
    private MyDialog dialog = null;

    public static HttpInterface.HttpHandler httpHandler = null;

    /**
     * 重写setcontentView方法可以实现添加布局文件
     */
    @Override
    public void setcontentView() {
        contentView.addView(LayoutInflater.from(this).inflate(
                R.layout.activity_main, null));

        context = MainActivity.this;
        activity = (Activity) context;

        httpHandler = this;

        cursor = SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.serverinfo);
        //判断serverinfo表是否存在
        if (cursor != null) {
            //判断serverinfo是否拥有数据
            //存在serverinfo获取数据保存到serverData
            serverData = SQLite_Table.dbServer(cursor);
            //判断userinfo是否存在
            cursor = SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userinfo);
            if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.msg) == null) {
                SQLite_Table.createMsgTable(context, PATH.ihuo);
            }
            if (cursor != null) {
                //判断有没有数据
                if (SQLite_Table.TableDataVisiable(context, PATH.ihuo, PATH.userinfo) != 0) {
                    //检查是否是登录状态
                    if (serverData.getOnline() == 1) {
                        //是登录状态
                        //拥有数据保存到user
                        user = SQLite_Table.dbUser(cursor);
                        user.setLogo(Nativte.logo);
                        user.setIdentification(Identification.Me);
                        url = PATH.Load + serverData.getName() + "/" + serverData.getPassword();
                        //自动链接服务器
                        PATH.mConnection.connect(url, new Wamp.ConnectionHandler() {
                            @Override
                            public void onOpen() {
                                UserData.Connect(httpHandler);
                            }

                            @Override
                            public void onClose(int code, String reason) {
                                user = Nativte.NativeUser();
                                SystemInfo.showToast(context, "服务器链接已断开");
                                SQLite_Table.insertTable(context, PATH.ihuo, PATH.serverinfo, "online", "", 0);
                            }

                            @Override
                            public void onBinaryMessage(byte[] payload) {
                            }
                        });
                    } else {
                        SQLite_Table.insertTable(context, PATH.ihuo, PATH.serverinfo, "online", "", 0);
                        user = Nativte.NativeUser();
                    }
                } else {
                    SQLite_Table.insertTable(context, PATH.ihuo, PATH.serverinfo, "online", "", 0);
                    user = Nativte.NativeUser();
                }
            } else {
                SQLite_Table.createUserTable(context, PATH.ihuo);
                SQLite_Table.insertTable(context, PATH.ihuo, PATH.serverinfo, "online", "", 0);
                //获取系统默认
                user = Nativte.NativeUser();
            }
        } else {
            SQLite_Table.createserverinfoTable(context, PATH.ihuo);
            user = Nativte.NativeUser();
        }

        Bitmap bitmap = FileWrite.readBitmapFile(PATH.Logo, user.getUserid());
        if (bitmap == null) {
            user.setLogo(Transformation.Resource2Bitmap(activity, R.mipmap.logo));
            if (serverData != null && serverData.getOnline() == 1) {
                new HTTP(this, PATH.downicon, user.getUserid() + "/" + user.getLogo_url(), "1");
            }
        } else {
            user.setLogo(bitmap);
        }

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        PATH.user = user;
        //默认跳转到第一个页面
        intentFragment(fragment1);

    }

    /**
     * 重写inti可以实现初始化
     */
    @Override
    public void init() {
        // 获取组件ID
        id();

        // 组件的点击事件
        nav1.setOnClickListener(this);
        nav2.setOnClickListener(this);
        nav3.setOnClickListener(this);
        nav4.setOnClickListener(this);


        // 设置标题是否显示0默认不显示
        Title(0);
        //设置背景颜色
        setBackground(R.color.WhiteSmoke);
        //设置发表按钮可见
        setVisiableAdd(true);

        setNav1("首页");
        setNav2("产品");
        setNav3("聊天");
        setNav4("商场");
    }

    /**
     * 获取组件ID
     */
    private void id() {
        nav1 = (RelativeLayout) findViewById(R.id.nav1);
        nav2 = (RelativeLayout) findViewById(R.id.nav2);
        nav3 = (RelativeLayout) findViewById(R.id.nav3);
        nav4 = (RelativeLayout) findViewById(R.id.nav4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav1:
                intentFragment(fragment1);
                setNav1Image(R.mipmap.house);
                setNav1TextColor(0xff28beff);
                setNav2Image(R.mipmap.shopping_up);
                setNav2TextColor(R.color.Grey);
                setNav3Image(R.mipmap.speech_up);
                setNav3TextColor(R.color.Grey);
                setNav4Image(R.mipmap.basket_up);
                setNav4TextColor(R.color.Grey);
                break;
            case R.id.nav2:
                intentFragment(fragment2);
                setNav2Image(R.mipmap.shopping);
                setNav2TextColor(0xff28beff);
                setNav1Image(R.mipmap.house_up);
                setNav1TextColor(R.color.Grey);
                setNav3Image(R.mipmap.speech_up);
                setNav3TextColor(R.color.Grey);
                setNav4Image(R.mipmap.basket_up);
                setNav4TextColor(R.color.Grey);
                break;
            case R.id.nav3:
                intentFragment(fragment3);
                setNav3Image(R.mipmap.speech);
                setNav3TextColor(0xff28beff);
                setNav2Image(R.mipmap.shopping_up);
                setNav2TextColor(R.color.Grey);
                setNav1Image(R.mipmap.house_up);
                setNav1TextColor(R.color.Grey);
                setNav4Image(R.mipmap.basket_up);
                setNav4TextColor(R.color.Grey);
                break;
            case R.id.nav4:
                intentFragment(fragment4);
                setNav4Image(R.mipmap.basket);
                setNav4TextColor(0xff28beff);
                setNav2Image(R.mipmap.shopping_up);
                setNav2TextColor(R.color.Grey);
                setNav3Image(R.mipmap.speech_up);
                setNav3TextColor(R.color.Grey);
                setNav1Image(R.mipmap.house_up);
                setNav1TextColor(R.color.Grey);
                break;
            case R.id.base_image_add:
                ArrayList<ListView_Object> list = new ArrayList<>();
                list.add(addItem("发表文字"));
                list.add(addItem("发表图片"));
                dialog = CreateDialog.ListViewDialog(context, list, CreateDialog.dialog_height, 2);
                dialog.setOnItemClick(this);
                dialog.show();
                break;

        }
    }

    private ListView_Object addItem(String content) {
        CreateDialog.dialog_height = DensityUtil.dip2px(context, 50);
        ListView_Object listView_object = new ListView_Object();
        listView_object.setContent(content);
        listView_object.setItem_height(CreateDialog.dialog_height);
        return listView_object;
    }

    /**
     * 跳转Fragment
     *
     * @param targetFragment
     */
    private void intentFragment(Fragment targetFragment) {
        //判断当前页面是不是已经显示
//        setNavOrigin3(PATH.TOTAL);
        if (!targetFragment.isVisible()) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", user);
            targetFragment.setArguments(bundle);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                //更新Fragment1头像
                if (data == null) {
                } else if (data != null) {
                    if (data.getParcelableExtra("data") != null) {
                        user = data.getParcelableExtra("data");
                        intentFragment(new Fragment1());
//                        update(serverData.getName(), 0);
                    }
                }
                break;
            case 31:
//                if (data == null) {
//                } else if (data != null && serverData.getName() != null) {
//                    if (data.getParcelableExtra("data") != null) {
//                        user = data.getParcelableExtra("data");
//                        intentFragment(new Fragment3());
//                        update(serverData.getName(), 2);
//                    }
//                }
                break;
            case 41:
                if (data == null) {
                    user = PATH.user;
                    intentFragment(new Fragment4());
                } else if (PATH.user != null && serverData.getName() != null) {
                    user = PATH.user;
                    intentFragment(new Fragment4());
                    update(serverData.getName(), 3);
                }
                break;
            case 91:
                if (data == null) {
                    user = PATH.user;
                    intentFragment(new Fragment1());
                } else if (PATH.user != null && serverData.getName() != null) {
                    user = PATH.user;
                    intentFragment(new Fragment1());
                    update(serverData.getName(), 0);
                }
                break;
        }
    }

    private long endtime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - endtime > 2000) {
                SystemInfo.showToast(activity, "再按一次退出软件");
                endtime = System.currentTimeMillis();
            } else {
                finish();
                //销毁全部的Activity
                Destory();
            }
        }
        return false;
    }

    private void update(String json, final int position) {
        if (PATH.mConnection != null && PATH.mConnection.isConnected()) {
            PATH.mConnection.call("getuserinfo", String.class, new Wamp.CallHandler() {

                @Override
                public void onResult(Object result) {
                    if (result.toString() == null || result.toString().length() == 0) {
                        SystemInfo.showToast(context, "服务器链接已断开");
                        SQLite_Table.insertTable(context, PATH.ihuo, PATH.serverinfo, "online", "", 0);
                    } else {
                        //获取数据需要Base64转码
                        String newresult = SystemData.Transcodingdecode(result.toString());
                        Message msg = new Message();
                        msg.obj = newresult;
                        msg.what = position;
                        handler.sendMessage(msg);
                    }
                }

                @Override
                public void onError(String errorUri, String errorDesc) {

                }
            }, json);
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            ContentValues contentValues = null;
            contentValues = AnalysisJSON.getUserinfo(msg.obj.toString());
            //将数据插进本地数据库
            SQLite_Table.insert_Table1(context, PATH.ihuo, PATH.userinfo, contentValues);

            User user1 = SQLite_Table.dbUser(SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.userinfo));
            user1.setLogo(Nativte.logo);
            user1.setIdentification(Identification.Me);
            PATH.user = user1;
            user = PATH.user;
            switch (msg.what) {
                case 0:
                    intentFragment(new Fragment1());
                    break;
                case 1:
                    intentFragment(new Fragment2());
                    break;
                case 2:
                    intentFragment(new Fragment3());
                    break;
                case 3:
                    intentFragment(new Fragment4());
                    break;
            }
        }
    };

    @Override
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SQLite_Table.StateLogin(context)) {
            switch (position) {
                case 0:
                    Common.IntentActivity(context, Release.class, setAcitivityData(Identification.release_word));
                    break;
                case 1:
                    Common.IntentActivity(context, Release.class, setAcitivityData(Identification.release_pir));
                    break;
            }
        } else {
            SystemInfo.showToast(context, getString(R.string.statelogin));
        }
        dialog.dismiss();
    }

    private AcitivityData setAcitivityData(int identification) {
        AcitivityData acitivityData = new AcitivityData();
        acitivityData.setIdentification(identification);
        return acitivityData;
    }

    /**
     * 更新小圆点
     */
    @Override
    protected void onStart() {
        if (SQLite_Table.TableDataVisiable(context, PATH.ihuo, PATH.msg) != 0) {
            Cursor cursor = SQLite_Table.queryData(context, PATH.ihuo, PATH.msg, new String[]{"count(*)"}, "read = ?", new String[]{"0"}, "", "", "", "");
            if (cursor.moveToNext()) {
                setNavOrigin3(Integer.parseInt(cursor.getString(0)));
            }
        } else {
            setNavOrigin3(0);
        }
        super.onStart();
    }

    /**
     * 处理聊天界面更新获取消息推送的处理
     *
     * @param result
     */
    @Override
    public void handler(String result) {
        Msg msg = AnalysisJSON.getMsg(result);
        if (SQLite_Table.TableDataVisiable(context, PATH.ihuo, PATH.msg) != 0) {
            Cursor cursor = SQLite_Table.queryData(context, PATH.ihuo, PATH.msg, new String[]{"count(*)"}, "read = ?", new String[]{"0"}, "", "", "", "");
            if (cursor.moveToNext()) {
                setNavOrigin3(Integer.parseInt(cursor.getString(0)));
            }

            if ("Fragment3".equals(PATH.acString)) {
                Fragment3.addItem();
            }

            if ("Chat".equals(PATH.acString)) {
                Chat.publish(msg);
            }
        }
    }

    /**
     * 处理下载的图片
     *
     * @param bitmap
     */
    @Override
    public void handler(Bitmap bitmap) {
        if (bitmap != null) {
            user.setLogo(bitmap);
            intentFragment(new Fragment1());
        }
    }
}

