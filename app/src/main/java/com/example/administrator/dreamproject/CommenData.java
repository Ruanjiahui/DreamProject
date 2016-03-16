package com.example.administrator.dreamproject;

import android.content.ContentValues;

import com.example.administrator.ui_sdk.ListView_Object;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/2/18.
 */
public class CommenData {

    //记录选择图片的路径
    public static String bitmaps = null;

    //记录现在选择图片是第几章
    public static int position = 0;

    public static int FLAG = 0;

    //记录Fragment1是不是第一次加载
    public static boolean Fragment_FLAG = true;
    //文章链表
    public static ArrayList<ListView_Object> list = new ArrayList<>();
    public static ArrayList<String> arrayList = new ArrayList<>();


    /**
     * 将获取文章信息写入本地数据
     *
     * @param articleId
     * @param articleContent
     * @param articleImages
     * @param articleUrl
     * @param articleTime
     * @param articleType
     * @param userId
     * @param articleRelease
     * @param nickname
     * @return
     */
    public static ContentValues getarticle(String articleId, String articleContent, String articleImages, String articleUrl, String articleTime, String articleType, String userId, String articleRelease, String nickname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("articleId", articleId);
        contentValues.put("articleContent", articleContent);
        contentValues.put("articleImages", articleImages);
        contentValues.put("articleUrl", articleUrl);
        contentValues.put("articleTime", articleTime);
        contentValues.put("articleType", articleType);
        contentValues.put("userId", userId);
        contentValues.put("articleRelease", articleRelease);
        contentValues.put("nickname", nickname);
        return contentValues;
    }
}
