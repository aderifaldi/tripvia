package com.playground.skypass.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playground.skypass.R;
import com.playground.skypass.app.textview.TextViewAutoStyle;
import com.playground.skypass.model.ModelWisata;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TourismDetailActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.pageTitle)
    TextViewAutoStyle pageTitle;

    private ModelWisata.DataBean tPromo;
    private Bundle extras;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        sdf = new SimpleDateFormat("dd/MMM/yyyy");

        extras = getIntent().getExtras();
        tPromo = (ModelWisata.DataBean) extras.getSerializable("data");

        ButterKnife.bind(this);

        Glide.with(this).
                load(tPromo.getPhoto()).
                centerCrop().
                crossFade().
                into(img);

        pageTitle.setText(tPromo.getTitle());
        title.setText(tPromo.getTitle());
        desc.setText(tPromo.getDesc());

    }

    @OnClick(R.id.back)
    protected void back() {
        onBackPressed();
        backAnimation();
    }

}
