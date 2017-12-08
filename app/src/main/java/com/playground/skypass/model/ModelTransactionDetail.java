package com.playground.skypass.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 15/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelTransactionDetail {


    /**
     * error : false
     * alerts : {"code":"202","message":"success"}
     * data : {"transaction_id":"26733E40-1F2","products":[{"name":"Mineral Water","code":null,"quantity":2,"price":114},{"name":"Irish Beef Steak","code":null,"quantity":1,"price":363},{"name":"Fish Diana","code":null,"quantity":1,"price":378},{"name":"Grilled Fish","code":null,"quantity":1,"price":337},{"name":"Finger Chips","code":null,"quantity":2,"price":105},{"name":"Coke","code":null,"quantity":1,"price":59}],"total_price":200000,"merchant":"Skypass Mart","discount":0,"marchant_v_a_account":"0123214"}
     */

    private boolean error;
    private Alerts alerts;
    private Data data;

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
         * products : [{"name":"Mineral Water","code":null,"quantity":2,"price":114},{"name":"Irish Beef Steak","code":null,"quantity":1,"price":363},{"name":"Fish Diana","code":null,"quantity":1,"price":378},{"name":"Grilled Fish","code":null,"quantity":1,"price":337},{"name":"Finger Chips","code":null,"quantity":2,"price":105},{"name":"Coke","code":null,"quantity":1,"price":59}]
         * total_price : 200000
         * merchant : Skypass Mart
         * discount : 0
         * marchant_v_a_account : 0123214
         */

        private String transaction_id;
        private int total_price;
        private String merchant;
        private int discount;
        private String marchant_v_a_account;
        private List<Products> products;

        @NoArgsConstructor
        @lombok.Data
        public static class Products {
            /**
             * name : Mineral Water
             * code : null
             * quantity : 2
             * price : 114
             */

            private String name;
            private String code;
            private int quantity;
            private int price;
        }
    }
}
