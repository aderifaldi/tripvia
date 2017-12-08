package com.plugin.arview.ui.viewholders;

import android.app.Activity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.plugin.arview.augmentedreality.appunta.android.ui.CameraView;
import com.plugin.arview.augmentedreality.appunta.android.ui.EyeView;
import com.plugin.arview.augmentedreality.appunta.android.ui.RadarView;
import com.playground.skypass.R;

public class EyeViewActivityViewHolder {
    public EyeView eyeView;
    public RadarView radarView;
    public CameraView cameraView;
    public FrameLayout cameraFrame;
    public TextView tvLocationOutput;

    public EyeViewActivityViewHolder(Activity activity) {
        this.eyeView = (EyeView) activity.findViewById(R.id.augmentedView);
        this.radarView = (RadarView) activity.findViewById(R.id.radarView);
        this.tvLocationOutput = (TextView) activity.findViewById(R.id.tv_location_output);
        this.cameraFrame = (FrameLayout) activity.findViewById(R.id.cameraFrame);
    }
}
