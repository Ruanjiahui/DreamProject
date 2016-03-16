package com.example.administrator.dreamproject.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Common;
import com.example.administrator.data_sdk.Msg;
import com.example.administrator.data_sdk.SystemData;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.data_sdk.Transformation;
import com.example.administrator.data_sdk.User;
import com.example.administrator.dreamproject.Activity.Chat;
import com.example.administrator.dreamproject.Activity.People;
import com.example.administrator.dreamproject.Activity.Show;
import com.example.administrator.dreamproject.AnalysisJSON;
import com.example.administrator.dreamproject.Identification;
import com.example.administrator.dreamproject.MainActivity;
import com.example.administrator.dreamproject.PATH;
import com.example.administrator.dreamproject.R;
import com.example.administrator.dreamproject.SQLite_Table;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.MyOnClickInterface;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.ui_sdk.View.MyDialog;
import com.example.administrator.ui_sdk.View.MyPopWindow;
import com.example.administrator.ui_sdk.View.SystemManager;

import java.util.ArrayList;

import static com.example.administrator.dreamproject.AnalysisJSON.getChatFlagMsg;

/**
 * Created by Administrator on 2016/1/11.
 */
public class Fragment3 extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener, MyOnClickInterface {

    private View view = null;
    private static ListView fragment3_listview = null;

    private static ArrayList<ListView_Object> arrayList = null;

    private static Context context = null;
    private View fragment3_top = null;
    private ImageView base_top_image1 = null;
    private TextView base_top_text1 = null;

    private RelativeLayout base_top_relative = null;
    private static Activity activity = null;

    private User user = null;
    private static MyBaseAdapter adapter = null;
    private static int position = 0;
    private MyDialog dialog = null;
    private String read = "";
    private static ArrayList<Msg> msgs = null;
    private TextView base_top_title = null;

    private MyPopWindow popWindow = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3, null);

        MainActivity.publsh = false;
        Bundle bundle = getArguments();
        user = bundle.getParcelable("data");


        PATH.acString = "Fragment3";

        context = getActivity();
        activity = (Activity) context;

        id();
//        addItem();

        DensityUtil.setHeight(fragment3_top, BaseActivity.height / 12);


        //设置沉寂式状态栏
        SystemManager.setWindowColor(fragment3_top, getActivity());


        fragment3_listview.setOnItemLongClickListener(this);
        fragment3_listview.setOnItemClickListener(this);
        base_top_image1.setOnClickListener(this);
        return view;
    }

    /**
     * 添加listview item子数据
     */
    public static void addItem() {
        arrayList = new ArrayList<>();
        msgs = new ArrayList<>();

        if (SQLite_Table.TableVisiable(context, PATH.ihuo, PATH.msg) == null) {
            SQLite_Table.createMsgTable(context, PATH.ihuo);
        } else {
            if (SQLite_Table.TableDataVisiable(context, PATH.ihuo, PATH.msg) != 0 && PATH.mConnection.isConnected()) {
                msgs = getChatFlagMsg(SQLite_Table.queryData(context, PATH.ihuo, PATH.msg, new String[]{"distinct channelid , nickname , content , read"}, "memsg=?", new String[]{"false"}, "channelid", "count(channelid) > 0", "time desc", ""));
            }else{
                msgs = getChatFlagMsg(SQLite_Table.queryData(context, PATH.ihuo, PATH.msg, new String[]{"distinct channelid , nickname , content , read"}, "memsg=? and channelid = ?", new String[]{"false" , "sgc:offical"}, "channelid", "count(channelid) > 0", "time desc", ""));
            }
        }
        for (int i = 0; i < msgs.size(); i++) {
            arrayList.add(
                    addObject(SystemData.createRoundConerImage(Transformation.zoomImg(context, Transformation.Resource2Bitmap(activity, R.mipmap.logo),
                            50, 50)), msgs.get(i).getName(), msgs.get(i).getContent(), SystemData.getTimeHour() + ":" + SystemData.getTimeMinuts()));
        }

        adapter = new MyBaseAdapter(context, arrayList, 1);
        fragment3_listview.setAdapter(adapter);
    }

    private static ListView_Object addObject(Bitmap resid, String content, String subtitle, String right_title) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setSubtitle(subtitle);
        object.setResid(resid);
        object.setRight_title(right_title);

        return object;
    }

    /**
     * 获取组件ID
     */
    private void id() {
        fragment3_listview = (ListView) view.findViewById(R.id.fragment3_listview);

        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        fragment3_top = view.findViewById(R.id.fragment3_top);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        fragment3_top.setBackgroundResource(R.color.Blue);
        base_top_relative.setVisibility(View.GONE);
        base_top_image1 = (ImageView) view.findViewById(R.id.base_top_image1);


        base_top_text1.setVisibility(View.GONE);
        base_top_image1.setVisibility(View.VISIBLE);
        base_top_image1.setImageBitmap(Transformation.Resource2Bitmap(activity, R.mipmap.more));
        base_top_title.setText("消息");
        base_top_title.setTextColor(getResources().getColorStateList(R.color.white));


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Common.IntentActivity(activity, Chat.class, addParcelable(arrayList.get(position).getContent(), Identification.ChatMessage, msgs.get(position).getChannelid()), Identification.ChatMessage, user);
//        Cursor cursor = SQLite_Table.queryData(context, PATH.ihuo, PATH.msg, new String[]{"count(*)"}, "channelid = ? and read = ?", new String[]{msgs.get(position).getChannelid(), "0"}, "", "", "", "");
//        if (cursor.moveToNext()) {
//            PATH.TOTAL -= Integer.parseInt(cursor.getString(0));
//        }
//        BaseActivity.setNavOrigin3(PATH.TOTAL);
    }


    private ListView_Object addPop(String content) {
        ListView_Object listView_object = new ListView_Object();
        listView_object.setContent(content);
        listView_object.setItem_height(DensityUtil.dip2px(context, 50));

        return listView_object;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_image1:
                ArrayList<ListView_Object> list = new ArrayList<>();
                list.add(addPop("添加好友"));
                list.add(addPop("创建群"));
                list.add(addPop("好友列表"));
                popWindow = new MyPopWindow(activity, list, BaseActivity.width / 3);
                popWindow.showPopupWindow(base_top_image1);
                popWindow.PopItemClick(this);
                break;
        }
    }

    /**
     * 传递参数的标识
     *
     * @param title
     * @return
     */
    private Parcelable addParcelable(String title, int Identification, String channelid) {
        AcitivityData object = new AcitivityData();
        object.setIdentification(Identification);
        object.setTitle(title);
        object.setChannelid(channelid);

        return object;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        dialog = new MyDialog(context, R.style.mydialog);
        dialog.DialogState("", dialogItem(), 0, "", "");
//        dialog.setWidth(BaseActivity.width / 3 * 2);
        dialog.setHeight(DensityUtil.dip2px(context, 70));
        Fragment3.position = position;
        dialog.setOnItemClick(new MyOnClickInterface.ItemClick() {
            @Override
            public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayList.remove(Fragment3.position);
                adapter.notifyDataSetChanged();
                SystemInfo.showToast(context, "删除成功");
                dialog.dismiss();
            }
        });
        dialog.show();
        return true;
    }

    private ArrayList<ListView_Object> dialogItem() {
        ArrayList<ListView_Object> objects = new ArrayList<>();
        objects.add(add("删除该消息"));

        return objects;
    }

    private ListView_Object add(String content) {
        ListView_Object list = new ListView_Object();
        list.setContent(content);
        list.setItem_height(DensityUtil.dip2px(context, 50));
        return list;
    }

    @Override
    public void onStop() {
        MainActivity.publsh = true;
        super.onStop();
    }

    /**
     * 重新启动刷新界面
     */
    @Override
    public void onStart() {
        MainActivity.publsh = false;
        addItem();
        adapter.ChangeData(arrayList);
        super.onStart();
    }

    public static Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            AnalysisJSON.getMsg(msg.obj.toString());
            addItem();
            adapter.ChangeData(arrayList);
        }
    };

    @Override
    public void ItemOnClick(int position) {
        AcitivityData acitivityData = new AcitivityData();
        switch (position) {
            case 0:
                acitivityData.setIdentification(Identification.Follow);
                Common.IntentActivity(context, Show.class, acitivityData);
                break;
            case 1:
                acitivityData.setIdentification(Identification.group);
                Common.IntentActivity(context, Show.class, acitivityData);
                break;
            case 2:
                //跳转到我关注的人
                Common.IntentActivity(context, People.class, addParcelable("我关注的人", Identification.ChatMessage));
                break;
        }
        popWindow.dismiss();
    }

    /**
     * 传递参数的标识
     *
     * @param title
     * @return
     */
    private Parcelable addParcelable(String title, int Identification) {
        AcitivityData data = new AcitivityData();
        data.setIdentification(Identification);
        data.setTitle(title);

        return data;
    }
}
