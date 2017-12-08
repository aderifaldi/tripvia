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
import com.playground.skypass.app.adapter.RewardAdapter;
import com.playground.skypass.app.dialog.RewardDialog;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelReward;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class MyRewardFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.emptyInfo)
    LinearLayout emptyInfo;

    private RewardAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RewardDialog rewardDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redeem, container, false);

        ButterKnife.bind(this, view);

        adapter = new RewardAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadReward();

//        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                ModelReward.Data tReward = adapter.getData().get(position);
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", tReward);
//
//                rewardDialog = new RewardDialog(tReward.getTitle(), tReward.getDescription());
//                rewardDialog.setArguments(bundle);
//                rewardDialog.show(getActivity().getSupportFragmentManager(), "");
//            }
//        });

        return view;
    }

    private void loadReward(){
        Call<ModelReward> call = apiService.myRedeem(GlobalVariable.getUserEmail(getActivity()));
        call.enqueue(new Callback<ModelReward>() {
            @Override
            public void onResponse(Call<ModelReward> call, Response<ModelReward> response) {
                if (response.isSuccessful()){

                    if (!response.body().isError()){
                        ModelReward data = response.body();
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
            public void onFailure(Call<ModelReward> call, Throwable t) {

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
