package com.example.administrator.dreamproject;

/**
 * Created by Administrator on 2016/2/17.
 */
public class ReleaseData {

    private String article_id = null;
    private String article_content = null;
    private String article_images = null;
    private String article_url = null;
    private String article_time = null;
    private String article_type = null;
    private String user_id = null;
    private String article_release = null;
    private String nickname = null;

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    public void setArticle_images(String article_images) {
        this.article_images = article_images;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public void setArticle_time(String article_time) {
        this.article_time = article_time;
    }

    public void setArticle_type(String article_type) {
        this.article_type = article_type;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setArticle_release(String article_release) {
        this.article_release = article_release;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }


    public String getArticle_id() {
        return article_id;
    }

    public String getArticle_content() {
        return article_content;
    }

    public String getArticle_images() {
        return article_images;
    }

    public String getArticle_url() {
        return article_url;
    }

    public String getArticle_time() {
        return article_time;
    }

    public String getArticle_type() {
        return article_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getArticle_release() {
        return article_release;
    }

    public String getNickname(){
        return nickname;
    }
}
