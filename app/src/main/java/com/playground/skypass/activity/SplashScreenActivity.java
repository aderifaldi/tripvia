package com.playground.skypass.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.playground.skypass.R;
import com.playground.skypass.app.util.GlobalVariable;
import com.radyalabs.irfan.util.AppUtility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreenActivity extends BaseActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!GlobalVariable.getLocationRequestGranted(this)){
            requestLocation();
        }else {
            runHandler();
        }

        printKeyHash();

    }

    public void requestLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    GlobalVariable.REQUEST_LOCATION);
        } else {
            GlobalVariable.setLocationRequestGranted(this, true);
            runHandler();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case GlobalVariable.REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GlobalVariable.setLocationRequestGranted(this, true);
                    runHandler();
                } else {
                    GlobalVariable.setLocationRequestGranted(this, false);
                    runHandler();
                }
                break;
        }
    }

    private void runHandler(){

        boolean isLogin = GlobalVariable.getIsLogin(this);

        if (isLogin){
            intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("first_menu", "EVENT");
        }else {
            intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    private void printKeyHash(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.playground.skypass",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                AppUtility.logD("KeyHash:", "hash key = " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.d("KeyHash:", e.toString());
        }
    }

}
