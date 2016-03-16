package com.example.administrator.data_sdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/1/18.
 */
public class Transformation {


    /**
     * 资源图片转Bitmap
     *
     * @param activity
     * @param resource
     * @return
     */
    public static Bitmap Resource2Bitmap(Activity activity, int resource) {
        return BitmapFactory.decodeResource(activity.getResources(), resource);
    }

    public static Drawable Resource2Drawable(Activity activity, int resource) {
        return activity.getResources().getDrawable(resource);
    }

    /**
     * 将路径转化成Bitmap
     *
     * @param uri
     * @return
     */
    public static Bitmap Path2Bitmap(String uri) {
        return BitmapFactory.decodeFile(uri.toString());
    }

    /**
     * 将路径转化成Drawable
     *
     * @param uri
     * @return
     */
    public static Drawable Path2Drawable(String uri) {
        return Bitmap2Drawable(BitmapFactory.decodeFile(uri.toString()));
    }

    /**
     * drawable转Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap Drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        return bitmapDrawable.getBitmap();
    }

    public static Drawable Bitmap2Drawable(Bitmap drawable) {
        return new BitmapDrawable(drawable);
    }

    /**
     * 处理图片
     *
     * @param bm        所要转换的bitmap
     * @param newWidth
     * @param newHeight
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoomImg(Context context, Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();

        newWidth = SystemInfo.dip2px(context, newWidth);
        newHeight = SystemInfo.dip2px(context, newHeight);
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    /**
     * 处理图片
     *
     * @param drawable  所要转换的bitmap
     * @param newWidth
     * @param newHeight
     * @return 指定宽高的bitmap
     */
    public static Drawable zoomImg(Activity activity, Drawable drawable, int newWidth, int newHeight) {
        // 获得图片的宽高
        Bitmap bm = Drawable2Bitmap(drawable);

        int width = bm.getWidth();
        int height = bm.getHeight();

        newWidth = SystemInfo.dip2px(activity, newWidth);
        newHeight = SystemInfo.dip2px(activity, newHeight);
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片   www.2cto.com
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return Bitmap2Drawable(newbm);
    }

    /**
     * 将给定图片维持宽高比缩放后，截取正中间的正方形部分。
     *
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }
        return result;
    }

    /**
     * 将给定图片维持宽高比缩放后，截取正中间的正方形部分。
     *
     * @param drawable     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Drawable centerSquareScaleBitmap(Drawable drawable, int edgeLength) {
        // 获得图片的宽高
        Bitmap bitmap = Drawable2Bitmap(drawable);
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }
        return Bitmap2Drawable(result);
    }
}
