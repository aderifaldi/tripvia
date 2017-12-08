package com.playground.skypass.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelBase;
import com.playground.skypass.model.ModelCheckIn;
import com.playground.skypass.model.ModelEvent;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.xp)
    TextView xp;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.dateEnd)
    TextView dateEnd;

    private ModelEvent.Data tEvent;
    private Bundle extras;
    private JsonObject jsonObject;
    private SimpleDateFormat sdf;

    private SupportMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        ButterKnife.bind(this);

        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        sdf = new SimpleDateFormat("dd/MMM/yyyy");

        extras = getIntent().getExtras();
        tEvent = (ModelEvent.Data) extras.getSerializable("data");

        Glide.with(this).
                load(tEvent.getImage()).
                centerCrop().
                crossFade().
                into(img);

        title.setText(tEvent.getTitle());
        desc.setText(tEvent.getDescription());
        point.setText("" + tEvent.getPoints() + " Points");
        xp.setText("" + tEvent.getXp() + " Xp");
        date.setText(sdf.format(tEvent.getStart_date()));
        dateEnd.setText(sdf.format(tEvent.getEnd_date()));

    }

    @OnClick(R.id.back)
    protected void back() {
        onBackPressed();
        backAnimation();
    }

    @OnClick(R.id.btnDirection)
    protected void btnDirection(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + GlobalVariable.DEFAULT_LATITUDE + "," +
                        GlobalVariable.DEFAULT_LONGITUDE + "&daddr=" + tEvent.getLatitude() + "," +
                        tEvent.getLongitude()));
        startActivity(intent);
    }

    @OnClick(R.id.check_in)
    protected void checkIn() {
//        insertCheckIn(tEvent);

        showLoading();
        jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getApplicationContext()));
        jsonObject.addProperty("event_id", tEvent.getId());
        jsonObject.addProperty("image", tEvent.getImage());
        jsonObject.addProperty("title", tEvent.getTitle());

        Call<ModelCheckIn> call = apiService.checkIn(jsonObject);
        call.enqueue(new Callback<ModelCheckIn>() {
            @Override
            public void onResponse(Call<ModelCheckIn> call, Response<ModelCheckIn> response) {
//                dismissLoading();

                if (!response.body().isError()) {
//                    showMessage(response.body().getAlerts().getMessage());
                    jsonObject = new JsonObject();
                    jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getApplicationContext()));
                    jsonObject.addProperty("total_xp", tEvent.getXp());
                    jsonObject.addProperty("total_points", tEvent.getPoints());

                    Call<ModelBase> call2 = apiService.updatePoint(jsonObject);
                    call2.enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                            dismissLoading();
                            if (!response.body().isError()){
                                Toast.makeText(EventDetailActivity.this, response.body().getAlerts().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable t) {
                            dismissLoading();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ModelCheckIn> call, Throwable t) {
                dismissLoading();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(tEvent.getLatitude(), tEvent.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location)); //.icon(BitmapDescriptorFactory.fromResource(R.drawable.places))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.8f), 2000, null);
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
//            mobileServiceUserEvent = mobileServiceClient.getTable(TUserEvent.class);
//            mobileServiceUserPoint = mobileServiceClient.getTable(TUserPoint.class);
//        } catch (MalformedURLException e) {
//            AppUtility.logD("RecipeDetail", "There was an error creating the Mobile Service. Verify the URL");
//        }
//    }
//
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
//
//    public void insertCheckIn(TEvent item){
//        try {
//            double jarak = AppUtility.getJarak(GlobalVariable.getLocation(getApplicationContext()).getLatitude(),
//                    GlobalVariable.getLocation(getApplicationContext()).getLongitude(),
//                    item.getLatitude(), item.getLongitude());
//
//            final TEvent finalItem = item;
//            final TUserEvent userEvent = new TUserEvent();
//            userEvent.setUser_id(GlobalVariable.getUserEmail(getApplicationContext()));
//            userEvent.setEvent_id(finalItem.getId());
//            userEvent.setTitle(finalItem.getTitle());
//            userEvent.setImage(finalItem.getImage());
//
//            new AsyncTask<Void, Void, Void>() {
//
//                @Override
//                protected Void doInBackground(Void... params) {
//                    try {
//                        mobileServiceUserEvent.insert(userEvent).get();
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//
//                                AppUtility.logD("MainActivity", "add comment Success");
//
//                                dismissLoading();
////                                updatePoint(tEvent);
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
//    private void updatePoint(TEvent tEvent){
//
//        final TEvent finalItem = tEvent;
//        final TUserPoint userPoint = new TUserPoint();
//
//        userPoint.setUser_id(GlobalVariable.getUserEmail(getApplicationContext()));
//        userPoint.setTotal_point(finalItem.getPoints());
//        userPoint.setTotal_xp(finalItem.getXp());
//
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    mobileServiceUserPoint.insert(userPoint).get();
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            AppUtility.logD("MainActivity", "add comment Success");
//
//                            dismissLoading();
//                        }
//                    });
//                } catch (Exception exception) {
//                    AppUtility.logD("MainActivity", "add comment Error : " + exception.toString());
//                }
//                return null;
//            }
//        }.execute();
//
//    }

}
