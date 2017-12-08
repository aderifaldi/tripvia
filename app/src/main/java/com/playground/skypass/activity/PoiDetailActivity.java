package com.playground.skypass.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playground.skypass.R;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.app.util.JsonData;
import com.playground.skypass.model.ModelPoiDetail;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoiDetailActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.pageTitle)
    TextViewAutoStyle pageTitle;

    private ModelPoiDetail poiDetail;
    private SupportMapFragment map;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_poi);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);

        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        poiDetail = JsonData.getPoiDetail(getApplicationContext());

        Glide.with(this).
                load(poiDetail.getData().getPhoto()).
                centerCrop().
                crossFade().
                into(img);

        pageTitle.setText(poiDetail.getData().getTitle());
        title.setText(poiDetail.getData().getAddress());
        desc.setText(poiDetail.getData().getFacility());

    }

    private void getPoiDetail(){
        showLoading();
        Call<ModelPoiDetail> call = apiService.poiDetail(id);
        call.enqueue(new Callback<ModelPoiDetail>() {
            @Override
            public void onResponse(Call<ModelPoiDetail> call, Response<ModelPoiDetail> response) {
                dismissLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        poiDetail = response.body();

                        Glide.with(getApplicationContext()).
                                load(poiDetail.getData().getPhoto()).
                                centerCrop().
                                crossFade().
                                into(img);

                        pageTitle.setText(poiDetail.getData().getTitle());
                        title.setText(poiDetail.getData().getAddress());
                        desc.setText(poiDetail.getData().getFacility());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelPoiDetail> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.back)
    protected void back() {
        onBackPressed();
        backAnimation();
    }

    @OnClick(R.id.btnDirection)
    protected void btnDirection(){
        startActivity(new Intent(getApplicationContext(), PoiNearbyActivity.class)
                .putExtra("id", id));
    }

    @OnClick(R.id.getDirection)
    protected void getDirection(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + GlobalVariable.getLocation(getApplicationContext()).getLatitude() + "," +
                        GlobalVariable.getLocation(getApplicationContext()).getLongitude() + "&daddr="
                        + poiDetail.getData().getLocation().getLatitude()
                        + ","
                        + poiDetail.getData().getLocation().getLongitude()));
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(poiDetail.getData().getLocation().getLatitude(), poiDetail.getData().getLocation().getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location)); //.icon(BitmapDescriptorFactory.fromResource(R.drawable.places))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.8f), 2000, null);
    }
}
