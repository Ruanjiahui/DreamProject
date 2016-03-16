package com.example.administrator.data_sdk;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.example.administrator.data_sdk.FileUtil.FileWrite;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class SystemData {

    private static String IMAGE_TYPE = "image/*";

    public static void SystemImage(Activity activity, int IMAGE_CODE) {
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType(IMAGE_TYPE);
        activity.startActivityForResult(getAlbum, IMAGE_CODE);
    }


    /**
     * 扫描全盘的图片
     *
     * @param context
     * @return
     */
    public static List<HashMap<String, String>> LocalImage(Context context) {
        // 指定要查询的uri资源
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        // 查询的字段
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE};
        // 条件
        String selection = MediaStore.Images.Media.MIME_TYPE + "=?";
        // 条件值(這裡的参数不是图片的格式，而是标准，所有不要改动)
        String[] selectionArgs = {"image/jpeg"};
        // 排序
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
        // 查询sd卡上的图片
        Cursor cursor = contentResolver.query(uri, projection, selection,
                selectionArgs, sortOrder);
        List<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
        if (cursor != null) {
            HashMap<String, String> imageMap = null;
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                imageMap = new HashMap<String, String>();
                // 获得图片的id
                imageMap.put("imageID", cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media._ID)));
                // 获得图片显示的名称
                imageMap.put("imageName", cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)));
                // 获得图片的信息
                imageMap.put(
                        "imageInfo",
                        "" + cursor.getLong(cursor
                                .getColumnIndex(MediaStore.Images.Media.SIZE) / 1024)
                                + "kb");
                // 获得图片所在的路径(可以使用路径构建URI)
                imageMap.put("data", cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA)));
                imageList.add(imageMap);
            }
            // 关闭cursor
            cursor.close();
        }
        return imageList;
    }

    /**
     * 将系统图片取出来并且压缩大小
     *
     * @param context
     * @param data
     * @return
     */
    public static Bitmap handlerbitmap(Context context, Intent data, int width, int height) {
        Bitmap bm = null;
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = context.getContentResolver();
        Uri originalUri = data.getData();        //获得图片的uri
        try {
            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bm == null) {
            return null;
        }
        //显得到bitmap图片
        bm = Transformation.zoomImg(context, bm, SystemInfo.dip2px(context, width), SystemInfo.dip2px(context, height));
        return bm;
    }

    /**
     * 将图片剪切成圆形
     *
     * @param source
     * @return
     */
    public static Bitmap createRoundConerImage(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(source.getWidth(),
                source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, 200, 200, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 获取固定路径的Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;

        //判断文件夹是否存在
        if (!FileWrite.isFileExist(path)) {
            //不存在则创建文件夹
            FileWrite.createFile(path);
            return null;
        }
        bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    public static String Transcodingdecode(String msg) {
        return new String(Base64.decode(msg.getBytes(), Base64.DEFAULT));
    }

    public static String EncodeStr(String s) {
        return new String(Base64.encode(s.getBytes(), Base64.DEFAULT));
    }

    private static int mHour = 0;
    private static int mMinuts = 0;
    private static int mSecond = 0;

    public static Calendar getTime() {
        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        return mCalendar;
    }

    public static int getTimeHour() {
        return mHour = getTime().get(Calendar.HOUR);
    }

    public static int getTimeMinuts() {
        return mHour = getTime().get(Calendar.MINUTE);
    }

    public static int getTimeSecond() {
        return mHour = getTime().get(Calendar.SECOND);
    }

    public static String getSystemTime() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.format(new Date());

        return "" + df;
    }

}
