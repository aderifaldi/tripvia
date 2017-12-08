package com.playground.skypass.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by aderifaldi on 17-Nov-17.
 */

@NoArgsConstructor
@Data
public class ModelWisata implements Serializable{

    private List<DataBean> data;

    @NoArgsConstructor
    @Data
    public static class DataBean implements Serializable{
        /**
         * title : Danau Toba
         * desc : Danau vulkanik yang memiliki panjang 100 km dan lebar mencapai 30 km masih menjadi salah satu primadona wisata di Sumatera Utara. Pasalnya danau yang dilengkapi dengan pulau kecil di tengahnya ini dikenal memiliki pemandangan matahari tenggelamnya yang indah. Dengan lokasinya yang bisa ditempuh hanya dalam waktu 4 jam perjalanan menggunakan rute Medan – Parapat atau Medan – Brastagi, Danau Toba pada 2016 diharapkan mampu menarik kunjungan wisata yang lebih banyak.
         * photo : http://cdn0-a.production.liputan6.static6.com/medias/1020657/big/077643200_1444871277-3.jpg
         */

        private String title;
        private String desc;
        private String photo;
    }
}
