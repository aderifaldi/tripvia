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
public class ModelMyCheckIn {


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : [{"id":1,"points":100,"xp":100,"minimum_xp":40,"image":"https://api-cache.goersapp.com/v2/galleries/custom?cf=78&id=40512&width=1366","title":"Amazing Art World Bandung","description":"Konsep 3D Museum memang bukan suatu hal yang baru di Indonesia dan sudah ada di beberapa kota. Namun, Amazing Art World yang  menempati areal seluas 15.500 m2 di kota Bandung ini diklaim sebagai museum lukisan 3 dimensi terbesar yang ada di indonesia.","start_date":"2017-08-01T00:00:00","end_date":"2017-09-29T00:00:00","latitude":-6.8745717,"longitude":107.5943043,"quota":100,"minimum_geofence":2,"is_private":false}]
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
         * minimum_xp : 40
         * image : https://api-cache.goersapp.com/v2/galleries/custom?cf=78&id=40512&width=1366
         * title : Amazing Art World Bandung
         * description : Konsep 3D Museum memang bukan suatu hal yang baru di Indonesia dan sudah ada di beberapa kota. Namun, Amazing Art World yang  menempati areal seluas 15.500 m2 di kota Bandung ini diklaim sebagai museum lukisan 3 dimensi terbesar yang ada di indonesia.
         * start_date : 2017-08-01T00:00:00
         * end_date : 2017-09-29T00:00:00
         * latitude : -6.8745717
         * longitude : 107.5943043
         * quota : 100
         * minimum_geofence : 2
         * is_private : false
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
        private boolean is_private;
    }
}
