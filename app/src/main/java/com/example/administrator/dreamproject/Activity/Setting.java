package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.ConfigurationOperation;
import com.example.administrator.data_sdk.ServerData;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.Nativte;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.websocket.WampConnection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Setting extends BaseActivity implements AdapterView.OnItemClickListener {

    private View view = null;
    private Context context = null;
    private ListView setting_listview = null;
    private ArrayList<ListView_Object> objects = null;
    private MyBaseAdapter adapter = null;
    private Button setting_unload = null;
    private Activity activity = null;
    private int disturb = 0;
    private int center = 0;
//    private DatabaseHelper databaseHelper = null;
    private Cursor cursor = null;
    private ServerData serverData = null;

//    @Override
//    public void setcontentView() {
//
//    }
//
//
//    @Override
//    public void init() {
//        super.init();
//
////        databaseHelper = new DatabaseHelper(context, PATH.ihuo);
//        loadState();
////        cursor = SQLite_Table.TableVisiable(databaseHelper, PATH.serverinfo);
////        if (cursor == null) {
////            setting_unload.setText("登录");
////        } else {
////            serverData = SQLite_Table.dbServer(cursor);
////            //如果表存在但是online状态为未登录也视为未登录状态
////            if (serverData.getOnline() == 0)
////                setting_unload.setText("登录");
////            else
////                setting_unload.setText("退出登录");
////
////        }
//
//        //设置标题
//        setTitle("设置");
//        //设置右边的文字不可见
//        TitleBarRight(false);
//        //隐藏导航条
//        Nav(0);
//        setBackground(R.color.WhiteSmoke);
//
//        addItem();
//        adapter = new MyBaseAdapter(context, objects, 2);
//        setting_listview.setAdapter(adapter);
//        setting_listview.setOnItemClickListener(this);
//        setting_unload.setOnClickListener(this);
//
//    }

    @Override
    public void inti() {
        setTopColor(R.color.blue);
        setTopTitleColor(R.color.white);
        setTitle("设置");
        setLeftTitleColor(R.color.white);
        setRightTitleVisiable(false);

        context = this;
        activity = (Activity) context;

        view = LayoutInflater.from(context).inflate(R.layout.settingmain, null);
        contentView.addView(view);

        setting_listview = (ListView) view.findViewById(R.id.setting_listview);
        setting_unload = (Button) view.findViewById(R.id.setting_unload);

        addItem();
        adapter = new MyBaseAdapter(context, objects, 2);
        setting_listview.setAdapter(adapter);
        setting_listview.setOnItemClickListener(this);
        setting_unload.setOnClickListener(this);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                //退出该页面
                Applications.getInstance().removeOneActivity(activity);
                break;
            case R.id.setting_unload:
                //把所有页面销毁剩下第一个页面
                //如果没有登录的点击则跳转到登录界面
                //如果登录了，点击则退出登录
                if (cursor == null) {
                    Common.IntentActivity(context, Load.class);
                } else {
                    if (serverData.getOnline() == 0) {
                        Common.IntentActivity(context, Load.class);
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("online", 0);
                        PATH.mConnection = new WampConnection();
                        SQLite_Table.updateData(context, PATH.ihuo, PATH.serverinfo, contentValues, null, null);
                        PATH.user = Nativte.NativeUser();
                        Applications.getInstance().removeActivity();
                    }
                }
                break;
        }
    }

    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();

        center = ConfigurationOperation.LoadState("center");
        disturb = ConfigurationOperation.LoadState("disturb");

        objects.add(addObject("勿扰模式", "", "", 0, disturb, BaseActivity.height / 15));
        objects.add(addObject("回车键发送消息", "", "", 0, center, BaseActivity.height / 15));
//        objects.add(addObject("设置", "", "", 0, 1, BaseActivity.height / 15));
        objects.add(addObject("测试", "-", "", 50, 0, BaseActivity.height / 15));
        objects.add(addObject("清除缓存", "", "", 0, -1, BaseActivity.height / 15));
        objects.add(addObject("反馈建议", "", "", 0, -1, BaseActivity.height / 15));
        objects.add(addObject("关于我们", "", "", 0, -1, BaseActivity.height / 15));
        objects.add(addObject("检查更新", "", "", 0, -1, BaseActivity.height / 15));
        objects.add(addObject("使用帮助", "", "", 0, -1, BaseActivity.height / 15));
//        objects.add(addObject("测试", "-", "", 50, 0, BaseActivity.height / 15));
//        objects.add(addObject("退出登录", "", "", 0, 0, BaseActivity.height / 18));
    }

    private ListView_Object addObject(String content, String Prompt, String Prompt_Content, int Height, int Setting, int item_height) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setPromptContent(Prompt_Content);
        object.setPrompt(Prompt);
        object.setHeight(Height);
        object.setSetting(Setting);
        object.setItem_height(item_height);

        return object;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                if (ConfigurationOperation.LoadState("disturb") == 0) {
                    ConfigurationOperation.writeConfiguration("disturb", "" + 1);
                    disturb = 1;
                } else {
                    ConfigurationOperation.writeConfiguration("disturb", "" + 0);
                    disturb = 0;
                }
                objects.set(0, addObject("勿扰模式", "", "", 0, disturb, BaseActivity.height / 15));
                adapter.notifyDataSetChanged();
                break;
            case 1:
                if (ConfigurationOperation.LoadState("center") == 0) {
                    ConfigurationOperation.writeConfiguration("center", "" + 1);
                    center = 1;
                } else {
                    ConfigurationOperation.writeConfiguration("center", "" + 0);
                    center = 0;
                }
                objects.set(1, addObject("回车键发送消息", "", "", 0, center, BaseActivity.height / 15));
                adapter.notifyDataSetChanged();
                break;
            case 4:
                Common.IntentActivity(context, Suggest.class, addParcelable(objects.get(position).getContent(), Identification.suggest));
                break;
            case 5:
                Common.IntentActivity(context, AboutMe.class, addParcelable(objects.get(position).getContent(), Identification.aboutme));
                break;
            //检查更新
            case 6:
                showDialog().show();
                break;
        }
    }

    /**
     * 创建对话框
     *
     * @return
     */
    private Dialog showDialog() {
        MyDialog dialog = new MyDialog(context, R.style.mydialog1);
        dialog.setWidth(BaseActivity.width / 3 * 2);
        dialog.setHeight(BaseActivity.height / 8);
        dialog.DialogState("正在检查更新版本", true);
        return dialog;
    }

    /**
     * 传递参数标识
     *
     * @param title
     * @param Identification
     * @return
     */
    private Parcelable addParcelable(String title, int Identification) {
        AcitivityData object = new AcitivityData();
        object.setIdentification(Identification);
        object.setTitle(title);

        return object;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //当再次重启这页面时再次判断是否是登录状态
        loadState();
    }

    private void loadState() {
        cursor = SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.serverinfo);
        if (cursor == null) {
            setting_unload.setText("登录");
        } else {
            serverData = SQLite_Table.dbServer(cursor);
            //如果表存在但是online状态为未登录也视为未登录状态
            if (serverData.getOnline() == 0)
                setting_unload.setText("登录");
            else
                setting_unload.setText("退出登录");

        }
    }
}
