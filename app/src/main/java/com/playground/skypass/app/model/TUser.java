package com.playground.skypass.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 09-Sep-16.
 */
public class TUser implements Serializable {

    @com.google.gson.annotations.SerializedName("id") private String id;
    @com.google.gson.annotations.SerializedName("user_id") private String user_id;
    @com.google.gson.annotations.SerializedName("user_photo") private String user_photo;
    @com.google.gson.annotations.SerializedName("email") private String email;
    @com.google.gson.annotations.SerializedName("bio") private String bio;
    @com.google.gson.annotations.SerializedName("total_xp") private int total_xp;
    @com.google.gson.annotations.SerializedName("total_points") private int total_points;

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

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getTotal_xp() {
        return total_xp;
    }

    public void setTotal_xp(int total_xp) {
        this.total_xp = total_xp;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }
}
