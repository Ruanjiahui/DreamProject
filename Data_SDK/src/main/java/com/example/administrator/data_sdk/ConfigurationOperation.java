package com.example.administrator.data_sdk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/1/12.
 */
public class ConfigurationOperation {

    public static String path = "/data/data/com.example.administrator.dreamproject/Properties.dat";
    private static Properties properties = new Properties();

    /**
     * 保存退出后的路径
     */
    public static void writeConfiguration(String key, String setting) {
        properties.put(key, setting);

        try {
            FileOutputStream out = new FileOutputStream(path, false);
            properties.store(out, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Properties数据
     *
     * @return
     */
    public static String readProperties(String key) {
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(path);
            //获取数据
            properties.load(in);
            if (properties.get(key) != null) {
                return properties.get(key).toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int LoadState(String key) {
        if (ConfigurationOperation.readProperties(key).equals("") || ConfigurationOperation.readProperties(key).equals("0")) {
            return 0;
        }
        return 1;
    }
}
