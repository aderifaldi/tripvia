package com.playground.skypass.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.playground.skypass.R;
import com.playground.skypass.activity.FacebookLoginActivity;
import com.playground.skypass.app.adapter.PagerProfileAdapter;
import com.playground.skypass.app.adapter.PagerPromoAdapter;
import com.playground.skypass.app.imageview.CircleImageView;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class PromotionFragment2 extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private CharSequence pagerTitle[] = {"Event", "Promo"};
    private PagerPromoAdapter adapter;
    private int Numboftabs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getActivity());

        View view = inflater.inflate(R.layout.fragment_promotion2, container, false);

        ButterKnife.bind(this, view);

        Numboftabs = pagerTitle.length;
        adapter = new PagerPromoAdapter(getActivity().getSupportFragmentManager(), pagerTitle, Numboftabs);

        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(pagerTitle.length);
        tab_layout.setupWithViewPager(view_pager);

        return view;
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
//            mobileServiceTableMonster = mobileServiceClient.getTable(TUserMonster.class);
//            mobileServiceTableEvent = mobileServiceClient.getTable(TUserEvent.class);
//            mobileServiceTableReward = mobileServiceClient.getTable(TUserReward.class);
//
//        } catch (MalformedURLException e) {
//            AppUtility.logD("RecipeDetail", "There was an error creating the Mobile Service. Verify the URL");
//        }
//    }
//
//    public void loadUserMonster(){
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    final MobileServiceList<TUserMonster> result = mobileServiceTableMonster.execute().get();
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            AppUtility.logD("MainActivity", "get user monster Success");
//
//                            userMonsters = new ArrayList<>();
//                            userMonsters.clear();
//
//                            for (TUserMonster item : result) {
//                                userMonsters.add(item);
//                            }
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
//
//    public void loadUserEvent(){
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    final MobileServiceList<TUserEvent> result = mobileServiceTableEvent.execute().get();
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            AppUtility.logD("MainActivity", "get user monster Success");
//
//                            userEvents = new ArrayList<>();
//                            userEvents.clear();
//
//                            for (TUserEvent item : result) {
//                                userEvents.add(item);
//                            }
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
//
//    public void loadUserReward(){
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    final MobileServiceList<TUserReward> result = mobileServiceTableReward.execute().get();
//                    getActivity().runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            AppUtility.logD("MainActivity", "get user monster Success");
//
//                            userRewards = new ArrayList<>();
//                            userRewards.clear();
//
//                            for (TUserReward item : result) {
//                                userRewards.add(item);
//                            }
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
//
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
//                    showLoading();
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
//                    dismissLoading();
//                }
//            });
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
