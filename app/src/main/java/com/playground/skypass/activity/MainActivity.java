package com.playground.skypass.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.playground.skypass.R;
import com.playground.skypass.fragment.ChallangeFragment;
import com.playground.skypass.fragment.EventFragment;
import com.playground.skypass.fragment.ProfileFragment;
import com.playground.skypass.fragment.PromotionFragment;
import com.playground.skypass.fragment.RewardFragment;
import com.radyalabs.irfan.util.AppUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String EVENT = "EVENT";
    private static final String PROMOTION = "PROMOTION";
    private static final String REWARD = "REWARD";
    private static final String CHALLANGE = "CHALLANGE";
    private static final String PROFILE = "PROFILE";

    @BindView(R.id.event) ImageView event;
    @BindView(R.id.promo) ImageView promo;
    @BindView(R.id.reward) ImageView reward;
    @BindView(R.id.profile) ImageView profile;

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        displayView(EVENT);

    }

    private void displayView(String menu){
        switch (menu){
            case EVENT:
                fragment = new EventFragment();

                break;

            case PROMOTION:
                fragment = new PromotionFragment();

                break;

            case REWARD:
                fragment = new RewardFragment();

                break;

            case CHALLANGE:
                fragment = new ChallangeFragment();

                break;

            case PROFILE:
                fragment = new ProfileFragment();

                break;

            default:
        }

        if (fragment != null) {
            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.body, fragment);
                fragmentTransaction.commit();
            }catch (Exception ex){
                AppUtility.logD("", Log.getStackTraceString(ex));
            }
        }
    }

    @OnClick(R.id.menu_event)
    protected void menuEvent(){
        event.setImageResource(R.mipmap.event_white);
        promo.setImageResource(R.mipmap.promotion_black);
        reward.setImageResource(R.mipmap.reward_black);
        profile.setImageResource(R.mipmap.profile_black);

        displayView(EVENT);
    }

    @OnClick(R.id.menu_promo)
    protected void menuPromo(){
        event.setImageResource(R.mipmap.event_black);
        promo.setImageResource(R.mipmap.promotion_white);
        reward.setImageResource(R.mipmap.reward_black);
        profile.setImageResource(R.mipmap.profile_black);

        displayView(PROMOTION);
    }

    @OnClick(R.id.menu_reward)
    protected void menuReward(){
        event.setImageResource(R.mipmap.event_black);
        promo.setImageResource(R.mipmap.promotion_black);
        reward.setImageResource(R.mipmap.reward_white);
        profile.setImageResource(R.mipmap.profile_black);

        displayView(REWARD);
    }

    @OnClick(R.id.menu_profile)
    protected void menuProfile(){
        event.setImageResource(R.mipmap.event_black);
        promo.setImageResource(R.mipmap.promotion_black);
        reward.setImageResource(R.mipmap.reward_black);
        profile.setImageResource(R.mipmap.profile_white);

        displayView(PROFILE);
    }

    @OnClick(R.id.menu_challange)
    protected void menuChallange(){
        event.setImageResource(R.mipmap.event_black);
        promo.setImageResource(R.mipmap.promotion_black);
        reward.setImageResource(R.mipmap.reward_black);
        profile.setImageResource(R.mipmap.profile_black);

        displayView(CHALLANGE);
    }

}
