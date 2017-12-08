package com.playground.skypass.app.model;

/**
 * Created by rudihartono on 9/9/16.
 */
public class TUserReward {
    /*
    id
    user_id
    reward_id
    */
    @com.google.gson.annotations.SerializedName("id") private String id;
    @com.google.gson.annotations.SerializedName("user_id") private String user_id;
    @com.google.gson.annotations.SerializedName("reward_id") private String reward_id;
    @com.google.gson.annotations.SerializedName("title") private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId(){return this.id;}
    public String getUser_id(){return this.user_id;}
    public String getMonster_id(){return this.reward_id;}
    public void setId(String id){
        this.id = id;
    }
    public void setMonster_id(String monster_id){
        this.reward_id = monster_id;
    }
    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
}
