package com.example.administrator.data_sdk;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/16.
 */
public class AcitivityData implements Parcelable {

    //网址
    private String url = "";
    //标题
    private String title = "";
    //内容
    private String content = "";
    //Identification标识
    private int identification = 0;
    //图片
    private Bitmap bitmap = null;
    //id
    private int ID = 0;
    //时间
    private String time = "";
    //聊天标志
    private String channelid = "";

    private User user = null;

    public AcitivityData() {
        super();
    }

    protected AcitivityData(Parcel in) {
        url = in.readString();
        title = in.readString();
        content = in.readString();
        identification = in.readInt();
        ID = in.readInt();
        time = in.readString();
        channelid = in.readString();
//        bitmap = Bitmap.CREATOR.createFromParcel(in);
//        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<AcitivityData> CREATOR = new Creator<AcitivityData>() {
        @Override
        public AcitivityData createFromParcel(Parcel in) {
            return new AcitivityData(in);
        }

        @Override
        public AcitivityData[] newArray(int size) {
            return new AcitivityData[size];
        }
    };

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIdentification(int identification) {
        this.identification = identification;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChannelid(String channelid){
        this.channelid = channelid;
    }

//    public void setBitmap(Bitmap bitmap) {
//        this.bitmap = bitmap;
//    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getIdentification() {
        return identification;
    }

    public User getUser() {
        return user;
    }

    public String getChannelid(){
        return channelid;
    }

//    public Bitmap getBitmap() {
//        return bitmap;
//    }

    public int getID() {
        return ID;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(identification);
        dest.writeInt(ID);
        dest.writeString(time);
        dest.writeString(channelid);
//        bitmap.writeToParcel(dest, flags);
//        dest.writeParcelable(user, flags);
    }
}
