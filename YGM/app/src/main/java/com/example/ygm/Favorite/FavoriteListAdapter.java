package com.example.ygm.Favorite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.Product.ProductActivity;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder> {

    private ArrayList<FavoriteListItem> item;

    View v;

    Activity activity;

    public FavoriteListAdapter(ArrayList<FavoriteListItem> item, Activity activity) {
        this.item = item;
        this.activity = activity;
    }

    @Override
    public FavoriteListViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.favorite_item, parent, false);
        return new FavoriteListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoriteListViewHolder holder, int position) {
        FavoriteListItem item = this.item.get(position);
        Glide.with(v).load(item.getImg()).override(Target.SIZE_ORIGINAL).into(holder.ivProduct);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(new DecimalFormat("###,###,###,###").format(item.getPrice()) + "원");
        holder.tvNum.setText(new DecimalFormat("###,###,###,###").format(Integer.parseInt(item.getNum())));
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductActivity.class);
                intent.putExtra("PID", item.getPK());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class FavoriteListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvName, tvPrice, tvNum;
        LinearLayout item;

        public FavoriteListViewHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageView_favorite);
            tvName = itemView.findViewById(R.id.textView_product_name_favorite);
            tvPrice = itemView.findViewById(R.id.textView_price_favorite);
            tvNum = itemView.findViewById(R.id.textView_num_favorite);
            item = itemView.findViewById(R.id.linearLayout_favorite);
        }
    }
}
