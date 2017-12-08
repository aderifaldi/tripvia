package com.playground.skypass.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.playground.skypass.R;
import com.playground.skypass.app.adapter.ActivityAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class ActivityFragment extends BaseFragment {

//    private MobileServiceClient mobileServiceClient;
//    private MobileServiceTable<TUserEvent> mobileServiceTable;
//    private ArrayList<TUserEvent> tRewards;

    @BindView(R.id.list)
    RecyclerView list;

    private ActivityAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//    public void setupAzureService() {
//        try {
//            // Create the Mobile Service Client instance, using the provided
//            // Mobile Service URL and key
//            mobileServiceClient = new MobileServiceClient(
//                    GlobalVariable.AZURE_SERVICE_URL,
//                    GlobalVariable.AZURE_SERVICE_KEY,
//                    getActivity()).withFilter(new ProgressFilter());
//
//            // Get the Mobile Service Table instance to use
//            mobileServiceTable = mobileServiceClient.getTable(TUserEvent.class);
//        } catch (MalformedURLException e) {
//            AppUtility.logD("RecipeDetail", "There was an error creating the Mobile Service. Verify the URL");
//        }
//    }

//    private class ProgressFilter implements ServiceFilter {
//
//        @Override
//        public ListenableFuture<ServiceFilterResponse> handleRequest(
//                ServiceFilterRequest request, NextServiceFilterCallback next) {
//
//            getActivity().runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
////                    showLoading();
//                }
//            });
//
//            SettableFuture<ServiceFilterResponse> result = SettableFuture.create();
//            try {
//                ServiceFilterResponse response = next.onNext(request).get();
//                result.set(response);
//            } catch (Exception exc) {
//                result.setException(exc);
//            }
//
//            dismissProgressBar();
//            return result;
//        }
//
//        private void dismissProgressBar() {
//            getActivity().runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
////                    dismissLoading();
//                }
//            });
//        }
//    }

//    private void loadMonster(){
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    final MobileServiceList<TUserEvent> result = mobileServiceTable.where().
//                            field("user_id").eq(GlobalVariable.getUserEmail(getActivity())).execute().get();
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            AppUtility.logD("MainActivity", "get monster Success");
//
//                            tRewards = new ArrayList<>();
//                            tRewards.clear();
//
//                            for(TUserEvent item : result){
//                                tRewards.add(item);
//                            }
//
//                            adapter.getData().addAll(tRewards);
//                            adapter.notifyItemInserted(adapter.getData().size() - 1);
//
//                            list.setLayoutManager(linearLayoutManager);
//                            list.setAdapter(adapter);
//
//                        }
//                    });
//                } catch (Exception exception) {
//                    AppUtility.logD("MainActivity", "get comment Error");
//                }
//                return null;
//            }
//        }.execute();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        ButterKnife.bind(this, view);

        adapter = new ActivityAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

//        setupAzureService();
//
//        loadMonster();
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
