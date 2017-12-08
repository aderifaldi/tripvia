package com.playground.skypass.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playground.skypass.R;
import com.playground.skypass.api.ApiService;
import com.playground.skypass.api.ServiceGenerator;
import com.radyalabs.irfan.util.AppUtility;

/**
 * Created by aderifaldi on 07/09/2016.
 */
public class BaseFragment extends Fragment {

    private ProgressDialog loading;
    public ApiService apiService = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ServiceGenerator.createService(ApiService.class);
    }

    public void showLoading(){
        loading = AppUtility.showLoading(loading, getActivity());
        loading.setCancelable(true);
    }

    public void dismissLoading(){
        loading.dismiss();
    }

    public void goToAnimation(){
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void backAnimation(){
        getActivity().overridePendingTransition(R.anim.left_back_in, R.anim.left_back_out);
    }

}
