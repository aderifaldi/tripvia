package com.playground.skypass.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rudihartono on 9/9/16.
 */
public class TReward implements Serializable{
    @com.google.gson.annotations.SerializedName("id") private String id;
    @com.google.gson.annotations.SerializedName("points") private int points;
    @com.google.gson.annotations.SerializedName("title") private String title;
    @com.google.gson.annotations.SerializedName("description") private String description;
    @com.google.gson.annotations.SerializedName("end_date") private Date end_date;
    @com.google.gson.annotations.SerializedName("start_date") private Date start_date;
    @com.google.gson.annotations.SerializedName("quota") private int quota;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}
