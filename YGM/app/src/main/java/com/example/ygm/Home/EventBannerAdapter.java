package com.example.ygm.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EventBannerAdapter extends RecyclerView.Adapter<EventBannerAdapter.SliderViewHolder> {

    ArrayList<EventBannerItem> item;

    View v;

    Activity activity;

    public EventBannerAdapter(ArrayList<EventBannerItem> item, Activity activity) {
        this.item = item;
        this.activity = activity;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.event_banner_item, parent, false);
        return new SliderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder holder, int position) {
        EventBannerItem item = this.item.get(position % this.item.size());
        Glide.with(v).load(item.getThumbnail()).override(Target.SIZE_ORIGINAL).into(holder.ivBanner);
        holder.ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity ,EventActivity.class);
                intent.putExtra("eventPK", item.getPK());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return Integer.MAX_VALUE; }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBanner;

        public SliderViewHolder(View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.imageView_event_banner);
        }
    }
}
