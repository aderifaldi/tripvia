package com.playground.skypass.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aderifaldi on 08-Aug-17.
 */

@NoArgsConstructor
@Data
public class ModelBase implements Serializable{


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     */

    private boolean error;
    private Alerts alerts;

    @NoArgsConstructor
    @Data
    public static class Alerts {
        /**
         * code : 202
         * message : Sucess
         */

        private String code;
        private String message;
    }
}
