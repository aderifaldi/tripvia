package com.playground.skypass.app.dialog;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.playground.skypass.R;
import com.playground.skypass.activity.MainActivity2;
import com.playground.skypass.model.ModelReward;
import com.radyalabs.irfan.util.AppUtility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by aderifaldi on 13-Dec-15.
 */
@SuppressLint("ValidFragment")
public class RewardDialog extends DialogFragment implements View.OnClickListener {

    private View view;
    private String title, message, price;
    private RelativeLayout redeem;
    private ModelReward.Data tReward;
    private ProgressDialog loading;
    private int point;
    private DecimalFormat decimalFormat;
    private DecimalFormatSymbols symbols;

    @SuppressLint("ValidFragment")
    public RewardDialog(String title, String message, int point) {
        this.title = title;
        this.message = message;
        this.point = point;
    }

    public void showLoading() {
        loading = AppUtility.showLoading(loading, getActivity());
        loading.setCancelable(true);
    }

    public void dismissLoading() {
        loading.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        decimalFormat = new DecimalFormat("###,###,###,###", symbols);

        view = inflater.inflate(R.layout.dialog_reward, container, false);

        tReward = (ModelReward.Data) getArguments().getSerializable("data");

        TextView TxtMessage = (TextView) view.findViewById(R.id.title);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        TextView price = (TextView) view.findViewById(R.id.point);

        TxtMessage.setText(title);
        desc.setText(message);
        price.setText(decimalFormat.format(point) + " Points");

        redeem = (RelativeLayout) view.findViewById(R.id.redeem);
        redeem.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.redeem:

                ((MainActivity2)getActivity()).confirmFingerPrint(tReward.getTitle(), tReward.getId());
                getDialog().dismiss();

                break;

            default:
        }

    }

}
