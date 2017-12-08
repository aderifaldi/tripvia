package com.playground.skypass.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aderifaldi on 10-Sep-17.
 */

@NoArgsConstructor
@Data
public class ModelMonster implements Serializable {


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : [{"id":1,"points":100,"xp":100,"minimum_xp":20,"image":"https://ecs7.tokopedia.net/img/cache/100-square/product-1/2016/5/30/788241/788241_1fbe6373-9e14-4dc0-8a3e-9f1f22b19426.jpg","title":"Si Komo","description":"Si Komo Ungu","start_date":"2017-01-01T00:00:00","end_date":"2017-12-01T00:00:00","latitude":-6.28711,"longitude":106.6724433,"quota":100,"minimum_geofence":5,"type":1}]
     */

    private boolean error;
    private AlertsBean alerts;
    private List<DataBean> data;

    @NoArgsConstructor
    @Data
    public static class AlertsBean implements Serializable{
        /**
         * code : 202
         * message : Sucess
         */

        private String code;
        private String message;
    }

    @NoArgsConstructor
    @Data
    public static class DataBean implements Serializable{
        /**
         * id : 1
         * points : 100
         * xp : 100
         * minimum_xp : 20
         * image : https://ecs7.tokopedia.net/img/cache/100-square/product-1/2016/5/30/788241/788241_1fbe6373-9e14-4dc0-8a3e-9f1f22b19426.jpg
         * title : Si Komo
         * description : Si Komo Ungu
         * start_date : 2017-01-01T00:00:00
         * end_date : 2017-12-01T00:00:00
         * latitude : -6.28711
         * longitude : 106.6724433
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
        private String start_date;
        private String end_date;
        private double latitude;
        private double longitude;
        private int quota;
        private int minimum_geofence;
        private int type;
    }
}
