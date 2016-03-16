package com.example.administrator.data_sdk.FileUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2016/1/21.
 */
public class FileWrite {

    /**
     * 将图片以jpeg格式保存
     *
     * @param bm
     * @param fileName
     * @param path
     */
    public static void saveBitmapFile(Bitmap bm, String fileName, String path) {
        // 保存图片
        File f = new File(path, fileName);
        try {
            FileExist(path);
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取本地的图片
     *
     * @param path
     * @param fileName
     * @return
     */
    public static Bitmap readBitmapFile(String path, String fileName) {
        FileExist(path);
        if (!isFileExist(path)) {
            return null;
        }
        BitmapFactory.Options option = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(path + fileName, option);

        return bm;

    }

    public static File createSDDir(String SDPATH, String dirName) {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
        }
        return dir;
    }

    /**
     * 判断路径是否存在
     * 如果不存在则创建
     *
     * @param SDPATH
     */
    public static void FileExist(String SDPATH) {
        File file = new File(SDPATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 创建文件夹
     *
     * @param path
     */
    public static void createFile(String path) {
        File file = new File(path);
        file.mkdirs();
    }

    /**
     * 判断文件是否存在
     *
     * @param SDPATH
     * @return
     */
    public static boolean isFileExist(String SDPATH) {
        File file = new File(SDPATH);
        return file.exists();
    }

    /**
     * 获取文件列表的文件
     *
     * @param url
     * @return
     */
    public static File[] getFilelist(String url) {
        File file = new File(url);
        File[] files = file.listFiles();

        return files;
    }

    /**
     * 获取文件MD5
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


}
