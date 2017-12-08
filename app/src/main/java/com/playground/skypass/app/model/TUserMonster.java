package com.playground.skypass.app.model;

import java.io.Serializable;

/**
 * Created by rudihartono on 9/9/16.
 */
public class TUserMonster implements Serializable {

    @com.google.gson.annotations.SerializedName("id") private String id;
    @com.google.gson.annotations.SerializedName("user_id") private String user_id;
    @com.google.gson.annotations.SerializedName("monster_id") private String monster_id;
    @com.google.gson.annotations.SerializedName("image") private String image;
    @com.google.gson.annotations.SerializedName("title") private String title;

    public String getId(){return this.id;}
    public String getUser_id(){return this.user_id;}
    public String getMonster_id(){return this.monster_id;}
    public void setId(String id){
        this.id = id;
    }
    public void setMonster_id(String monster_id){
        this.monster_id = monster_id;
    }
    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
