package com.playground.skypass.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterPinForRedeemActivity extends BaseActivity {

    @BindView(R.id.indicator_dots)
    IndicatorDots mIndicatorDots;
    @BindView(R.id.pin_lock_view)
    PinLockView mPinLockView;

    private final static String TAG = "TAG";

    private String title;
    private int redeemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
        ButterKnife.bind(this);

        title = getIntent().getStringExtra("title");
        redeemId = getIntent().getIntExtra("redeemId", 0);

        mPinLockView.attachIndicatorDots(mIndicatorDots);

        //set lock code length
        mPinLockView.setPinLength(4);

        //set listener for lock code change
        mPinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                Log.d(TAG, "lock code: " + pin);

                //User input true code
                if (pin.equals("1234")){
                    letPay();
                    mPinLockView.resetPinLockView();
                }else {
                    Toast.makeText(EnterPinForRedeemActivity.this, "Invalid pin", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    backAnimation();
                }

            }

            @Override
            public void onEmpty() {
                Log.d(TAG, "lock code is empty!");
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            }
        });

    }

    private void letPay(){
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
                    onBackPressed();
                    backAnimation();
                }
            }

            @Override
            public void onFailure(Call<ModelBase> call, Throwable t) {
                dismissLoading();
            }
        });
    }

}
