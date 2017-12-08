package com.playground.skypass.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 15/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelTransaction {


    /**
     * error : false
     * alerts : {"code":"202","message":"success"}
     * data : [{"transaction_id":"26733E40-1F2","merchant_id":"0123214","merchant_name":"Skypass Mart","discount":0,"amount":200000,"user_id":"10209350862402067"}]
     */

    private boolean error;
    private Alerts alerts;
    private List<Data> data;

    @NoArgsConstructor
    @lombok.Data
    public static class Alerts {
        /**
         * code : 202
         * message : success
         */

        private String code;
        private String message;
    }

    @NoArgsConstructor
    @lombok.Data
    public static class Data {
        /**
         * transaction_id : 26733E40-1F2
         * merchant_id : 0123214
         * merchant_name : Skypass Mart
         * discount : 0
         * amount : 200000
         * user_id : 10209350862402067
         */

        private String transaction_id;
        private String merchant_id;
        private String merchant_name;
        private int discount;
        private int amount;
        private String user_id;
    }
}
