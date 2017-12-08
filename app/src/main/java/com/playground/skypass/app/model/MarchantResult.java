package com.playground.skypass.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 31-Jul-16.
 */
public class MarchantResult implements Serializable{

    private String total;
    private MarchantData[] data;
    private String note;
    private int zoom;

    public int getZoom() {
        return zoom;
    }

    public String getNote() {
        return note;
    }

    public String getTotal() {
        return total;
    }

    public MarchantData[] getData() {
        return data;
    }
}
