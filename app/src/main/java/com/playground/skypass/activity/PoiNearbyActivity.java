package com.playground.skypass.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.playground.skypass.R;
import com.playground.skypass.app.adapter.PagerPoiAdapter;
import com.playground.skypass.app.adapter.PagerPoiNearbyAdapter;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.app.util.JsonData;
import com.playground.skypass.model.ModelPoiAll;
import com.playground.skypass.model.ModelPoiDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoiNearbyActivity extends BaseActivity {

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.title)
    TextViewAutoStyle title;

    private PagerPoiNearbyAdapter adapter;
    private ArrayList<String> titlePage = new ArrayList<>();
    private ModelPoiDetail modelPoiAll;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_nearby);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);

        modelPoiAll = JsonData.getPoiDetail(getApplicationContext());
        for (int i = 0; i < modelPoiAll.getData().getNearby().size(); i++) {
            titlePage.add(modelPoiAll.getData().getNearby().get(i).getCategory());
        }

        title.setText("Nearby " + modelPoiAll.getData().getTitle());

        final CharSequence[] tabTitle = titlePage.toArray(new CharSequence[titlePage.size()]);

        adapter = new PagerPoiNearbyAdapter(getSupportFragmentManager(), modelPoiAll.getData().getNearby(), tabTitle, modelPoiAll.getData().getNearby().size());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(modelPoiAll.getData().getNearby().size());
        tabLayout.setupWithViewPager(viewPager);

    }

    private void getPoiDetail(){
        showLoading();
        Call<ModelPoiDetail> call = apiService.poiDetail(id);
        call.enqueue(new Callback<ModelPoiDetail>() {
            @Override
            public void onResponse(Call<ModelPoiDetail> call, Response<ModelPoiDetail> response) {
                dismissLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        modelPoiAll = response.body();
                        modelPoiAll.getData().getNearby().clear();

                        for (int i = 0; i < modelPoiAll.getData().getNearby().size(); i++) {
                            titlePage.add(modelPoiAll.getData().getNearby().get(i).getCategory());
                        }

                        title.setText("Nearby " + modelPoiAll.getData().getTitle());

                        final CharSequence[] tabTitle = titlePage.toArray(new CharSequence[titlePage.size()]);

                        adapter = new PagerPoiNearbyAdapter(getSupportFragmentManager(), modelPoiAll.getData().getNearby(), tabTitle, modelPoiAll.getData().getNearby().size());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(modelPoiAll.getData().getNearby().size());
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelPoiDetail> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
        backAnimation();
    }
}
