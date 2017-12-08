package com.plugin.arview.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.activity.BaseActivity;
import com.playground.skypass.app.dialog.MonsterCatchedDialog;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelBase;
import com.playground.skypass.model.ModelMonster;
import com.plugin.arview.augmentedreality.AppuntaBuilder;
import com.plugin.arview.augmentedreality.appunta.android.orientation.Orientation;
import com.plugin.arview.augmentedreality.appunta.android.orientation.OrientationManager;
import com.plugin.arview.augmentedreality.appunta.android.point.Point;
import com.plugin.arview.augmentedreality.appunta.android.point.renderer.PointRenderer;
import com.plugin.arview.augmentedreality.appunta.android.point.renderer.impl.EyeViewRenderer;
import com.plugin.arview.augmentedreality.appunta.android.point.renderer.impl.SimplePointRenderer;
import com.plugin.arview.augmentedreality.appunta.android.ui.AppuntaView.OnPointPressedListener;
import com.plugin.arview.augmentedreality.appunta.android.ui.CameraView;
import com.plugin.arview.augmentedreality.appunta.android.ui.EyeView;
import com.plugin.arview.augmentedreality.appunta.android.ui.RadarView;
import com.plugin.arview.location.LocationHelper;
import com.plugin.arview.model.PointsModel;
import com.plugin.arview.recognizeim.RecognizeImHelper;
import com.plugin.arview.task.TakePictureTask;
import com.plugin.arview.ui.DialogHelper;
import com.plugin.arview.ui.viewholders.EyeViewActivityViewHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EyeViewActivity extends BaseActivity implements OrientationManager.OnOrientationChangedListener, OnPointPressedListener,
        GestureDetector.OnGestureListener, SensorEventListener {

    //    private MobileServiceClient mobileServiceClient;
//    private MobileServiceTable<TUserMonster> mobileServiceTable;
//    private MobileServiceTable<Tmonster> mobileServiceTableMonster;
    public static final String LOG_TAG = "EyeViewActivity";
    private static final String EMPTY_STRING = "";
    private boolean pointIsNearEnough = false;

    private EyeViewActivityViewHolder viewHolder;

    private List<Point> eyeViewPoints;
    private List<Point> radarPoints;
    private ArrayList<ModelMonster.DataBean> monster;

    private OrientationManager compass;
    private LocationManager locationManager;
    private Location currentLocation;
    private LocationHelper locationHelper;

    private GestureDetector gestureDetector;

    private RecognizeImHelper recognizeImHelper;
    private DialogHelper dialogHelper;
    private ProgressDialog waitDialog;
    private Bitmap imgBitmap;

    private Bundle extras;

    private MonsterCatchedDialog monsterCatchedDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyeview);
//        setupAzureService();
        extras = getIntent().getExtras();
        monster = (ArrayList<ModelMonster.DataBean>) extras.getSerializable("data");

        try {
            cekCompatibility();
        } catch (Exception e) {
        }

    }
//    public void setupAzureService() {
//        try {
//            // Create the Mobile Service Client instance, using the provided
//            // Mobile Service URL and key
//            mobileServiceClient = new MobileServiceClient(
//                    GlobalVariable.AZURE_SERVICE_URL,
//                    GlobalVariable.AZURE_SERVICE_KEY,
//                    this).withFilter(new ProgressFilter());
//
//            // Get the Mobile Service Table instance to use
//            mobileServiceTable = mobileServiceClient.getTable(TUserMonster.class);
//            mobileServiceTableMonster = mobileServiceClient.getTable(Tmonster.class);
//        } catch (MalformedURLException e) {
//            AppUtility.logD("RecipeDetail", "There was an error creating the Mobile Service. Verify the URL");
//        }
//    }
//    private class ProgressFilter implements ServiceFilter {
//
//        @Override
//        public ListenableFuture<ServiceFilterResponse> handleRequest(
//                ServiceFilterRequest request, NextServiceFilterCallback next) {
//
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    showLoading();
//                }
//            });
//
//            SettableFuture<ServiceFilterResponse> result = SettableFuture.create();
//            try {
//                ServiceFilterResponse response = next.onNext(request).get();
//                result.set(response);
//            } catch (Exception exc) {
//                result.setException(exc);
//            }
//
//            dismissProgressBar();
//            return result;
//        }
//
//        private void dismissProgressBar() {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    dismissLoading();
//                }
//            });
//        }
//    }

    private void loadData() {
        initializeAndsetPoints();
        initializeViewHolder();
        initializeProgressDialog();
        initializeGestureDetector();
        initializeCompass();
        initializeAugmentedReality();
        initializeCamera();
        initializeLocationHelper();
        initializeRecognizeImHelper();
        initializeCurrentLocation();
        initializeLocationManager();
        initializeLocationListenerMock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SensorManager sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        boolean hasAccelorometer = sensors.registerListener(EyeViewActivity.this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean hasCompass = sensors.registerListener(EyeViewActivity.this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);

        if (hasAccelorometer && hasCompass) {
            compass.stopSensor();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onStart();
        SensorManager sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        boolean hasAccelorometer = sensors.registerListener(EyeViewActivity.this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean hasCompass = sensors.registerListener(EyeViewActivity.this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);

        if (hasAccelorometer && hasCompass) {
            compass.stopSensor();
        }
    }

    private void initializeViewHolder() {
        viewHolder = new EyeViewActivityViewHolder(this);
    }

    private void initializeGestureDetector() {
        gestureDetector = new GestureDetector(this, this);
    }

    private void initializeLocationManager() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    private void initializeCompass() {
        compass = new OrientationManager(this);
    }

    private void initializeAndsetPoints() {
        PointRenderer eyeViewRenderer = new EyeViewRenderer(getResources(), getApplicationContext(), imgBitmap);
        eyeViewPoints = PointsModel.getPoints(getApplicationContext(), eyeViewRenderer, monster);
        radarPoints = PointsModel.getPoints(getApplicationContext(), new SimplePointRenderer(), monster);
    }

    private void initializeAugmentedReality() {
        AppuntaBuilder appuntaBuilder = new AppuntaBuilder();
        appuntaBuilder.activity(this).compass(compass).eyeView(viewHolder.eyeView).radarView(viewHolder.radarView).eyeViewPoints(eyeViewPoints).radarPoints(radarPoints).build();
    }

    private void initializeCamera() {
        viewHolder.cameraView = new CameraView(this);
        viewHolder.cameraFrame.addView(viewHolder.cameraView);
    }

    private void initializeCurrentLocation() {
        currentLocation = new Location(EMPTY_STRING);
    }

    private void initializeProgressDialog() {
        dialogHelper = new DialogHelper(this, waitDialog);
    }

    private void initializeLocationHelper() {
        locationHelper = new LocationHelper();
    }

    private void initializeRecognizeImHelper() {
        recognizeImHelper = new RecognizeImHelper(this);
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public EyeView getEyeView() {
        return viewHolder.eyeView;
    }

    public RadarView getRadarView() {
        return viewHolder.radarView;
    }

    public CameraView getCameraView() {
        return viewHolder.cameraView;
    }

    public DialogHelper getDialogHelper() {
        return dialogHelper;
    }

    private void initializeLocationListener() {
        locationHelper.setLocationListener(currentLocation, locationManager, this);
    }

    private void initializeLocationListenerMock() {
        this.pointIsNearEnough = locationHelper.setLocationListenerMock(currentLocation, eyeViewPoints, viewHolder.tvLocationOutput, this);
    }

    private void executeTakePictureTask() {
        new TakePictureTask(recognizeImHelper).execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onOrientationChanged(Orientation orientation) {
        viewHolder.eyeView.setOrientation(orientation);
        viewHolder.eyeView.setPhoneRotation(OrientationManager.getPhoneRotation(this));
        viewHolder.radarView.setOrientation(orientation);
    }

    @Override
    public void onPointPressed(Point point) {

        final Point data = point;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getApplicationContext()));
        jsonObject.addProperty("monster_id", point.getId());

        showLoading();
        Call<ModelBase> call = apiService.catchMonster(jsonObject);
        call.enqueue(new Callback<ModelBase>() {
            @Override
            public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                dismissLoading();
                if (!response.body().isError()){
                    monsterCatchedDialog = new MonsterCatchedDialog(data.getImageUrl(),
                            data.getTitle(), response.body().getAlerts().getMessage());
                    monsterCatchedDialog.show(getSupportFragmentManager(), "");
                }

            }

            @Override
            public void onFailure(Call<ModelBase> call, Throwable t) {
                dismissLoading();
            }
        });

//        Intent intent = new Intent(getApplicationContext(), PropertyDetailActivity.class);
//        intent.putExtra("item", point.getData());
//        startActivity(intent);
//        //finish();
//        overridePendingTransition(R.anim.left_in, R.anim.left_out);

//        if (point.getType() == 4){
//            startActivity(new Intent(getApplicationContext(), QuizActivity.class));
//            overridePendingTransition(R.anim.left_in, R.anim.left_out);
//        }else {
//            final String name = point.getTitle();
//            final String imageUrl = point.getImageUrl();
//            final String id = point.getId();
//
//            try{
//
//                double jarak = AppUtility.getJarak(GlobalVariable.getLocation(this).getLatitude(), GlobalVariable.getLocation(this).getLongitude(), point.getLocation().getLatitude(), point.getLocation().getLongitude());
//
//                if((jarak*1000) < point.getGeofence()){
//                    final TUserMonster userMonster = new TUserMonster();
//                    userMonster.setUser_id(GlobalVariable.getUserEmail(getApplicationContext()));
//                    userMonster.setMonster_id(point.getId());
//                    userMonster.setTitle(point.getTitle());
//                    userMonster.setImage(point.getImageUrl());
//
//                    new AsyncTask<Void, Void, Void>() {
//
//                        @Override
//                        protected Void doInBackground(Void... params) {
//                            try {
//                                mobileServiceTable.insert(userMonster).get();
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        AppUtility.logD("MainActivity", "add comment Success");
//
//                                        dismissLoading();
//
//                                        getMonsterById(id);
//                                    }
//                                });
//                            } catch (Exception exception) {
//                                AppUtility.logD("MainActivity", "add comment Error : " + exception.toString());
//                            }
//                            return null;
//                        }
//                    }.execute();
//
//
//                }else{
//                    double distance = round((jarak*1000)-point.getGeofence(),2);
//                    Toast.makeText(this, "You are still too far from "+ name + "Continue Walk about "+ distance + " meters.",
//                            Toast.LENGTH_SHORT).show();
//                }
//
//                AppuntaUtils.unselectAllPoints(eyeViewPoints);
//                point.setSelected(true);
//            }catch (Exception ex){
//                //
//            }
//        }


    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

//    public void getMonsterById(String id){
//        final String idMonster = id;
//        try{
//            new AsyncTask<Void, Void, Void>() {
//
//                @Override
//                protected Void doInBackground(Void... params) {
//                    try {
//                        final MobileServiceList<Tmonster> result = mobileServiceTableMonster.where().field("id").eq(idMonster).top(1).execute().get();
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                AppUtility.logD("MainActivity", "get item by id");
//                                Tmonster monster = new Tmonster();
//                                if(result.getTotalCount()>0){
//                                    for(Tmonster item : result){
//                                        monster = item;
//                                    }
//                                }
//                                UpdateMonsterQuota(monster);
//                            }
//                        });
//                    } catch (Exception exception) {
//                        AppUtility.logD("MainActivity", "add comment Error : " + exception.toString());
//                    }
//                    return null;
//                }
//            }.execute();
//        }catch (Exception ex){
//            //
//        }
//    }
//
//    public void UpdateMonsterQuota(Tmonster item){
//        final Tmonster monster = item;
//        try{
//            new AsyncTask<Void, Void, Void>() {
//
//                @Override
//                protected Void doInBackground(Void... params) {
//                    try {
//                        mobileServiceTableMonster.update(monster).get();
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                AppUtility.logD("MainActivity", "get item by id");
//                                dismissLoading();
//
//                                monsterCatchedDialog = new MonsterCatchedDialog(monster.getImage(), monster.getTitle());
//                                monsterCatchedDialog.show(getSupportFragmentManager(), "");
//                            }
//                        });
//                    } catch (Exception exception) {
//                        AppUtility.logD("MainActivity", "add comment Error : " + exception.toString());
//                    }
//                    return null;
//                }
//            }.execute();
//        }catch (Exception ex){
//            //
//        }
//    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(LOG_TAG, "onBackPressed - Glass: swipe down");
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(LOG_TAG, "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(LOG_TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(LOG_TAG, "onSingleTapUp - Glass: tap");
        if (pointIsNearEnough) {
            executeTakePictureTask();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(LOG_TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("Gesture Example", "onFling: velocityX:" + velocityX + " velocityY:" + velocityY);
        if (velocityX < -3500) {
            Log.d(LOG_TAG, "Fling Right - Glass: swipe right");
        } else if (velocityX > 3500) {
            Log.d(LOG_TAG, "Fling Left - Glass: swipe left");
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_MENU && pointIsNearEnough) {
            executeTakePictureTask();
            return true;
        }
        return super.onKeyDown(keycode, event);
    }

    private void cekCompatibility() {
        SensorManager sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        boolean hasAccelorometer = sensors.registerListener(EyeViewActivity.this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        boolean hasCompass = sensors.registerListener(EyeViewActivity.this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);

        if (hasAccelorometer && hasCompass) {
            loadData();
        } else {
            if (!hasAccelorometer && !hasCompass) {
                showDialog("Perangkat Anda tidak mendukung untuk fitur ini.");
            } else if (!hasAccelorometer) {
                showDialog("Perangkat Anda tidak mendukung untuk fitur ini.");
            } else if (!hasCompass) {
                showDialog("Perangkat Anda tidak mendukung untuk fitur ini.");
            }
        }

    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setTitle("Augmented Reality");
        builder.setMessage(message);
        builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });

        //builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recognizeImHelper.onActivityResultCalled(requestCode, resultCode, data);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
