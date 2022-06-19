package com.example.ygm.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.Product.ProductActivity;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    ArrayList<RankingListItem> item;

    int orientation;

    View v;

    Activity activity;

    RankingAdapter(ArrayList<RankingListItem> item, int orientation, Activity activity) {
        this.item = item;
        this.orientation = orientation;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.ranking_list_item, parent, false);
        return new RankingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        RankingListItem item = this.item.get(position);
        holder.tvRank.setText((position + 1) + "위");
        Glide.with(v).load(item.getImg()).override(Target.SIZE_ORIGINAL).into(holder.ivProduct);
        holder.tvPname.setText(item.getName());
        holder.tvPrice.setText(new DecimalFormat("###,###,###,###").format(item.getPrice()) + "원");
        int favorite = item.getFavorite();
        if (favorite > 0) {
            holder.tvFavorite.setText((favorite < 1000) ? new DecimalFormat("###,###,###,###").format(favorite) : "999+");
        } else {
            holder.favorite.setVisibility(View.GONE);
        }
        int review = item.getReview();
        if (review > 0) {
            holder.tvReview.setText((review < 1000) ? new DecimalFormat("###,###,###,###").format(review) : "999+");
        } else {
            holder.rate.setVisibility(View.GONE);
        }
        holder.ratingBar.setRating(item.getRate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductActivity.class);
                intent.putExtra("PID", item.getPK());
                activity.startActivity(intent);
            }
        });

        holder.product.setOrientation(orientation);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {

        TextView tvRank;
        ImageView ivProduct;
        TextView tvPname;
        TextView tvPrice;
        TextView tvFavorite;
        TextView tvReview;
        RatingBar ratingBar;

        LinearLayout favorite;
        LinearLayout rate;

        LinearLayout product;

        public RankingViewHolder(View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.textView_productlist_item_rank);
            ivProduct = itemView.findViewById(R.id.imageView_productlist_item);
            tvPname = itemView.findViewById(R.id.textView_productlist_item_name);
            tvPrice = itemView.findViewById(R.id.textView_productlist_item_price);
            tvFavorite = itemView.findViewById(R.id.textView_productlist_item_favorite);
            tvReview = itemView.findViewById(R.id.textView_productlist_item_review);
            ratingBar = itemView.findViewById(R.id.ratingBar_productlist_item);

            favorite = itemView.findViewById(R.id.linearLayout_productlist_item_favorite);
            rate = itemView.findViewById(R.id.linearLayout_productlist_item_rate);

            product = itemView.findViewById(R.id.linearLayour_productlist_item);
        }
    }
}
