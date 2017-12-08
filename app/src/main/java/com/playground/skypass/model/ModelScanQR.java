package com.playground.skypass.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by RadyaLabs PC on 15/09/2017.
 */

@NoArgsConstructor
@Data
public class ModelScanQR {


    /**
     * transaction_id : 26733E40-1F2
     * merchant_id : 0123214
     * discount : 0
     * amount : 200000
     */

    private String transaction_id;
    private String merchant_id;
    private int discount;
    private int amount;
}
