package com.playground.skypass.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 13/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelProfile {


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : {"id":4,"user_id":"10155209111459531","user_photo":"http://graph.facebook.com/10155209111459531/picture?type=large","email":"","bio":"","total_xp":100,"total_points":100}
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
         * id : 4
         * user_id : 10155209111459531
         * user_photo : http://graph.facebook.com/10155209111459531/picture?type=large
         * email :
         * bio :
         * total_xp : 100
         * total_points : 100
         */

        private int id;
        private String user_id;
        private String user_photo;
        private String email;
        private String bio;
        private int total_xp;
        private int total_points;
    }
}
