package com.playground.skypass.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by aderifaldi on 08-Sep-16.
 */
public class Tmonster implements Serializable {

    @com.google.gson.annotations.SerializedName("id") private String id;
    @com.google.gson.annotations.SerializedName("points") private int points;
    @com.google.gson.annotations.SerializedName("xp") private int xp;
    @com.google.gson.annotations.SerializedName("minimum_xp") private int minimum_xp;
    @com.google.gson.annotations.SerializedName("image") private String image;
    @com.google.gson.annotations.SerializedName("title") private String title;
    @com.google.gson.annotations.SerializedName("description") private String description;
    @com.google.gson.annotations.SerializedName("end_date") private Date end_date;
    @com.google.gson.annotations.SerializedName("latitude") private double latitude;
    @com.google.gson.annotations.SerializedName("longitude") private double longitude;
    @com.google.gson.annotations.SerializedName("start_date") private Date start_date;
    @com.google.gson.annotations.SerializedName("quota") private int quota;
    @com.google.gson.annotations.SerializedName("minimum_geofence") private double minimum_geofence;
    @com.google.gson.annotations.SerializedName("type") private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMinimum_geofence(){return minimum_geofence;}

    public void setMinimum_geofence(double minimum_geofence){this.minimum_geofence = minimum_geofence;}

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

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getMinimum_xp() {
        return minimum_xp;
    }

    public void setMinimum_xp(int minimum_xp) {
        this.minimum_xp = minimum_xp;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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
