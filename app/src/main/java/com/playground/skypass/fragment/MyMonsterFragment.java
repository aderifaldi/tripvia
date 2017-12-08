package com.playground.skypass.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.playground.skypass.R;
import com.playground.skypass.app.adapter.MonsterAdapter;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelMonster;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class MyMonsterFragment extends BaseFragment {

    @BindView(R.id.listMonster)
    RecyclerView list;
    @BindView(R.id.emptyInfo)
    LinearLayout emptyInfo;

    private MonsterAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_monster, container, false);

        ButterKnife.bind(this, view);

        adapter = new MonsterAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadMonster();

        return view;
    }

    private void loadMonster() {
        Call<ModelMonster> call = apiService.myMonster(GlobalVariable.getUserEmail(getActivity()));
        call.enqueue(new Callback<ModelMonster>() {
            @Override
            public void onResponse(Call<ModelMonster> call, Response<ModelMonster> response) {
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        ModelMonster data = response.body();
                        if (data.getData() != null){
                            emptyInfo.setVisibility(View.GONE);
                            if (data.getData().size() != 0){
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
            }

            @Override
            public void onFailure(Call<ModelMonster> call, Throwable t) {
                dismissLoading();
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
