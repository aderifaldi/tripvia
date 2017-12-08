package com.playground.skypass.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aderifaldi on 13-Sep-17.
 */

@NoArgsConstructor
@Data
public class ModelMyMonster {


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : [{"id":1,"points":100,"xp":100,"minimum_xp":20,"image":"https://cityviewbsd.azurewebsites.net/Uploads/Property/b65f24dd-4316-4ded-a1ce-792ed4e45a69.jpg","title":"Elang Bondol","description":"Elang Bondol asli Indonesia","start_date":"2017-01-01T00:00:00","end_date":"2017-12-01T00:00:00","latitude":-6.3005099,"longitude":106.6486091,"quota":100,"minimum_geofence":5,"type":1},{"id":1,"points":100,"xp":100,"minimum_xp":20,"image":"https://cityviewbsd.azurewebsites.net/Uploads/Property/b65f24dd-4316-4ded-a1ce-792ed4e45a69.jpg","title":"Elang Bondol","description":"Elang Bondol asli Indonesia","start_date":"2017-01-01T00:00:00","end_date":"2017-12-01T00:00:00","latitude":-6.3005099,"longitude":106.6486091,"quota":100,"minimum_geofence":5,"type":1}]
     */

    private boolean error;
    private AlertsBean alerts;
    private List<DataBean> data;

    @NoArgsConstructor
    @Data
    public static class AlertsBean {
        /**
         * code : 202
         * message : Sucess
         */

        private String code;
        private String message;
    }

    @NoArgsConstructor
    @Data
    public static class DataBean {
        /**
         * id : 1
         * points : 100
         * xp : 100
         * minimum_xp : 20
         * image : https://cityviewbsd.azurewebsites.net/Uploads/Property/b65f24dd-4316-4ded-a1ce-792ed4e45a69.jpg
         * title : Elang Bondol
         * description : Elang Bondol asli Indonesia
         * start_date : 2017-01-01T00:00:00
         * end_date : 2017-12-01T00:00:00
         * latitude : -6.3005099
         * longitude : 106.6486091
         * quota : 100
         * minimum_geofence : 5
         * type : 1
         */

        private int id;
        private int points;
        private int xp;
        private int minimum_xp;
        private String image;
        private String title;
        private String description;
        private Date start_date;
        private Date end_date;
        private double latitude;
        private double longitude;
        private int quota;
        private int minimum_geofence;
        private int type;
    }
}
