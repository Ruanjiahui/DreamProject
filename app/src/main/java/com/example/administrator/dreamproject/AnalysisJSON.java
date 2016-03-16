package com.example.administrator.dreamproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.administrator.data_sdk.Msg;
import com.example.administrator.data_sdk.SystemData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/12.
 */
public class AnalysisJSON {


    /**
     * 解析用户信息
     *
     * @param data
     * @return
     */
    public static ContentValues getUserinfo(String data) {
        ContentValues contentValues = new ContentValues();
        JSONObject json = null;
        try {
            JSONArray array = new JSONArray(data);

            json = array.getJSONObject(0);

            //将从服务器获取的数据保存到本地数据库
            contentValues.put("userid", json.getString("userid"));
            contentValues.put("nickname", json.getString("nickname"));
            contentValues.put("truename", json.getString("truename"));
            contentValues.put("password", json.getString("password"));
            contentValues.put("company", json.getString("company"));
            contentValues.put("viplev", Integer.parseInt(json.getString("viplev")));
            contentValues.put("sexy", json.getString("sexy"));
            contentValues.put("wbid", json.getString("wbid"));
            contentValues.put("qq", json.getString("qq"));
            contentValues.put("wxid", json.getString("wxid"));
            contentValues.put("email", json.getString("email"));
            contentValues.put("icon", json.getString("icon"));
            contentValues.put("picture", json.getString("picture"));
            contentValues.put("d2code", json.getString("d2code"));
            contentValues.put("address", json.getString("address"));
            contentValues.put("postcode", json.getString("postcode"));
            contentValues.put("idcard", json.getString("idcard"));
            contentValues.put("note", json.getString("note"));
            contentValues.put("registerdate", json.getString("registerdate"));
            contentValues.put("bankaccount", json.getString("bankaccount"));
            contentValues.put("bankname", json.getString("bankname"));
            contentValues.put("balance", json.getString("balance"));
            contentValues.put("available", json.getString("available"));
            if (json.getString("state").equals("")) {
                contentValues.put("state", 0);
            } else {
                contentValues.put("state", Integer.parseInt(json.getString("state")));
            }
            contentValues.put("appversion", json.getString("appversion"));
            if (json.getString("Integral").equals("")) {
                contentValues.put("Integral", 0);
            } else {
                contentValues.put("Integral", Integer.parseInt(json.getString("Integral")));
            }
            contentValues.put("height", json.getString("height"));
            contentValues.put("birthday", json.getString("birthday"));
            contentValues.put("occupation", json.getString("occupation"));
            contentValues.put("education", json.getString("education"));
            contentValues.put("autograph", json.getString("autograph"));
            contentValues.put("phone", json.getString("phone"));

        } catch (JSONException e) {
            Log.e("error", "this is data not json");
            e.printStackTrace();
        }
        return contentValues;
    }

    public static String getModifyInformation(String key, String value) {
        JSONObject json = new JSONObject();

        try {
            json.put("fieldname", key);
            json.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "" + json;
    }

    public static Msg getMsg(String json) {
        Msg msg = new Msg();

        if (json.substring(0, 1).equals("\"")) {
            json = json.substring(1, json.length() - 1);
        }
        try {
            JSONArray array = new JSONArray(json);
            JSONObject jsonObject = array.getJSONObject(0);

            msg.setChannelid(jsonObject.getString("channelid"));
            msg.setContent(SystemData.Transcodingdecode(jsonObject.getString("content")));
            msg.setName(jsonObject.getString("nickname"));
            msg.setId(jsonObject.getString("sender"));
            msg.setRead("0");
            //判断是不是自己发出的信息
            if (MainActivity.sendid.equals(jsonObject.getString("nickname"))) {
                msg.setMemsg(true);
            } else {
                msg.setMemsg(false);
            }

            if (PATH.acString.equals("Chat")) {
                msg.setRead("1");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("channelid", msg.getChannelid());
        contentValues.put("sender", msg.getId());
        contentValues.put("nickname", msg.getName());
        contentValues.put("content", msg.getContent());
        contentValues.put("memsg", msg.getMemsg() + "");
        contentValues.put("read", msg.getRead());
        contentValues.put("time", "new now()");

        SQLite_Table.insert_Table2(MainActivity.context, PATH.ihuo, PATH.msg, contentValues);

        return msg;
    }

    public static ArrayList<Msg> getChatMsg(Cursor cursor) {
        ArrayList<Msg> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Msg msg = new Msg();
            msg.setName(cursor.getString(2));
            msg.setContent(cursor.getString(3));
            if ("false".equals(cursor.getString(4))) {
                msg.setMemsg(false);
            } else {
                msg.setMemsg(true);
            }
            msg.setId(cursor.getString(1));
            msg.setRead(cursor.getString(5));
            list.add(msg);
        }
        return list;
    }

    public static ArrayList<Msg> getChatFlagMsg(Cursor cursor) {
        ArrayList<Msg> list = new ArrayList<>();
        Msg msg = new Msg();

        while (cursor.moveToNext()) {
            msg.setRead(cursor.getString(3));
            msg.setChannelid(cursor.getString(0));
            msg.setContent(cursor.getString(2));
            msg.setName(cursor.getString(1));
            list.add(msg);
        }
        return list;
    }

    public static JSONArray PutChatMsg(String channelid, String nickname, String content, String sender) {
        JSONArray array = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("channelid", channelid);
            jsonObject.put("nickname", nickname);
            jsonObject.put("content", content);
            jsonObject.put("sender", sender);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        array.put(jsonObject);

        return array;
    }

    public static JSONObject ReleaseMsg(String resid, String content, String submittype) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", resid);
            jsonObject.put("content", content);
            jsonObject.put("submittype", submittype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static ArrayList<ReleaseData> Release_Information(String msg) {
        ArrayList<ReleaseData> list = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(msg);
            JSONArray array = obj.getJSONArray("article");
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                ReleaseData releaseData = new ReleaseData();
                releaseData.setArticle_id(json.getString("articleId"));
                releaseData.setArticle_content(json.getString("articleContent"));
                releaseData.setArticle_images(json.getString("articleImages"));
                releaseData.setArticle_release(json.getString("articleRelease"));
                releaseData.setArticle_time(json.getString("articleTime"));
                releaseData.setArticle_type(json.getString("articleType"));
                releaseData.setArticle_url(json.getString("articleUrl"));
                releaseData.setUser_id(json.getString("userId"));
                releaseData.setNickname(json.getString("userNickname"));

                list.add(releaseData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


}
