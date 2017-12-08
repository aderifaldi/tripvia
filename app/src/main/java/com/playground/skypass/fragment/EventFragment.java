package com.playground.skypass.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.activity.EventDetailActivity;
import com.playground.skypass.app.adapter.EventAdapter;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelBase;
import com.playground.skypass.model.ModelEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class EventFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.pay)
    FloatingActionButton pay;

    private EventAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        ButterKnife.bind(this, view);

        showLoading();

        pay.setVisibility(View.GONE);

        adapter = new EventAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadEvent();

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelEvent.Data tEvent = adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra("data", tEvent);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        registerNotification();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        return view;
    }

    private void registerNotification() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getActivity()));
        jsonObject.addProperty("token", GlobalVariable.getGCMToken(getActivity()));
        jsonObject.addProperty("platform", "android");

        Call<ModelBase> call = apiService.registerPushNotif(jsonObject);
        call.enqueue(new Callback<ModelBase>() {
            @Override
            public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        GlobalVariable.saveIsRegisterPush(getActivity(), true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelBase> call, Throwable t) {

            }
        });

    }

    private void loadEvent() {
//        showLoading();

        Call<ModelEvent> call = apiService.getEvent();
        call.enqueue(new Callback<ModelEvent>() {
            @Override
            public void onResponse(Call<ModelEvent> call, Response<ModelEvent> response) {
//                dismissLoading();
                if (response.isSuccessful()) {
                    ModelEvent data = response.body();
                    if (data.getData() != null) {
                        if (data.getData().size() != 0) {
                            for (int i = 0; i < data.getData().size(); i++) {
                                adapter.getData().add(data.getData().get(i));
                                adapter.notifyItemInserted(adapter.getData().size() - 1);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelEvent> call, Throwable t) {
            }
        });

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

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("latitude", dLatitude);
            jsonObject.addProperty("longitude", dLongitude);
            jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getActivity()));

            Call<ModelBase> call = apiService.locationUpdate(jsonObject);
            call.enqueue(new Callback<ModelBase>() {
                @Override
                public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                    dismissLoading();
                }

                @Override
                public void onFailure(Call<ModelBase> call, Throwable t) {
                    dismissLoading();
                }
            });
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
