package com.playground.skypass.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.app.adapter.PagerPoiAdapter;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.app.util.JsonData;
import com.playground.skypass.model.ModelPoiAll;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class PoiFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private PagerPoiAdapter adapter;
    private ArrayList<String> titlePage = new ArrayList<>();
    private ModelPoiAll modelPoiAll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.poi_fragment, container, false);

        ButterKnife.bind(this, view);

        modelPoiAll = JsonData.getPoiAll(getActivity());
        for (int i = 0; i < modelPoiAll.getData().size(); i++) {
            titlePage.add(modelPoiAll.getData().get(i).getCategory());
        }

        final CharSequence[] tabTitle = titlePage.toArray(new CharSequence[titlePage.size()]);

        adapter = new PagerPoiAdapter(getFragmentManager(), modelPoiAll.getData(), tabTitle, modelPoiAll.getData().size());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(modelPoiAll.getData().size());
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void getPoiAll(){
        showLoading();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat", GlobalVariable.getLocation(getActivity()).getLatitude());
        jsonObject.addProperty("lon", GlobalVariable.getLocation(getActivity()).getLongitude());


        Call<ModelPoiAll> call = apiService.getPoiAll(jsonObject);
        call.enqueue(new Callback<ModelPoiAll>() {
            @Override
            public void onResponse(Call<ModelPoiAll> call, Response<ModelPoiAll> response) {
                dismissLoading();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        modelPoiAll.getData().clear();
                        modelPoiAll = response.body();

                        for (int i = 0; i < modelPoiAll.getData().size(); i++) {
                            titlePage.add(modelPoiAll.getData().get(i).getCategory());
                        }

                        final CharSequence[] tabTitle = titlePage.toArray(new CharSequence[titlePage.size()]);

                        adapter = new PagerPoiAdapter(getFragmentManager(), modelPoiAll.getData(), tabTitle, modelPoiAll.getData().size());
                        viewPager.setAdapter(adapter);
                        viewPager.setOffscreenPageLimit(modelPoiAll.getData().size());
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelPoiAll> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
