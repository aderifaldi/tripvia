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
import com.playground.skypass.app.adapter.EventAdapter;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class MyCheckInFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.emptyInfo)
    LinearLayout emptyInfo;

    private EventAdapter adapter;
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

        adapter = new EventAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadEvent();

//        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ModelEvent.Data tEvent = adapter.getData().get(position);
//                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
//                intent.putExtra("data", tEvent);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
//            }
//        });

        return view;
    }

    private void loadEvent() {
        Call<ModelEvent> call = apiService.myCheckIn(GlobalVariable.getUserEmail(getActivity()));
        call.enqueue(new Callback<ModelEvent>() {
            @Override
            public void onResponse(Call<ModelEvent> call, Response<ModelEvent> response) {
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        ModelEvent data = response.body();
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
            public void onFailure(Call<ModelEvent> call, Throwable t) {
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
