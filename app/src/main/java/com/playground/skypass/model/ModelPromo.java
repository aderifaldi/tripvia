package com.playground.skypass.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 06/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelPromo implements Serializable{


    /**
     * error : false
     * alerts : {"code":"202","message":"Sucess"}
     * data : [{"id":1,"image":"https://s2.bukalapak.com/uploads/flash_banner/72422/s-824-392/Banner_2_kurban2sept.jpg","title":"Berkurban Lebih Mudah dan Tepat Sasaran di Bukalapak","description":"Mari berkurban di Bukalapak","end_date":"2017-09-01T00:00:00","start_date":"2017-08-01T00:00:00","xp":200,"point":2000},{"id":2,"image":"https://s2.bukalapak.com/uploads/flash_banner/71422/s-1256-824/Banner_1_bayarcincai_sept.jpg","title":"Belanja Sekarang dan Dapatkan Diskon 20% Say","description":"Mari belanja sekarang dan dapatkan diskon","end_date":"2017-09-01T00:00:00","start_date":"2017-08-01T00:00:00","xp":400,"point":2000},{"id":7,"image":"/Uploads/Promo/e41ada17-4b41-46fa-aa11-de00fc6ef090.jpg","title":"Lebih Cepat Sampai Gratis Ongkos Kirim","description":"Lebih Cepat Sampai Gratis Ongkos KirimLebih Cepat Sampai Gratis Ongkos Kirim","end_date":"2011-04-04T00:00:00","start_date":"2011-04-04T00:00:00","xp":30,"point":20}]
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
         * image : https://s2.bukalapak.com/uploads/flash_banner/72422/s-824-392/Banner_2_kurban2sept.jpg
         * title : Berkurban Lebih Mudah dan Tepat Sasaran di Bukalapak
         * description : Mari berkurban di Bukalapak
         * end_date : 2017-09-01T00:00:00
         * start_date : 2017-08-01T00:00:00
         * xp : 200
         * point : 2000
         */

        private int id;
        private String image;
        private String title;
        private String description;
        private Date end_date;
        private Date start_date;
        private int xp;
        private int point;
    }
}
