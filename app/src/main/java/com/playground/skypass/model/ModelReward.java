package com.playground.skypass.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 06/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelReward implements Serializable{


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : [{"id":1,"points":28000,"title":"Apple MacBook Pro","description":"Apple MacBook Pro MD101ID-A Notebook [13.3 inch/ 500GB] merupakan Apple MacBook Pro berukuran 13.3 inch dengan resolusi 1280 x 800. ","image":"https://www.dicoding.com/images/original/reward/201705012234552d2384405d854e1fe25808768dd3a8d9.jpg","start_date":"2017-08-01T00:00:00","end_date":"2017-09-29T00:00:00","quota":100},{"id":2,"points":18000,"title":"Lenovo ThinkPad E470","description":"Lenovo ThinkPad Edge E470 adalah Laptop 14 Inch yang tipis dan ringan sehingga mudah dibawa kemana saja.","image":"https://www.dicoding.com/images/original/reward/20170501223056efdc2406485bd6c39a164376d90a8626.jpg","start_date":"2017-08-01T00:00:00","end_date":"2017-09-20T00:00:00","quota":100}]
     */

    private boolean error;
    private Alerts alerts;
    private List<Data> data;

    @NoArgsConstructor
    @lombok.Data
    public static class Alerts implements Serializable{
        /**
         * code : 202
         * message : Sucess
         */

        private String code;
        private String message;
    }

    @NoArgsConstructor
    @lombok.Data
    public static class Data implements Serializable{
        /**
         * id : 1
         * points : 28000
         * title : Apple MacBook Pro
         * description : Apple MacBook Pro MD101ID-A Notebook [13.3 inch/ 500GB] merupakan Apple MacBook Pro berukuran 13.3 inch dengan resolusi 1280 x 800.
         * image : https://www.dicoding.com/images/original/reward/201705012234552d2384405d854e1fe25808768dd3a8d9.jpg
         * start_date : 2017-08-01T00:00:00
         * end_date : 2017-09-29T00:00:00
         * quota : 100
         */

        private int id;
        private int points;
        private String title;
        private String description;
        private String image;
        private String start_date;
        private String end_date;
        private int quota;
    }
}
