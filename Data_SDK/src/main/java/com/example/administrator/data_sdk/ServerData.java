package com.example.administrator.data_sdk;

/**
 * Created by Administrator on 2016/1/27.
 */
public class ServerData {

    private String name = "";
    private int online = 0;
    private String password = "";
    private String phone = "";

    public void setName(String name) {
        this.name = name;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public int getOnline() {
        return online;
    }
}
