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
import com.playground.skypass.activity.PromoDetailActivity;
import com.playground.skypass.app.adapter.PromoAdapter;
import com.playground.skypass.model.ModelPromo;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class PromotionFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.pay)
    FloatingActionButton pay;

    private PromoAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        ButterKnife.bind(this, view);

        pay.setVisibility(View.GONE);

        adapter = new PromoAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());


        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadPromo();

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelPromo.Data tPromo = adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), PromoDetailActivity.class);
                intent.putExtra("data", tPromo);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        return view;
    }

    private void loadPromo() {

        Call<ModelPromo> call = apiService.getPromo();
        call.enqueue(new Callback<ModelPromo>() {
            @Override
            public void onResponse(Call<ModelPromo> call, Response<ModelPromo> response) {
                if (response.isSuccessful()){
                    ModelPromo data = response.body();
                    if (data.getData() != null){
                        if (data.getData().size() != 0){
                            for (int i = 0; i < data.getData().size(); i++) {
                                adapter.getData().add(data.getData().get(i));
                                adapter.notifyItemInserted(adapter.getData().size() - 1);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelPromo> call, Throwable t) {

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

}
