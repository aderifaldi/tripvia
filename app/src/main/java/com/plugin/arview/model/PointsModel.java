package com.plugin.arview.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.playground.skypass.model.ModelMonster;
import com.plugin.arview.augmentedreality.appunta.android.location.LocationFactory;
import com.plugin.arview.augmentedreality.appunta.android.point.Point;
import com.plugin.arview.augmentedreality.appunta.android.point.impl.SimplePoint;
import com.plugin.arview.augmentedreality.appunta.android.point.renderer.PointRenderer;
import com.playground.skypass.app.util.image.ImageDownloader;
import com.radyalabs.irfan.util.AppUtility;

import java.util.ArrayList;
import java.util.List;

public class PointsModel {

    public static List<Point> getPoints(final Context context, final PointRenderer renderer, ArrayList<ModelMonster.DataBean> property) {
        final List<Point> points = new ArrayList<Point>();

        for (int i = 0; i < property.size(); i ++){
            final ModelMonster.DataBean data = property.get(i);

            String imageURL = data.getImage();

            ImageDownloader imageDownloader = new ImageDownloader(imageURL, context, new ImageDownloader.OnImageFinishDownload() {
                @Override
                public void onFinish(Bitmap bitmap, int returnCode) {
                    if (bitmap != null) {

                        Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, AppUtility.dpToPx(114, context), AppUtility.dpToPx(114, context), false);

                        bitmap.recycle();

                        points.add(new SimplePoint(""+data.getId(), LocationFactory.createLocation(data.getLatitude(), data.getLongitude(),
                                0),  renderer, data.getTitle(), data.getDescription(), data.getXp(), data.getPoints(),
                                data, imageBitmap, data.getMinimum_geofence(), data.getImage(), data.getType()));

                    }
                }
            });
            imageDownloader.setSizeOption(128, true);
            imageDownloader.execute();

        }

        return points;
    }
}
