package com.playground.skypass.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 12/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelCheckIn {


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : {"id":0,"user_id":"10209350862402067","event_id":1,"image":"https://api-cache.goersapp.com/v2/galleries/custom?cf=78&id=40512&width=1366","title":"Amazing Art World Bandung","created_date":"2017-09-12T04:32:33.7714826+00:00"}
     */

    private boolean error;
    private Alerts alerts;
    private Data data;

    @NoArgsConstructor
    @lombok.Data
    public static class Alerts {
        /**
         * code : 202
         * message : Sucess
         */

        private String code;
        private String message;
    }

    @NoArgsConstructor
    @lombok.Data
    public static class Data {
        /**
         * id : 0
         * user_id : 10209350862402067
         * event_id : 1
         * image : https://api-cache.goersapp.com/v2/galleries/custom?cf=78&id=40512&width=1366
         * title : Amazing Art World Bandung
         * created_date : 2017-09-12T04:32:33.7714826+00:00
         */

        private int id;
        private String user_id;
        private int event_id;
        private String image;
        private String title;
        private String created_date;
    }
}
