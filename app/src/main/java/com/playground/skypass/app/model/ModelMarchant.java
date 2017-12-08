package com.playground.skypass.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 31-Jul-16.
 */
public class ModelMarchant implements Serializable {

    private Status status;
    private MarchantResult result;

    public Status getStatus() {
        return status;
    }

    public MarchantResult getResult() {
        return result;
    }

}
