package com.example.ygm.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.Product.ProductActivity;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HomeRankingGridAdapter extends RecyclerView.Adapter<HomeRankingGridAdapter.HomeRankingGridViewHolder> {

    HomeRankingGridItem[] item;

    View v;

    Context context;

    public HomeRankingGridAdapter(HomeRankingGridItem[] item) {
        this.item = item;
    }

    @Override
    public HomeRankingGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.home_ranking_grid_item, parent, false);
        return new HomeRankingGridViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeRankingGridViewHolder holder, int position) {
        HomeRankingGridItem item = this.item[position];
        holder.tvRank.setText((item.getRank()) + "위");
        Glide.with(v).load(item.getImg()).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).into(holder.ivProduct);
        holder.tvPrice.setText(new DecimalFormat("###,###,###,###").format(item.getPrice()) + "원");
        holder.tvPname.setText(item.getName());
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("PID", item.getPK());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return item.length; }

    public class HomeRankingGridViewHolder extends RecyclerView.ViewHolder {

        TextView tvRank;
        ImageView ivProduct;
        TextView tvPrice;
        TextView tvPname;

        ConstraintLayout product;

        public HomeRankingGridViewHolder(View itemView) {
            super(itemView);
            tvRank = itemView.findViewById(R.id.textView_home_ranking_rank);
            ivProduct = itemView.findViewById(R.id.imageView_home_ranking);
            tvPrice = itemView.findViewById(R.id.textView_home_ranking_price);
            tvPname = itemView.findViewById(R.id.textView_product_name_home_rank);

            product = itemView.findViewById(R.id.constraintLayout_home_ranking_product);
        }
    }
}
