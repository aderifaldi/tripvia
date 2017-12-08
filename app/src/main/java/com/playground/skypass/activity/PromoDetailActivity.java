package com.playground.skypass.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playground.skypass.R;
import com.playground.skypass.model.ModelPromo;
import com.radyalabs.irfan.util.AppUtility;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromoDetailActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.xp)
    TextView xp;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.dateEnd)
    TextView dateEnd;

    private ModelPromo.Data tPromo;
    private Bundle extras;
    private SimpleDateFormat sdf;
//    private Uri data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

//        Intent intent = getIntent();
//        data = intent.getData();
//        if (data != null){
//            AppUtility.logD("MainActivity", "url : " + data.toString().substring(data.toString().lastIndexOf("/") + 1));
//        }

        sdf = new SimpleDateFormat("dd/MMM/yyyy");

        extras = getIntent().getExtras();
        tPromo = (ModelPromo.Data) extras.getSerializable("data");

        ButterKnife.bind(this);

        Glide.with(this).
                load(tPromo.getImage()).
                centerCrop().
                crossFade().
                into(img);

        title.setText(tPromo.getTitle());
        desc.setText(tPromo.getDescription());
        point.setText("" + tPromo.getPoint() + " Points");
        xp.setText("" + tPromo.getXp() + " Xp");
        date.setText(sdf.format(tPromo.getStart_date()));
        dateEnd.setText(sdf.format(tPromo.getEnd_date()));

    }

    @OnClick(R.id.back)
    protected void back(){
        onBackPressed();
        backAnimation();
    }

}
