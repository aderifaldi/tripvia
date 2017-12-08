package com.playground.skypass.app.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.playground.skypass.model.ModelPoiAll;
import com.playground.skypass.model.ModelPoiDetail;
import com.playground.skypass.model.ModelPoiSearch;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by RadyaLabs PC on 07/12/2017.
 */

public class JsonData {

    public static ModelPoiAll getPoiAll(Context context){
        ModelPoiAll data = null;
        JsonObject object;
        JsonObject json;
        String rawContent;
        Gson gson;
        GsonBuilder gsonBuilder;
        String jsonString;

        try {
            InputStream is = context.getAssets().open("json/get_poi_all.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        rawContent = new String(jsonString);
        json = new JsonParser().parse(rawContent).getAsJsonObject();
        object = json.getAsJsonObject();

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        data = gson.fromJson(object, ModelPoiAll.class);

        return data;
    }

    public static ModelPoiDetail getPoiDetail(Context context){
        ModelPoiDetail data = null;
        JsonObject object;
        JsonObject json;
        String rawContent;
        Gson gson;
        GsonBuilder gsonBuilder;
        String jsonString;

        try {
            InputStream is = context.getAssets().open("json/get_poi_detail.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        rawContent = new String(jsonString);
        json = new JsonParser().parse(rawContent).getAsJsonObject();
        object = json.getAsJsonObject();

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        data = gson.fromJson(object, ModelPoiDetail.class);

        return data;
    }

    public static ModelPoiSearch getPoiSearch(Context context){
        ModelPoiSearch data = null;
        JsonObject object;
        JsonObject json;
        String rawContent;
        Gson gson;
        GsonBuilder gsonBuilder;
        String jsonString;

        try {
            InputStream is = context.getAssets().open("json/get_poi_search.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        rawContent = new String(jsonString);
        json = new JsonParser().parse(rawContent).getAsJsonObject();
        object = json.getAsJsonObject();

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        data = gson.fromJson(object, ModelPoiSearch.class);

        return data;
    }

}
