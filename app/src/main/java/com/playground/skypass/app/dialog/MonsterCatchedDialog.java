package com.playground.skypass.app.dialog;


import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playground.skypass.R;

/**
 * Created by aderifaldi on 13-Dec-15.
 */
@SuppressLint("ValidFragment")
public class MonsterCatchedDialog extends DialogFragment implements View.OnClickListener{

    private View view;
    private Button btn_close;
    private ImageView MonsterImage;
    private String monsterImageUrl;
    private String monsterTitle, message;
    private RelativeLayout level;

    @SuppressLint("ValidFragment")
    public MonsterCatchedDialog(String monsterImageUrl, String title, String message){
        this.monsterImageUrl = monsterImageUrl;
        this.monsterTitle = title;
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view = inflater.inflate(R.layout.dialog_monster, container, false);
        btn_close = (Button) view.findViewById(R.id.close);
        MonsterImage = (ImageView)view.findViewById(R.id.monsterImage);
        TextView TxtMessage = (TextView)view.findViewById(R.id.title);
        TxtMessage.setText(message);
        Glide.with(getActivity()).
                load(this.monsterImageUrl).
                centerCrop().
                crossFade().
                into(MonsterImage);

        level = (RelativeLayout) view.findViewById(R.id.level);

        btn_close.setOnClickListener(this);
        level.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.level:

                getDialog().dismiss();
                getActivity().onBackPressed();

                break;

            default:
        }

    }

}
