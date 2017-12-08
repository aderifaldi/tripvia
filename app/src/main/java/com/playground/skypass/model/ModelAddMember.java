package com.playground.skypass.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 02/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelAddMember {


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : {"id":1,"user_id":"23214124124","user_photo":"","email":"rudi.hartono@student.upi.edu","bio":null,"total_xp":0,"total_points":0}
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
         * id : 1
         * user_id : 23214124124
         * user_photo :
         * email : rudi.hartono@student.upi.edu
         * bio : null
         * total_xp : 0
         * total_points : 0
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
