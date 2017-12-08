package com.playground.skypass.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.playground.skypass.R;
import com.playground.skypass.app.marker.PicassoMarker;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelMonster;
import com.plugin.arview.activity.EyeViewActivity;
import com.radyalabs.irfan.util.AppUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class MonsterFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CAMERA = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private ArrayList<ModelMonster.DataBean> tmonsters;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<PicassoMarker> arrayMarker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monster, container, false);

        ButterKnife.bind(this, view);

        arrayMarker = new ArrayList<>();

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        setupGoogleApiClient();
        initMaps();

        loadMonster();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showARView();
                } else {
                    showARView();
                }
            }
        }
    }

    @OnClick(R.id.search)
    protected void search() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > 22) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            } else {
                showARView();
            }
        } else {
            showARView();
        }
    }

    private void showARView() {
        Intent intent = new Intent(getActivity(), EyeViewActivity.class);
        intent.putExtra("data", tmonsters);
        startActivity(intent);
        Toast.makeText(getActivity(), "Let's catch monster!", Toast.LENGTH_SHORT).show();
    }

    private void loadMonster() {

        showLoading();
        Call<ModelMonster> call = apiService.getMonster();
        call.enqueue(new Callback<ModelMonster>() {
            @Override
            public void onResponse(Call<ModelMonster> call, Response<ModelMonster> response) {
                AppUtility.logD("MainActivity", "get monster Success");
                dismissLoading();

                tmonsters = new ArrayList<>();
                tmonsters.clear();

                LatLng centerPosition;
                centerPosition = new LatLng(GlobalVariable.getLocation(getActivity()).getLatitude(),
                        GlobalVariable.getLocation(getActivity()).getLongitude());

                CameraPosition newCamPos = new CameraPosition(centerPosition,
                        15,
                        mMap.getCameraPosition().tilt, //use old tilt
                        mMap.getCameraPosition().bearing); //use old bearing
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 2000, null);

                for (ModelMonster.DataBean item : response.body().getData()) {
                    tmonsters.add(item);
                }

                for (int i = 0; i < tmonsters.size(); i++) {
                    LatLng propertyLocation = new LatLng(tmonsters.get(i).getLatitude(),
                            tmonsters.get(i).getLongitude());

                    MarkerOptions markerOne = new MarkerOptions().
                            position(propertyLocation).
                            title(tmonsters.get(i).getTitle());

                    PicassoMarker target = new PicassoMarker(mMap.addMarker(markerOne));
                    Picasso.with(getActivity()).
                            load(tmonsters.get(i).getImage()).
                            resize(72, 72).
                            into(target);

                    arrayMarker.add(target);

                }

                for (PicassoMarker marker : arrayMarker) {
                    marker.setVisible(true);
                }
            }

            @Override
            public void onFailure(Call<ModelMonster> call, Throwable t) {
                dismissLoading();
            }
        });

    }

    private void setupGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void initMaps() {
        mapFragment.getMapAsync(this);

        mMap = mapFragment.getMap();
        try {
            mMap.getUiSettings().setMapToolbarEnabled(false);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

//            mMap.setMyLocationEnabled(true);
//            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        } catch (Exception e) {

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            double dLatitude = mLastLocation.getLatitude();
            double dLongitude = mLastLocation.getLongitude();

            if (this != null) {
                GlobalVariable.saveLocation(getActivity(), GlobalVariable.DEFAULT_LATITUDE, GlobalVariable.DEFAULT_LONGITUDE);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
