package com.playground.skypass.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.playground.skypass.R;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelBase;
import com.playground.skypass.model.ModelScanQR;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterPinActivity extends BaseActivity {

    @BindView(R.id.indicator_dots)
    IndicatorDots mIndicatorDots;
    @BindView(R.id.pin_lock_view)
    PinLockView mPinLockView;

    private ModelScanQR data;
    private JsonObject object;
    private JsonObject json;
    private String rawContent;
    private Gson gson;
    private GsonBuilder gsonBuilder;

    private final static String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
        ButterKnife.bind(this);

        rawContent = getIntent().getStringExtra("data");

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
                    Toast.makeText(EnterPinActivity.this, "Invalid Pin", Toast.LENGTH_SHORT).show();
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
        showLoading();
        json = new JsonParser().parse(rawContent).getAsJsonObject();
        object = json.getAsJsonObject();

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        data = gson.fromJson(object, ModelScanQR.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("transaction_id", data.getTransaction_id());
        jsonObject.addProperty("merchant_id", data.getMerchant_id());
        jsonObject.addProperty("discount", data.getDiscount());
        jsonObject.addProperty("amount", data.getAmount());
        jsonObject.addProperty("user_id", GlobalVariable.getUserEmail(this));

        Call<ModelBase> call = apiService.buy(jsonObject);
        call.enqueue(new Callback<ModelBase>() {
            @Override
            public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                dismissLoading();

                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        onBackPressed();
                        backAnimation();
                        Toast.makeText(getApplicationContext(), response.body().getAlerts().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelBase> call, Throwable t) {
                dismissLoading();
            }
        });
    }

}
