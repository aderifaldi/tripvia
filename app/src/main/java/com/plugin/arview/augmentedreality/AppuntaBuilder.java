package com.plugin.arview.augmentedreality;

import android.app.Activity;

import com.plugin.arview.activity.EyeViewActivity;
import com.plugin.arview.augmentedreality.appunta.android.location.LocationFactory;
import com.plugin.arview.augmentedreality.appunta.android.orientation.OrientationManager;
import com.plugin.arview.augmentedreality.appunta.android.point.Point;
import com.plugin.arview.augmentedreality.appunta.android.ui.AppuntaView;
import com.plugin.arview.augmentedreality.appunta.android.ui.EyeView;
import com.plugin.arview.augmentedreality.appunta.android.ui.RadarView;
import com.playground.skypass.app.util.GlobalVariable;

import java.util.List;

public class AppuntaBuilder {

    public static final int MAX_DISTANCE = 5000; // 10 KM

    private Activity activity;
    private OrientationManager compass;
    private EyeView eyeView;
    private RadarView radarView;
    private List<Point> eyeViewPoints;
    private List<Point> radarPoints;

    public AppuntaBuilder activity(EyeViewActivity activity) {
        this.activity = activity;
        return this;
    }

    public AppuntaBuilder compass(OrientationManager compass) {
        this.compass = compass;
        return this;
    }

    public AppuntaBuilder eyeView(EyeView eyeView) {
        this.eyeView = eyeView;
        return this;
    }

    public AppuntaBuilder radarView(RadarView radarView) {
        this.radarView = radarView;
        return this;
    }

    public AppuntaBuilder eyeViewPoints(List<Point> eyeViewPoints) {
        this.eyeViewPoints = eyeViewPoints;
        return this;
    }

    public AppuntaBuilder radarPoints(List<Point> radarPoints) {
        this.radarPoints = radarPoints;
        return this;
    }

    public void build() {
        compass.setAxisMode(OrientationManager.MODE_AR);
        compass.setOnOrientationChangeListener((OrientationManager.OnOrientationChangedListener) activity);
        compass.startSensor(activity);

        eyeView.setMaxDistance(MAX_DISTANCE);
        radarView.setMaxDistance(MAX_DISTANCE);
        eyeView.setOnPointPressedListener((AppuntaView.OnPointPressedListener) activity);
        radarView.setOnPointPressedListener((AppuntaView.OnPointPressedListener) activity);

        eyeView.setPoints(eyeViewPoints);
        eyeView.setPosition(LocationFactory.createLocation(GlobalVariable.getLocation(activity).getLatitude(),
                GlobalVariable.getLocation(activity).getLongitude(),
                AppuntaConstants.DEFAULT_ALTITUDE));
        eyeView.setOnPointPressedListener((AppuntaView.OnPointPressedListener) activity);
        radarView.setPoints(radarPoints);
        radarView.setPosition(LocationFactory.createLocation(GlobalVariable.getLocation(activity).getLatitude(),
                GlobalVariable.getLocation(activity).getLongitude(),
                AppuntaConstants.DEFAULT_ALTITUDE));
        //radarView.setRotableBackground(R.drawable.arrow);
    }


}
