package com.playground.skypass.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.playground.skypass.R;
import com.playground.skypass.api.ApiService;
import com.playground.skypass.api.ServiceGenerator;
import com.radyalabs.irfan.util.AppUtility;

/**
 * Created by aderifaldi on 07/09/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog loading;
    public ApiService apiService = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ServiceGenerator.createService(ApiService.class);
    }

    public void showLoading(){
        loading = AppUtility.showLoading(loading, this);
        loading.setCancelable(true);
    }

    public void dismissLoading(){
        loading.dismiss();
    }

    public void goToAnimation(){
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void backAnimation(){
        overridePendingTransition(R.anim.left_back_in, R.anim.left_back_out);
    }

    protected void showMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", null);
        builder.show();
    }

}
