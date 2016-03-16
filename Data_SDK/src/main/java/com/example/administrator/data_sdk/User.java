package com.example.administrator.data_sdk;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/16.
 */
public class User implements Parcelable {

    //用户ID
    private String userid = "";
    //用户名字
    private String username = "";
    //用户性别
    private int sex = 0;
    //用户头像
    private Bitmap logo = null;
    //用户地址
    private String logo_url = "";
    //用户等级
    private String grade = "";
    //用户个人签名
    private String autograph = "";
    //标识
    private int identification = 0;
    //用户电话
    private String phone = "";
    //用户二维码
    private String qrcode = "";
    //用户生日
    private String birthday = "";
    //用户身高
    private int userheight = 0;
    //用户职业
    private String Occupation = "";
    //用户学历
    private String Education = "";
    //用户家乡
    private String Hometown = "";
    //用户现居住地
    private String live = "";


    public User() {
        super();
    }

    protected User(Parcel in) {
        userid = in.readString();
        username = in.readString();
        sex = in.readInt();
        logo = Bitmap.CREATOR.createFromParcel(in);
        grade = in.readString();
        autograph = in.readString();
        identification = in.readInt();
        phone = in.readString();
        qrcode = in.readString();
        birthday = in.readString();
        userheight = in.readInt();
        Occupation = in.readString();
        Hometown = in.readString();
        live = in.readString();
        Education = in.readString();
        logo_url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHeight(int userheight) {
        this.userheight = userheight;
    }

    public void setOccupation(String Occupation) {
        this.Occupation = Occupation;
    }

    public void setHometown(String Hometown) {
        this.Hometown = Hometown;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public void setEducation(String Education) {
        this.Education = Education;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }


    public String getUserid() {
        return userid;
    }

    public int getSex() {
        return sex;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public String getUsername() {
        return username;
    }

    public String getGrade() {
        return grade;
    }

    public String getAutograph() {
        return autograph;
    }

    public int getIdentification() {
        return identification;
    }


    public String getPhone() {
        return phone;
    }

    public String getQrcode() {
        return qrcode;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getOccupation() {
        return Occupation;
    }

    public int getUserheight() {
        return userheight;
    }

    public String getHometown() {
        return Hometown;
    }

    public String getLive() {
        return live;
    }

    public String getEducation() {
        return Education;
    }

    public String getLogo_url() {
        return logo_url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeString(username);
        dest.writeInt(sex);
        logo.writeToParcel(dest, flags);
        dest.writeString(grade);
        dest.writeString(autograph);
        dest.writeInt(identification);
        dest.writeString(phone);
        dest.writeString(qrcode);
        dest.writeString(birthday);
        dest.writeInt(userheight);
        dest.writeString(Occupation);
        dest.writeString(Hometown);
        dest.writeString(live);
        dest.writeString(Education);
        dest.writeString(logo_url);
    }
}
