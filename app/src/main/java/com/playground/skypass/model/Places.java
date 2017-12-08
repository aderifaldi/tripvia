package com.playground.skypass.model;

import java.io.Serializable;

import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 07/12/2017.
 */
@NoArgsConstructor
@lombok.Data
public class Places implements Serializable {
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
}
