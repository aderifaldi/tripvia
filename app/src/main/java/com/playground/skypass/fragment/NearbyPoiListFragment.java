package com.playground.skypass.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.playground.skypass.R;
import com.playground.skypass.activity.PoiDetailActivity;
import com.playground.skypass.activity.PoiNearbyActivity;
import com.playground.skypass.app.adapter.PoiAdapter;
import com.playground.skypass.app.adapter.PoiNearbyAdapter;
import com.playground.skypass.model.ModelPoiAll;
import com.playground.skypass.model.ModelPoiDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class NearbyPoiListFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.pay)
    FloatingActionButton pay;

    private PoiNearbyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ModelPoiDetail.Data.Nearby places;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);
        places = (ModelPoiDetail.Data.Nearby) getArguments().getSerializable("places");

        ButterKnife.bind(this, view);

        pay.setVisibility(View.GONE);

        adapter = new PoiNearbyAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        for (int i = 0; i < places.getPlaces().size(); i++) {
            adapter.getData().add(places.getPlaces().get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), PoiDetailActivity.class)
                        .putExtra("id", adapter.getData().get(i).getId()));
                goToAnimation();
            }
        });

        return view;
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
