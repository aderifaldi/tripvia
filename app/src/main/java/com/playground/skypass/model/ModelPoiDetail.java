package com.playground.skypass.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 07/12/2017.
 */

@NoArgsConstructor
@Data
public class ModelPoiDetail implements Serializable {


    /**
     * status : ok
     * alerts : {"code":200,"message":"success"}
     * data : {"id":1,"type":"rptra","photo":"default","title":"Kenanga.1","description":"","address":"Jl. Makian No. 1, RT 02 RW 05, Kode Pos 10150","phone":"081388007545","facility":"Lapangan Futsal, Mading / Papan Informasi, Amphitheater, Toilet Dewasa, Toilet Disabilitas, Toilet Anak, Keran Cuci Tangan ramah anak, Tempat Sampah terpilah, Informasi tentang kawasan bebas rokok, Pagar, Pintu gerbang, Papan nama RPTRA, Prasasti RPTRA, Thoren air, Selang Panjang, Mesin Air","problem":"Penduduk usia anak yang banyak, Memiliki Anak usia sekolah yang tidak sekolah","location":{"latitude":-6.167257,"longitude":106.807},"nearby":[{"category":"Museum","places":[{"id":3,"type":"museum","photo":"default","title":"National Museum","description":"The most complete collection museum in Indonesia. Total collection recorded 140.00 units, which contain ancient statues, inscription, ceramic,  textile, historical relic and categorized by ethnography. The collection of Indonesian ceramics and etnography in this museum was the largetst and the most comprehensinve in the world","address":"Jl. Medan Merdeka Barat No. 12, Central Jakarta","phone":"","facility":"","problem":"","location":{"latitude":-6.17642,"longitude":106.812958}},{"id":4,"type":"museum","photo":"default","title":"Textile Museum","description":"Textile Museum poses wide-ranging textile related collections such as spinning tools for yarn, weaving tools, woven fabric, bark fabric and animal leather fabric. There are around 120 displayed textile objects of nearly 1,800 collections in total. The museum also provides several facilities and activities, including a batik making course, training of textile conservation, natural coloring application on textile as well as the sequin application.","address":"Jl. KS. Tubun No. 4, West Jakarta","phone":"","facility":"","problem":"","location":{"latitude":-6.18793,"longitude":106.809639}}]},{"category":"Masjid","places":[{"id":7,"type":"masjid","photo":"default","title":"MASJID RAWA BENGKEL","description":"","address":"RAWA BENGKEL JAKARTA BARAT DKI JAKARTA","phone":"","facility":"","problem":"","location":{"latitude":-6.13983,"longitude":106.717461}},{"id":8,"type":"masjid","photo":"default","title":"MASJID PRIMA 2","description":"","address":"PRIMA 2 JAKARTA BARAT DKI JAKARTA","phone":"","facility":"","problem":"","location":{"latitude":-6.12488,"longitude":106.715851}}]}]}
     */

    private String status;
    private Alerts alerts;
    private Data data;

    @NoArgsConstructor
    @lombok.Data
    public static class Alerts implements Serializable {
        /**
         * code : 200
         * message : success
         */

        private int code;
        private String message;
    }

    @NoArgsConstructor
    @lombok.Data
    public static class Data implements Serializable {
        /**
         * id : 1
         * type : rptra
         * photo : default
         * title : Kenanga.1
         * description :
         * address : Jl. Makian No. 1, RT 02 RW 05, Kode Pos 10150
         * phone : 081388007545
         * facility : Lapangan Futsal, Mading / Papan Informasi, Amphitheater, Toilet Dewasa, Toilet Disabilitas, Toilet Anak, Keran Cuci Tangan ramah anak, Tempat Sampah terpilah, Informasi tentang kawasan bebas rokok, Pagar, Pintu gerbang, Papan nama RPTRA, Prasasti RPTRA, Thoren air, Selang Panjang, Mesin Air
         * problem : Penduduk usia anak yang banyak, Memiliki Anak usia sekolah yang tidak sekolah
         * location : {"latitude":-6.167257,"longitude":106.807}
         * nearby : [{"category":"Museum","places":[{"id":3,"type":"museum","photo":"default","title":"National Museum","description":"The most complete collection museum in Indonesia. Total collection recorded 140.00 units, which contain ancient statues, inscription, ceramic,  textile, historical relic and categorized by ethnography. The collection of Indonesian ceramics and etnography in this museum was the largetst and the most comprehensinve in the world","address":"Jl. Medan Merdeka Barat No. 12, Central Jakarta","phone":"","facility":"","problem":"","location":{"latitude":-6.17642,"longitude":106.812958}},{"id":4,"type":"museum","photo":"default","title":"Textile Museum","description":"Textile Museum poses wide-ranging textile related collections such as spinning tools for yarn, weaving tools, woven fabric, bark fabric and animal leather fabric. There are around 120 displayed textile objects of nearly 1,800 collections in total. The museum also provides several facilities and activities, including a batik making course, training of textile conservation, natural coloring application on textile as well as the sequin application.","address":"Jl. KS. Tubun No. 4, West Jakarta","phone":"","facility":"","problem":"","location":{"latitude":-6.18793,"longitude":106.809639}}]},{"category":"Masjid","places":[{"id":7,"type":"masjid","photo":"default","title":"MASJID RAWA BENGKEL","description":"","address":"RAWA BENGKEL JAKARTA BARAT DKI JAKARTA","phone":"","facility":"","problem":"","location":{"latitude":-6.13983,"longitude":106.717461}},{"id":8,"type":"masjid","photo":"default","title":"MASJID PRIMA 2","description":"","address":"PRIMA 2 JAKARTA BARAT DKI JAKARTA","phone":"","facility":"","problem":"","location":{"latitude":-6.12488,"longitude":106.715851}}]}]
         */

        private int id;
        private String type;
        private String photo;
        private String title;
        private String description;
        private String address;
        private String phone;
        private String facility;
        private String problem;
        private Location location;
        private List<Nearby> nearby;

        @NoArgsConstructor
        @lombok.Data
        public static class Location implements Serializable {
            /**
             * latitude : -6.167257
             * longitude : 106.807
             */

            private double latitude;
            private double longitude;
        }

        @NoArgsConstructor
        @lombok.Data
        public static class Nearby implements Serializable {
            /**
             * category : Museum
             * places : [{"id":3,"type":"museum","photo":"default","title":"National Museum","description":"The most complete collection museum in Indonesia. Total collection recorded 140.00 units, which contain ancient statues, inscription, ceramic,  textile, historical relic and categorized by ethnography. The collection of Indonesian ceramics and etnography in this museum was the largetst and the most comprehensinve in the world","address":"Jl. Medan Merdeka Barat No. 12, Central Jakarta","phone":"","facility":"","problem":"","location":{"latitude":-6.17642,"longitude":106.812958}},{"id":4,"type":"museum","photo":"default","title":"Textile Museum","description":"Textile Museum poses wide-ranging textile related collections such as spinning tools for yarn, weaving tools, woven fabric, bark fabric and animal leather fabric. There are around 120 displayed textile objects of nearly 1,800 collections in total. The museum also provides several facilities and activities, including a batik making course, training of textile conservation, natural coloring application on textile as well as the sequin application.","address":"Jl. KS. Tubun No. 4, West Jakarta","phone":"","facility":"","problem":"","location":{"latitude":-6.18793,"longitude":106.809639}}]
             */

            private String category;
            private List<Places> places;

            @NoArgsConstructor
            @lombok.Data
            public static class Places implements Serializable {
                /**
                 * id : 3
                 * type : museum
                 * photo : default
                 * title : National Museum
                 * description : The most complete collection museum in Indonesia. Total collection recorded 140.00 units, which contain ancient statues, inscription, ceramic,  textile, historical relic and categorized by ethnography. The collection of Indonesian ceramics and etnography in this museum was the largetst and the most comprehensinve in the world
                 * address : Jl. Medan Merdeka Barat No. 12, Central Jakarta
                 * phone :
                 * facility :
                 * problem :
                 * location : {"latitude":-6.17642,"longitude":106.812958}
                 */

                private int id;
                private String type;
                private String photo;
                private String title;
                private String description;
                private String address;
                private String phone;
                private String facility;
                private String problem;
                private LocationX location;

                @NoArgsConstructor
                @lombok.Data
                public static class LocationX implements Serializable{
                    /**
                     * latitude : -6.17642
                     * longitude : 106.812958
                     */

                    private double latitude;
                    private double longitude;
                }
            }
        }
    }
}
