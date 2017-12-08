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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.playground.skypass.R;
import com.playground.skypass.app.marker.PicassoMarker;
import com.playground.skypass.app.model.Tmonster;
import com.playground.skypass.app.util.GlobalVariable;
import com.plugin.arview.activity.EyeViewActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class ChallangeFragment extends BaseFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private ArrayList<PicassoMarker> arrayMarker;
    //    private MobileServiceClient mobileServiceClient;
//    private MobileServiceTable<Tmonster> mobileServiceTable;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private ArrayList<Tmonster> tmonsters;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_challange, container, false);

        ButterKnife.bind(this, view);

        arrayMarker = new ArrayList<>();

//        setupAzureService();

        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        setupGoogleApiClient();
        initMaps();

//        loadMonster();

        return view;
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

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

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
//                    getActivity()).withFilter(new ProgressFilter());
//
//            // Get the Mobile Service Table instance to use
//            mobileServiceTable = mobileServiceClient.getTable(Tmonster.class);
//        } catch (MalformedURLException e) {
//            AppUtility.logD("RecipeDetail", "There was an error creating the Mobile Service. Verify the URL");
//        }
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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

//    private class ProgressFilter implements ServiceFilter {
//
//        @Override
//        public ListenableFuture<ServiceFilterResponse> handleRequest(
//                ServiceFilterRequest request, NextServiceFilterCallback next) {
//
//            getActivity().runOnUiThread(new Runnable() {
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
//            getActivity().runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    dismissLoading();
//                }
//            });
//        }
//    }

//    private void loadMonster(){
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    final MobileServiceList<Tmonster> result = mobileServiceTable.execute().get();
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            AppUtility.logD("MainActivity", "get monster Success");
//
//                            tmonsters = new ArrayList<>();
//                            tmonsters.clear();
//
//                            LatLng centerPosition;
//                            centerPosition = new LatLng(GlobalVariable.getLocation(getActivity()).getLatitude(),
//                                    GlobalVariable.getLocation(getActivity()).getLongitude());
//
//                            CameraPosition newCamPos = new CameraPosition(centerPosition,
//                                    17,
//                                    mMap.getCameraPosition().tilt, //use old tilt
//                                    mMap.getCameraPosition().bearing); //use old bearing
//                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 2000, null);
//
//                            for (Tmonster item : result) {
//                                tmonsters.add(item);
//                            }
//
//                            for (int i = 0; i < tmonsters.size(); i++){
//                                LatLng propertyLocation = new LatLng(tmonsters.get(i).getLatitude(),
//                                        tmonsters.get(i).getLongitude());
//
//                                MarkerOptions markerOne = new MarkerOptions().
//                                        position(propertyLocation).
//                                        title(tmonsters.get(i).getTitle());
//
//                                PicassoMarker target = new PicassoMarker(mMap.addMarker(markerOne));
//                                Picasso.with(getActivity()).
//                                        load(tmonsters.get(i).getImage()).
//                                        resize(72, 72).
//                                        into(target);
//
//                                arrayMarker.add(target);
//
//                            }
//
//                            for (PicassoMarker marker : arrayMarker){
//                                marker.setVisible(true);
//                            }
//
//                        }
//                    });
//                } catch (Exception exception) {
//                    AppUtility.logD("MainActivity", "get comment Error");
//                }
//                return null;
//            }
//        }.execute();
//    }

    @OnClick(R.id.ar_view)
    protected void goToAR() {

        Intent intent = new Intent(getActivity(), EyeViewActivity.class);
        intent.putExtra("data", tmonsters);
        startActivity(intent);

        Toast.makeText(getActivity(), "Go To AR View", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}