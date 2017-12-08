package com.playground.skypass.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.playground.skypass.R;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelPoiDetail;
import com.playground.skypass.model.ModelPoiSearch;
import com.radyalabs.irfan.util.AppUtility;

import java.util.ArrayList;

public class PoiSearchAdapter extends RecyclerView.Adapter<PoiSearchAdapter.MyViewHolder> {
    private ArrayList<ModelPoiSearch.Data> data;
    private LayoutInflater inflater;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Point screenSize;

    public PoiSearchAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<ModelPoiSearch.Data> getData() {
        return data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_nearby, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelPoiSearch.Data poi = data.get(position);
        holder.position = position;

        final long identity = System.currentTimeMillis();
        holder.identity = identity;

        holder.recipeImageURL = poi.getPhoto();

        if (holder.identity == identity){

            Glide.with(context).
                    load(holder.recipeImageURL).
                    centerCrop().
                    crossFade().
                    into(holder.img);

        }

        double jarak = AppUtility.getJarak(GlobalVariable.getLocation(context).getLatitude(),
                GlobalVariable.getLocation(context).getLongitude(), poi.getLocation().getLatitude(),
                poi.getLocation().getLongitude());
        String distance = String.format("%.2f KM", jarak);

        holder.title.setText(poi.getTitle() +  "\n" + "(" + distance + ")");
        holder.desc.setText(poi.getAddress());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, desc;
        ImageView img;
        int position;
        String recipeImageURL;
        long identity;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);

            img = (ImageView) itemView.findViewById(R.id.img);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(null, v, position, 0);
            }
        }
    }
}
