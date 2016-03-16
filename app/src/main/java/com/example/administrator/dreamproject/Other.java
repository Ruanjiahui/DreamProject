package com.example.administrator.dreamproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.data_sdk.AcitivityData;
import com.example.administrator.data_sdk.Judge;
import com.example.administrator.data_sdk.SystemInfo;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ListView_Object;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;
import com.example.administrator.ui_sdk.Other.MyBaseAdapter;
import com.example.administrator.websocket.Wamp;
import com.example.administrator.websocket.WampConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/18.
 */
public class Other extends BaseActivity implements AdapterView.OnItemClickListener {

    private Context context = null;
    private Activity activity = null;
    private View view = null;

    public AcitivityData data = null;

    private EditText other_edit = null;
    private LinearLayout other_linear = null;
    private ListView other_listview1 = null;
    private ListView other_listview2 = null;

    private ArrayList<ListView_Object> list = null;
    private Map<String, ArrayList<ListView_Object>> map = null;
    private MyBaseAdapter adapter = null;

    private String province = "", city = "";
    private int POSITION = 0;

    private String password = "", newpassword = "", new1password = "";
    private int state = 0;
    private LinearLayout other_linear1 = null;
    private EditText other_edit1, other_edit2, other_edit3 = null;
    private Wamp mConnection = null;

//    @Override
//    public void setcontentView() {
//
//    }

//    @Override
//    public void init() {
//
//        setTitle(data.getTitle());
//        setTitleRight("保存");
//        setBackground(R.color.WhiteSmoke);
//        Nav(0);
//    }

    @Override
    public void inti() {
        context = this;

        data = getIntent().getParcelableExtra("data");

        view = LayoutInflater.from(context).inflate(R.layout.other, null);

        other_edit = (EditText) view.findViewById(R.id.other_edit);
        other_linear = (LinearLayout) view.findViewById(R.id.other_linear);
        other_listview1 = (ListView) view.findViewById(R.id.other_listview1);
        other_listview2 = (ListView) view.findViewById(R.id.other_listview2);
        other_linear1 = (LinearLayout) view.findViewById(R.id.other_linear1);
        other_edit1 = (EditText) view.findViewById(R.id.other_edit1);
        other_edit2 = (EditText) view.findViewById(R.id.other_edit2);
        other_edit3 = (EditText) view.findViewById(R.id.other_edit3);

        if (data.getIdentification() == Identification.Name || data.getIdentification() == Identification.Autograph) {
            other_edit.setVisibility(View.VISIBLE);
            other_edit.setText(data.getContent());
        } else if (data.getIdentification() == Identification.Password) {
            other_linear1.setVisibility(View.VISIBLE);
        } else {
            other_linear.setVisibility(View.VISIBLE);

            additem();

            other_listview1.setAdapter(new MyBaseAdapter(context, list, 0));
            adapter = new MyBaseAdapter(context, map.get("address" + 0), 0);
            other_listview2.setAdapter(adapter);

            other_listview1.setOnItemClickListener(this);
            other_listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    city = map.get("address" + POSITION).get(position).getContent();
                }
            });
        }
        contentView.addView(view);
    }

    @Override
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.base_top_relative:
                setResult(0, intent);
                finish();
                break;
            case R.id.base_top_text1:
                if (data.getIdentification() == Identification.Password) {
                    mConnection = new WampConnection();
                    password = other_edit1.getText().toString();
                    newpassword = other_edit2.getText().toString();
                    new1password = other_edit3.getText().toString();

                    if (Judge.Password(newpassword, new1password)) {
                        Check("password", newpassword);
                    } else {
                        SystemInfo.showToast(context, "两次密码不一样");
                        other_edit2.setText("");
                        other_edit3.setText("");
                    }
                } else {
                    //保存按钮
                    if (data.getIdentification() == Identification.Name || data.getIdentification() == Identification.Autograph) {
                        intent.putExtra("data", other_edit.getText().toString());
                    } else {
                        intent.putExtra("data", province + city);
                    }
                    setResult(data.getIdentification(), intent);
                    finish();
                }
                break;
        }
    }

    /**
     * 添加item子项
     */
    private void additem() {
        list = new ArrayList<>();
        map = new HashMap<>();

        list.add(addObject("北京市"));
        list.add(addObject("天津市"));
        list.add(addObject("上海市"));
        list.add(addObject("广东省"));
        list.add(addObject("福建省"));
        list.add(addObject("浙江省"));
        list.add(addObject("重庆市"));
        list.add(addObject("湖南省"));

        ArrayList<ListView_Object> arrayList = new ArrayList<>();
        arrayList.add(addObject("朝阳区"));
        arrayList.add(addObject("朝阳区"));
        arrayList.add(addObject("朝阳区"));
        arrayList.add(addObject("朝阳区"));
        arrayList.add(addObject("朝阳区"));
        arrayList.add(addObject("朝阳区"));
        map.put("address" + 0, arrayList);

        for (int i = 1; i < list.size(); i++) {
            arrayList = new ArrayList<>();
            arrayList.add(addObject("上海"));
            arrayList.add(addObject("上海"));
            arrayList.add(addObject("上海"));
            arrayList.add(addObject("上海"));
            arrayList.add(addObject("上海"));
            arrayList.add(addObject("上海"));
            map.put("address" + i, arrayList);
        }

    }

    private ListView_Object addObject(String content) {
        ListView_Object object = new ListView_Object();

        object.setContent(content);
        object.setItem_height(DensityUtil.dip2px(context, 50));

        return object;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        POSITION = position;
        province = list.get(position).getContent();
        adapter.ChangeData(map.get("address" + position));
    }

    /**
     * 修改密码
     *
     * @param key
     * @param values
     */
    private void Check(final String key, final String values) {
        mConnection.connect(PATH.Load + PATH.user.getUserid() + "/" + password, new Wamp.ConnectionHandler() {
            @Override
            public void onOpen() {
                PATH.mConnection = mConnection;
                UserData.ModifyInformation(AnalysisJSON.getModifyInformation(key, values));
                //修改本地数据
                SQLite_Table.insertTable(context, PATH.ihuo, PATH.userinfo, key, values, 0);
                SQLite_Table.insertTable(context, PATH.ihuo, PATH.serverinfo, key, values, 0);

                Intent intent = new Intent();
                intent.putExtra("data", "OK");
                setResult(data.getIdentification(), intent);
                finish();
            }

            @Override
            public void onClose(int code, String reason) {
                SystemInfo.showToast(context, "原始密码错误");
                other_edit1.setText("");
                other_edit2.setText("");
                other_edit3.setText("");
            }

            @Override
            public void onBinaryMessage(byte[] payload) {

            }
        });
    }
}
