package com.plugin.arview.location;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.plugin.arview.activity.EyeViewActivity;
import com.plugin.arview.augmentedreality.AppuntaConstants;
import com.plugin.arview.augmentedreality.appunta.android.location.LocationFactory;
import com.plugin.arview.augmentedreality.appunta.android.point.Point;

import java.util.List;

public class LocationHelper {

    public void setLocationListener(final Location currentLocation, LocationManager locationManager, final EyeViewActivity activity) {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                activity.setCurrentLocation(location);
                activity.getEyeView().setPosition(LocationFactory.createLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), currentLocation.getAltitude()));
                activity.getRadarView().setPosition(LocationFactory.createLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), currentLocation.getAltitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
    }

    public boolean setLocationListenerMock(Location currentLocation, List<Point> eyeViewPoints, TextView tvLocationOutput, Activity activity) {
        currentLocation.setLatitude(AppuntaConstants.DEFAULT_LATITUDE);
        currentLocation.setLongitude(AppuntaConstants.DEFAULT_LONGITUDE);
        currentLocation.setAltitude(AppuntaConstants.DEFAULT_ALTITUDE);

        for (Point point : eyeViewPoints)
            if (point.getLocation().distanceTo(currentLocation) < 800) {
                //tvLocationOutput.setVisibility(View.VISIBLE);
                tvLocationOutput.setVisibility(View.GONE);
                tvLocationOutput.setText("Location: " + point.getName() + " found. Take a picture");
                return true;
            }
        tvLocationOutput.setVisibility(View.GONE);
        return false;
    }

}
