package com.example.ygm.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.R;

import java.util.ArrayList;

public class ProductSliderAdapter extends RecyclerView.Adapter<ProductSliderAdapter.ProductSliderViewHolder> {

    ArrayList<ProductImageItem> item;

    View v;

    ProductSliderAdapter(ArrayList<ProductImageItem> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public ProductSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.event_banner_item, parent, false);
        return new ProductSliderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSliderViewHolder holder, int position) {
        ProductImageItem item = this.item.get(position);
        Glide.with(v).load(item.getImg()).override(Target.SIZE_ORIGINAL).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ProductSliderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ProductSliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_event_banner);
        }
    }
}
