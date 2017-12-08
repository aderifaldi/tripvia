package com.playground.skypass.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.api.ApiService;
import com.playground.skypass.api.ServiceGenerator;
import com.playground.skypass.app.imageview.CircleImageView;
import com.playground.skypass.app.textview.FontPath;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.fragment.EventFragment;
import com.playground.skypass.fragment.PoiFragment;
import com.playground.skypass.fragment.ProfileFragment;
import com.playground.skypass.fragment.PromotionFragment;
import com.playground.skypass.fragment.PromotionFragment2;
import com.playground.skypass.fragment.RewardFragment;
import com.playground.skypass.fragment.TourismFragment;
import com.playground.skypass.fragment.TransactionFragment;
import com.playground.skypass.model.ModelBase;
import com.radyalabs.irfan.util.AppUtility;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.SpassFingerprint;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity implements MyPassCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final String EVENT = "EVENT";
    private static final String PROMOTION = "PROMOTION";
    private static final String REWARD = "REWARD";
    private static final String CHALLANGE = "CHALLANGE";
    private static final String PROFILE = "PROFILE";
    @BindView(R.id.txtEvent)
    TextViewAutoStyle txtEvent;
    @BindView(R.id.menuEvent)
    RelativeLayout menuEvent;
    @BindView(R.id.iconMenuPromo)
    ImageView iconMenuPromo;
    @BindView(R.id.txtPromo)
    TextViewAutoStyle txtPromo;
    @BindView(R.id.menuPromo)
    RelativeLayout menuPromo;
    @BindView(R.id.iconMenuChallange)
    ImageView iconMenuChallange;
    @BindView(R.id.txtChallange)
    TextViewAutoStyle txtChallange;
    @BindView(R.id.menuChallange)
    RelativeLayout menuChallange;
    @BindView(R.id.iconMenuReward)
    ImageView iconMenuReward;
    @BindView(R.id.txtReward)
    TextViewAutoStyle txtReward;
    @BindView(R.id.menuReward)
    RelativeLayout menuReward;
    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.borderProfileImage)
    RelativeLayout borderProfileImage;
    @BindView(R.id.menuProfile)
    RelativeLayout menuProfile;
    @BindView(R.id.bottomMenu)
    PercentRelativeLayout bottomMenu;
    @BindView(R.id.body)
    FrameLayout body;
    @BindView(R.id.iconMenuEvent)
    ImageView iconMenuEvent;
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    private Fragment fragment = null;
    private MyPass myPass;
    private String title;
    private int redeemId;
    private ApiService apiService = null;
    private ProgressDialog loading;
    private Typeface tfBodyRegular;
    private GoogleApiClient mGoogleApiClient;

    public void showLoading() {
        loading = AppUtility.showLoading(loading, this);
        loading.setCancelable(true);
    }

    public void dismissLoading() {
        loading.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        ButterKnife.bind(this);

        tfBodyRegular = Typeface.createFromAsset(getAssets(), FontPath.REGULAR);
        edtSearch.setTypeface(tfBodyRegular);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

                    String keyWord = edtSearch.getText().toString();
                    if (!TextUtils.isEmpty(keyWord)){
                        startActivity(new Intent(getApplicationContext(), PoiSearchActivity.class)
                                .putExtra("q", keyWord));
                        edtSearch.setText("");
                    }else {
                        Toast.makeText(MainActivity2.this, "Please input keyword!", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });

        apiService = ServiceGenerator.createService(ApiService.class);

        displayView(getIntent().getStringExtra("first_menu"));

        Glide.with(this).load(GlobalVariable.getUserPhotoURL(this)).into(imgProfile);

        myPass = new MyPass(this);
        myPass.setCallback(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        try {
            myPass.initialize();
            GlobalVariable.setIsSamsungDevice(this, true);
        } catch (SsdkUnsupportedException e) {
//            showErrorDialog("SDK PASS",e.getMessage());
            GlobalVariable.setIsSamsungDevice(this, false);
            return;
        } catch (UnsupportedOperationException e) {
//            showErrorDialog("OPERATION PASS",e.getMessage());
            GlobalVariable.setIsSamsungDevice(this, false);
            return;
        }

        myPass.setActive(true);

    }

    public void confirmFingerPrint(String title, int redeemId) {

        this.title = title;
        this.redeemId = redeemId;

        if (GlobalVariable.getIsSamsungDevice(this)) {
            if (!myPass.hasRegisteredFinger) {
                myPass.handleRegisterFinger();
            } else {
                myPass.handleIdentifyWithDialog(true);
            }
        } else {
            startActivity(new Intent(getApplicationContext(), EnterPinForRedeemActivity.class)
                    .putExtra("title", title)
                    .putExtra("redeemId", redeemId));
        }

    }

    void showErrorDialog(String title, String message) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void displayView(String menu) {
        switch (menu) {
            case EVENT:
                fragment = new PoiFragment();

                break;

            case PROMOTION:
                fragment = new PromotionFragment2();

                break;

            case REWARD:
                fragment = new RewardFragment();

                break;

            case CHALLANGE:
                fragment = new TransactionFragment();

                break;

            case PROFILE:
                fragment = new ProfileFragment();

                break;

            default:
        }

        if (fragment != null) {
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.body, fragment);
                fragmentTransaction.commit();
            } catch (Exception ex) {
                AppUtility.logD("", Log.getStackTraceString(ex));
            }
        }
    }

    @OnClick({R.id.menuEvent, R.id.menuPromo, R.id.menuChallange, R.id.menuReward, R.id.menuProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuEvent:
                displayView(EVENT);
                iconMenuEvent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.event));
                iconMenuPromo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_unselected));
                iconMenuChallange.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_unselected));
                iconMenuReward.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coin_unselected));
                borderProfileImage.setBackgroundResource(R.drawable.bullet_fill_accent);

                txtEvent.setTextColor(ContextCompat.getColor(this, R.color.colorDarkOrange));
                txtPromo.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtChallange.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtReward.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                break;
            case R.id.menuPromo:
                displayView(PROMOTION);
                iconMenuEvent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.event_unselected));
                iconMenuPromo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo));
                iconMenuChallange.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_unselected));
                iconMenuReward.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coin_unselected));
                borderProfileImage.setBackgroundResource(R.drawable.bullet_fill_accent);

                txtEvent.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtPromo.setTextColor(ContextCompat.getColor(this, R.color.colorDarkOrange));
                txtChallange.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtReward.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                break;
            case R.id.menuChallange:
                displayView(CHALLANGE);
                iconMenuEvent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.event_unselected));
                iconMenuPromo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_unselected));
                iconMenuChallange.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search));
                iconMenuReward.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coin_unselected));
                borderProfileImage.setBackgroundResource(R.drawable.bullet_fill_accent);

                txtEvent.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtPromo.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtChallange.setTextColor(ContextCompat.getColor(this, R.color.colorDarkOrange));
                txtReward.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                break;
            case R.id.menuReward:
                displayView(REWARD);
                iconMenuEvent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.event_unselected));
                iconMenuPromo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_unselected));
                iconMenuChallange.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_unselected));
                iconMenuReward.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coin));
                borderProfileImage.setBackgroundResource(R.drawable.bullet_fill_accent);

                txtEvent.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtPromo.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtChallange.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtReward.setTextColor(ContextCompat.getColor(this, R.color.colorDarkOrange));
                break;
            case R.id.menuProfile:
                displayView(PROFILE);
                iconMenuEvent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.event_unselected));
                iconMenuPromo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.promo_unselected));
                iconMenuChallange.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.search_unselected));
                iconMenuReward.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.coin_unselected));
                borderProfileImage.setBackgroundResource(R.drawable.bullet_fill_active);

                txtEvent.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtPromo.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtChallange.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                txtReward.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                break;
        }
    }

    @Override
    public void onFinishedIdentify(int eventStatus) {
        if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getApplicationContext()));
            jsonObject.addProperty("reward_id", redeemId);
            jsonObject.addProperty("title", title);

            showLoading();
            Call<ModelBase> call = apiService.redeem(jsonObject);
            call.enqueue(new Callback<ModelBase>() {
                @Override
                public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                    dismissLoading();
                    if (!response.body().isError()) {
                        Toast.makeText(getApplicationContext(),
                                response.body().getAlerts().getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onFinishedRegister(boolean register) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                GlobalVariable.saveLocation(this, dLatitude, dLongitude);
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                mGoogleApiClient);
//        if (mLastLocation != null) {
//
//            double dLatitude = mLastLocation.getLatitude();
//            double dLongitude = mLastLocation.getLongitude();
//
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("latitude", dLatitude);
//            jsonObject.addProperty("longitude", dLongitude);
//            jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(getApplicationContext()));
//
//            Call<ModelBase> call = apiService.locationUpdate(jsonObject);
//            call.enqueue(new Callback<ModelBase>() {
//                @Override
//                public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
//                    Toast.makeText(MainActivity2.this, response.body().getAlerts().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<ModelBase> call, Throwable t) {
//
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}
