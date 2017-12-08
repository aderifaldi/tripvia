package com.playground.skypass.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.playground.skypass.R;
import com.playground.skypass.app.adapter.TransactionDetailAdapter;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.model.ModelTransactionDetail;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionDetailActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextViewAutoStyle title;
    @BindView(R.id.total)
    TextViewAutoStyle total;
    @BindView(R.id.marchantName)
    TextViewAutoStyle marchantName;

    private TransactionDetailAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DecimalFormat decimalFormat;
    private DecimalFormatSymbols symbols;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        decimalFormat = new DecimalFormat("###,###,###,###", symbols);

        ButterKnife.bind(this);

        id = getIntent().getStringExtra("id");
//        marchantName.setText(getIntent().getStringExtra("name"));
        marchantName.setText("Pesona Marchant");
        total.setText("Rp. " + decimalFormat.format(getIntent().getIntExtra("total", 0)));

        adapter = new TransactionDetailAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadDetail();

    }

    private void loadDetail() {

        Call<ModelTransactionDetail> call = apiService.myTransactionDetail(id);
        call.enqueue(new Callback<ModelTransactionDetail>() {
            @Override
            public void onResponse(Call<ModelTransactionDetail> call, Response<ModelTransactionDetail> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isError()) {
                        if (response.body().getData() != null) {
                            if (response.body().getData().getProducts().size() != 0) {
                                for (int i = 0; i < response.body().getData().getProducts().size(); i++) {
                                    adapter.getData().add(response.body().getData().getProducts().get(i));
                                    adapter.notifyItemInserted(adapter.getData().size() - 1);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTransactionDetail> call, Throwable t) {

            }
        });

    }


    @OnClick(R.id.back)
    protected void back() {
        onBackPressed();
        backAnimation();
    }

}
