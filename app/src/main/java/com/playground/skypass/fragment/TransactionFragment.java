package com.playground.skypass.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.playground.skypass.R;
import com.playground.skypass.activity.QRScanActivity;
import com.playground.skypass.activity.TransactionDetailActivity;
import com.playground.skypass.app.adapter.TransactionAdapter;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class TransactionFragment extends BaseFragment {

    private static final int REQUEST_CAMERA = 1;

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.txt_name)
    TextViewAutoStyle txtName;
    @BindView(R.id.emptyInfo)
    LinearLayout emptyInfo;

    private TransactionAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaksi, container, false);

        ButterKnife.bind(this, view);

        adapter = new TransactionAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), TransactionDetailActivity.class)
                        .putExtra("id", adapter.getData().get(i).getTransaction_id())
                        .putExtra("name", adapter.getData().get(i).getMerchant_name())
                        .putExtra("total", adapter.getData().get(i).getAmount()));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadReward();
    }

    private void loadReward() {
        Call<ModelTransaction> call = apiService.myTransaction(GlobalVariable.getUserEmail(getActivity()));
        call.enqueue(new Callback<ModelTransaction>() {
            @Override
            public void onResponse(Call<ModelTransaction> call, Response<ModelTransaction> response) {
                if (response.isSuccessful()) {
                    ModelTransaction data = response.body();
                    if (data.getData() != null) {
                        if (data.getData().size() != 0) {
                            adapter.getData().clear();
                            emptyInfo.setVisibility(View.GONE);
                            for (int i = 0; i < data.getData().size(); i++) {
                                adapter.getData().add(data.getData().get(i));
                                adapter.notifyItemInserted(adapter.getData().size() - 1);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        emptyInfo.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTransaction> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.pay)
    protected void pay() {
//        Toast.makeText(getActivity(), "If camera not launch, please try again", Toast.LENGTH_SHORT).show();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > 22) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            } else {
                goToEnterPin();
            }
        } else {
            goToEnterPin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToEnterPin();
                } else {
                    goToEnterPin();
                }
            }
        }
    }

    private void goToEnterPin(){
        startActivity(new Intent(getActivity(), QRScanActivity.class));
        goToAnimation();
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
