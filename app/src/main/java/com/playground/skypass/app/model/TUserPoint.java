package com.playground.skypass.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 10-Sep-16.
 */
public class TUserPoint implements Serializable {

    @com.google.gson.annotations.SerializedName("id") private String id;
    @com.google.gson.annotations.SerializedName("user_id") private String user_id;
    @com.google.gson.annotations.SerializedName("total_xp") private int total_xp;
    @com.google.gson.annotations.SerializedName("total_point") private int total_point;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTotal_xp() {
        return total_xp;
    }

    public void setTotal_xp(int total_xp) {
        this.total_xp = total_xp;
    }

    public int getTotal_point() {
        return total_point;
    }

    public void setTotal_point(int total_point) {
        this.total_point = total_point;
    }
}
