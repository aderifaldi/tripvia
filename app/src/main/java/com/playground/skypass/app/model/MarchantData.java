package com.playground.skypass.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 31-Jul-16.
 */
public class MarchantData implements Serializable {

    private String id;
    private String name;
    private double lat;
    private double lon;
    private String geo_province;
    private String geo_city;
    private String geo_district;
    private String geo_postal_code;
    private String distance;
    private String address;
    private String contact_name;
    private String phone;
    private String since_year;
    private String image_url;
    private String buyers;
    private String likes;
    private boolean is_open;

    public boolean is_open() {
        return is_open;
    }

    public String getBuyers() {
        return buyers;
    }

    public String getLikes() {
        return likes;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getGeo_province() {
        return geo_province;
    }

    public String getGeo_city() {
        return geo_city;
    }

    public String getGeo_district() {
        return geo_district;
    }

    public String getGeo_postal_code() {
        return geo_postal_code;
    }

    public String getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSince_year() {
        return since_year;
    }
}
