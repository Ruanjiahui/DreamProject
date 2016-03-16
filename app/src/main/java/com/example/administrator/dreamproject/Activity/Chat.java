package com.example.administrator.dreamproject.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.ConfigurationOperation;
import com.example.administrator.data_sdk.Judge;
import com.example.administrator.data_sdk.Msg;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.Fragment.Chat_Botton_Fragment;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.MainActivity;
import com.example.administrator.dreamproject.Nativte;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.ChatBaseAdapter;
import com.example.administrator.ui_sdk.View.MyPopWindow;
import com.example.administrator.ui_sdk.View.MyViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/14.
 */
public class Chat extends BaseActivity implements View.OnLongClickListener, MyOnClickInterface.Chat_interface, TextWatcher, View.OnKeyListener, TextView.OnEditorActionListener , MyOnClickInterface{

    private Context context = null;
    private View view = null;
    private AcitivityData object = null;

    private MyViewPager chat_bottom_viewpager = null;
    private ArrayList<Fragment> list = null;
    private LinearLayout chat_bottom_linear = null;
    private ImageView chat_bottom_image = null;
    private EditText chat_bottom_edit = null;
    private View chat_bottom = null;
    private RelativeLayout chat_relat = null;
    private ListView chat_listview = null;
    private Button chat_bottom_but = null;

    private static ArrayList<ListView_Object> objects = null;
    private static ChatBaseAdapter chat_adapter = null;

    private Activity activity = null;

    //用户ID
    private String userid = "ID:" + 123456;
    //用户名字
    private String username = "阮家辉";
    //用户性别
    private int sex = 1;
    //用户头像
    private static Bitmap logo = null;
    //用户等级
    private String grade = "黄金钻石级";
    //用户个人签名
    private String autograph = "个性签名";
    //标识
    private int identification = Identification.Me;
    //用户电话
    private String phone = "15119481373";
    //用户二维码
    private String qrcode = "一个链接";
    //用户生日
    private String birthday = "1994-7-4";
    //用户身高
    private int userheight = 175;
    //用户职业
    private String Occupation = "学生";
    //用户学历
    private String Education = "专科";
    //用户家乡
    private String Hometown = "广东阳江";
    //用户现居住地
    private String live = "广东广州";

    private static User user = null;
    private User other_user = null;
    //    private DatabaseHelper databaseHelper = null;
    private Cursor cursor = null;

    private MyPopWindow popWindow = null;

//    @Override
//    public void setcontentView() {
//
//
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @Override
//    public void init() {
//
//        instance();
//
//        addItem();
//
//        setBackground(R.color.WhiteSmoke);
//        setTitle(object.getTitle());
//        TitleBarRight(false);
//        RightIcon(R.mipmap.more);
//
//        Nav(0);
//
//        DensityUtil.setHeight(chat_bottom_viewpager, BaseActivity.height / 5);
//        chat_bottom_image.setOnClickListener(this);
//        chat_relat.setOnClickListener(this);
//
//        chat_adapter = new ChatBaseAdapter(context, objects);
//        chat_listview.setAdapter(chat_adapter);
//        chat_adapter.setLongClick(this);
//        chat_adapter.setClick(this);
//        chat_bottom_but.setOnClickListener(this);
//        chat_adapter.setIconClick(this);
//        chat_bottom_edit.addTextChangedListener(this);
//        chat_bottom_edit.setOnClickListener(this);
//        chat_bottom_edit.setOnKeyListener(this);
//        base_top_image1.setOnClickListener(this);
//
//    }

    @Override
    public void inti() {
        object = getIntent().getParcelableExtra("data");
        user = getIntent().getParcelableExtra("user");

        PATH.acString = "Chat";

        context = this;
        activity = (Activity) context;

        logo = Transformation.Resource2Bitmap(activity, R.mipmap.ic_launcher);

        activity = (Activity) context;
        view = LayoutInflater.from(context).inflate(R.layout.chatmain, null);
        chat_bottom_viewpager = (MyViewPager) view.findViewById(R.id.chat_bottom_viewpager);
        chat_bottom_linear = (LinearLayout) view.findViewById(R.id.chat_bottom_linear);
        chat_bottom_image = (ImageView) view.findViewById(R.id.chat_bottom_image);
        chat_bottom = view.findViewById(R.id.chat_bottom);
        chat_bottom_edit = (EditText) view.findViewById(R.id.chat_bottom_edit);
        chat_relat = (RelativeLayout) view.findViewById(R.id.chat_relat);
        chat_listview = (ListView) view.findViewById(R.id.chat_listview);
        chat_bottom_but = (Button) view.findViewById(R.id.chat_bottom_but);

        identification = Identification.Click_Logo;
//        other_user = addUser(userid, username, sex, logo, grade, autograph, identification, phone
//                , qrcode, birthday, userheight, Occupation, Education, Hometown, live);
        other_user = Nativte.NativeUser();


        contentView.addView(view);
    }

    @Override
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.base_top_relative:
                Common.SetActivity(activity, null, Identification.ChatMessage);
                PATH.acString = "";
                finish();
                break;
            case R.id.chat_bottom_image:
                //判断viewpager是否显示
                if (chat_bottom_viewpager.getVisibility() == View.GONE) {
                    //判断软键盘是否显示
                    if (SystemInfo.KeyBoard(activity)) {
                        SystemInfo.Closekeyboard(activity);
                    }
                    chat_bottom_viewpager.setVisibility(View.VISIBLE);
                } else {
                    chat_bottom_viewpager.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_relat:
                SystemInfo.Closekeyboard(activity);
                break;
            case R.id.chat_bottom_edit:
                if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
                    chat_bottom_viewpager.setVisibility(View.GONE);
                }
                break;
            case R.id.chat_bottom_but:
                send();
                break;
            case R.id.base_top_image1:
                ArrayList<ListView_Object> list = new ArrayList<>();
                list.add(addPop("修改群名称"));
                list.add(addPop("更换群主"));
                list.add(addPop("更换群头像"));
                popWindow = new MyPopWindow(activity, list, BaseActivity.width / 3);
//                popWindow.showPopupWindow(base_top_image1);
                popWindow.PopItemClick(this);
                break;
        }
    }

    private ListView_Object addPop(String content) {
        ListView_Object listView_object = new ListView_Object();
        listView_object.setContent(content);
        listView_object.setItem_height(DensityUtil.dip2px(context, 50));

        return listView_object;
    }


    private void instance() {
        list = new ArrayList<>();

        list.add(new Chat_Botton_Fragment());
        list.add(new Chat_Botton_Fragment());

//        chat_bottom_viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), list, false));
    }


    /**
     * 添加Gridview 的item数据
     */
    private void addItem() {
        objects = new ArrayList<>();
        ArrayList<Msg> msgs = new ArrayList<>();

        ContentValues contentValues = new ContentValues();
        contentValues.put("read", "1");
        //将未读的消息改成以读消息
        SQLite_Table.updateData(context, PATH.ihuo, PATH.msg, contentValues, "channelid=?", new String[]{object.getChannelid()});

        //获取本地数据库的消息进行显示
        msgs = AnalysisJSON.getChatMsg(SQLite_Table.queryData(context, PATH.ihuo, PATH.msg, new String[]{"*"}, "channelid=?", new String[]{object.getChannelid()}, "", "", "", ""));

        for (int i = 0; i < msgs.size(); i++) {
            if (msgs.get(i).getMemsg()) {
                objects.add(addObject(user.getLogo(), msgs.get(i).getContent(), "", msgs.get(i).getMemsg()));
            } else {
                objects.add(addObject(logo, msgs.get(i).getContent(), "", msgs.get(i).getMemsg()));
            }
        }
    }

    private static ListView_Object addObject(Bitmap resid, String content, String time, boolean memsg) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setResid(resid);
        object.setTime(time);
        object.setMemsg(memsg);

        return object;
    }


    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(context, "你好", Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public void Click(int position) {
        if (chat_bottom_viewpager.getVisibility() == View.VISIBLE) {
            chat_bottom_viewpager.setVisibility(View.GONE);
        }
        SystemInfo.Closekeyboard(activity);
    }

//    private User addUser(String userid, String username, int sex, Bitmap logo, String grade, String autograph, int Identification, String phone
//            , String qrcode, String birthday, int height, String Occupation, String Education, String Hometown, String live) {
//        return Common.addUser(userid, username, sex, logo, grade, autograph, Identification, phone
//                , qrcode, birthday, height, Occupation, Education, Hometown, live);
//    }

    @Override
    public void IconClick(int position) {
        //跳转到个人信息
        cursor = SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.serverinfo);
        //判断有没有这个表
        //没有这个表则视为没有登录
        if (cursor == null) {
            SystemInfo.showToast(context, "登录后再查看");
            return;
        } else {
            //如果拥有这个表online为0则视为未登录
            if (SQLite_Table.dbServer(cursor).getOnline() == 0) {
                SystemInfo.showToast(context, "登录后再查看");
                return;
            } else {
                //online为1则视为已登录
                //判断memsg是否为自己发送的信息
                if (objects.get(position).getMemsg()) {
                    identification = Identification.Me;
                    Common.IntentActivity(activity, Personal.class, user, Identification.Chat);
                } else {
                    Common.IntentActivity(activity, Personal.class, other_user, Identification.Firend);
                }
            }
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
        if (chat_bottom_edit.getText().toString().length() > 0) {
            chat_bottom_but.setClickable(true);
            chat_bottom_but.setEnabled(true);
            chat_bottom_but.setBackgroundResource(R.drawable.button_selector);
        } else {
            chat_bottom_but.setClickable(false);
            chat_bottom_but.setEnabled(false);
            chat_bottom_but.setBackgroundResource(R.drawable.button_down);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 312:
                if (data != null) {
                    if (data.getParcelableExtra("data") != null) {
                        user = data.getParcelableExtra("data");
                        for (int i = 0; i < objects.size(); i++) {
                            if (objects.get(i).getMemsg()) {
                                objects.set(i, addObject(user.getLogo(), objects.get(i).getContent(), objects.get(i).getTime(), objects.get(i).getMemsg()));
                            }
                        }
                        chat_adapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    /**
     * 回车按钮发送
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            if (ConfigurationOperation.LoadState("center") == 1) {
                if (Judge.Space(chat_bottom_edit.getText().toString())) {
                    chat_bottom_edit.setOnEditorActionListener(this);
                } else {
                    send();
                }
            } else {
                chat_bottom_edit.setText(chat_bottom_edit.getText().toString() + "\n");
            }
        }
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return false;
    }

    /**
     * 禁止回车按键换行
     *
     * @param v
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    private void send() {
        if (PATH.mConnection != null && PATH.mConnection.isConnected()) {
            MainActivity.sendid = user.getUsername();
            String json = "" + AnalysisJSON.PutChatMsg(object.getChannelid(), user.getUsername(), SystemData.EncodeStr(chat_bottom_edit.getText().toString()), user.getUserid());
            PATH.mConnection.publish(object.getChannelid(), "\"" + json + "\"");
            objects.add(addObject(user.getLogo(), chat_bottom_edit.getText().toString(), "", true));
            chat_bottom_edit.setText("");
            chat_adapter.notifyDataSetChanged();
        }
    }

    public static void publish(Msg msg) {
        if (msg.getMemsg()) {
        } else {
            objects.add(addObject(logo, msg.getContent(), "", msg.getMemsg()));
        }
        chat_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        MainActivity.sendid = "";
        super.onStop();
    }

    @Override
    public void ItemOnClick(int position) {

    }
}
