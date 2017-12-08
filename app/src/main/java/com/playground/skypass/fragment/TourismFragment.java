package com.playground.skypass.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.playground.skypass.R;
import com.playground.skypass.activity.PromoDetailActivity;
import com.playground.skypass.activity.TourismDetailActivity;
import com.playground.skypass.app.adapter.PromoAdapter;
import com.playground.skypass.app.adapter.TourismAdapter;
import com.playground.skypass.model.ModelPromo;
import com.playground.skypass.model.ModelWisata;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chef_marinka on 21-Nov-15.
 */
public class TourismFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.pay)
    FloatingActionButton pay;

    private ModelWisata data;
    private JsonObject object;
    private JsonObject json;
    private String rawContent;
    private Gson gson;
    private GsonBuilder gsonBuilder;
    private String homemenuString;

    private TourismAdapter adapter;
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

        pay.setVisibility(View.GONE);

        adapter = new TourismAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());


        list.setAdapter(adapter);
        list.setLayoutManager(linearLayoutManager);

        loadPromo();

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelWisata.DataBean tPromo = adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), TourismDetailActivity.class);
                intent.putExtra("data", tPromo);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });

        return view;
    }

    private void loadPromo() {

        loadKnowledgeBase();

    }

    private void loadKnowledgeBase() {

        homemenuString = loadJSONFromAsset();

        rawContent = new String(homemenuString);
        json = new JsonParser().parse(rawContent).getAsJsonObject();
        object = json.getAsJsonObject();

        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        data = gson.fromJson(object, ModelWisata.class);

        for (ModelWisata.DataBean menu : data.getData()) {
            adapter.getData().add(menu);
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getActivity().getAssets().open("json/wisata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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
