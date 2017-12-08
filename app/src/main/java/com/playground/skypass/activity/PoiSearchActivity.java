package com.playground.skypass.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.playground.skypass.R;
import com.playground.skypass.app.adapter.PoiSearchAdapter;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.app.util.JsonData;
import com.playground.skypass.model.ModelPoiSearch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoiSearchActivity extends BaseActivity {

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.title)
    TextViewAutoStyle title;
    @BindView(R.id.list)
    RecyclerView list;

    private LinearLayoutManager linearLayoutManager;
    private PoiSearchAdapter adapter;
    private ModelPoiSearch poiSearch;
    private String q = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_search);
        ButterKnife.bind(this);

        q = getIntent().getStringExtra("q");

        adapter = new PoiSearchAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        poiSearch = JsonData.getPoiSearch(getApplicationContext());
        for (int i = 0; i < poiSearch.getData().size(); i++) {
            adapter.getData().add(poiSearch.getData().get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }

    private void getSearchPoi(){
        showLoading();
        Call<ModelPoiSearch> call = apiService.poiSearch(q);
        call.enqueue(new Callback<ModelPoiSearch>() {
            @Override
            public void onResponse(Call<ModelPoiSearch> call, Response<ModelPoiSearch> response) {
                dismissLoading();
                if (response.isSuccessful()){
                    if (response != null){
                        poiSearch = response.body();
                        adapter.getData().clear();

                        for (int i = 0; i < poiSearch.getData().size(); i++) {
                            adapter.getData().add(poiSearch.getData().get(i));
                            adapter.notifyItemInserted(adapter.getData().size() - 1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelPoiSearch> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        backAnimation();
    }
}
