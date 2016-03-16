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
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.NavActivity;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.websocket.Wamp;

public class MainActivity extends NavActivity implements MyOnClickInterface.ItemClick, HttpInterface.HttpHandler {

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

    @Override
    public void setNavClick(View v) {

    }

    @Override
    public void setNav1Click() {
        super.setNav1Click();
        intentFragment(fragment1);
    }

    @Override
    public void setNav2Click() {
        super.setNav2Click();
        intentFragment(fragment2);
    }

    @Override
    public void setNav3Click() {
        super.setNav3Click();
        intentFragment(fragment3);
    }

    @Override
    public void setNav4Click() {
        super.setNav4Click();
        intentFragment(fragment4);
    }

    @Override
    public void Nav() {
        //去掉标题栏
        setNavTitle(0);

        setNav1("首页");
        setNav2("产品");
        setNav3("聊天");
        setNav4("商场");

        //给导航上面添加内容
        setNavContent(LayoutInflater.from(this).inflate(
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
//                setNavOrigin3(Integer.parseInt(cursor.getString(0)));
            }
        } else {
//            setNavOrigin3(0);
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
//                setNavOrigin3(Integer.parseInt(cursor.getString(0)));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Applications.getInstance().onTerminate();
    }
}

