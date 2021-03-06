package com.example.ygm.ProductList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.Collections;
import java.util.Comparator;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ItemViewHolder> {

    private ArrayList<ProductListItem> item;

    private Context context;

    ProductListAdapter(ArrayList<ProductListItem> item, Context context) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    public ProductListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_list, parent, false);
        return new ProductListAdapter.ItemViewHolder(v);
    }

    public void onBindViewHolder(@NonNull ProductListAdapter.ItemViewHolder holder, int position) {
        holder.onBind(item.get(position), position);
    }

    public int getItemCount() {
        return item.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivProduct;
        private TextView tvCategory, tvPname, tvPrice, tvFavorite, tvRate, tvSoldOut;
        private ImageView ivHeart;
        private RatingBar ratingBar;
        private ProductListItem data;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);

            ivProduct = itemView.findViewById(R.id.list_product_image);
            tvCategory = itemView.findViewById(R.id.list_product_category);
            tvPname = itemView.findViewById(R.id.list_product_contents);
            tvPrice = itemView.findViewById(R.id.list_product_price);
            ivHeart = itemView.findViewById(R.id.like_product);
            tvFavorite = itemView.findViewById(R.id.list_product_like);
            ratingBar = itemView.findViewById(R.id.ratingBar_product);
            tvRate = itemView.findViewById(R.id.ratingBar_product_list);
            tvSoldOut = itemView.findViewById(R.id.textView_soldout_product_list_item);
        }

        void onBind(ProductListItem data, int position) {
            this.data = data;
            this.position = position;
            switch (Integer.parseInt(data.getCategory())) {
                case 100:
                    tvCategory.setText("?????? > ??????");
                    break;
                case 200:
                    tvCategory.setText("?????? > ??????");
                    break;
                case 300:
                    tvCategory.setText("?????? > ??????");
                    break;
                case 400:
                    tvCategory.setText("?????? > ??????");
                    break;
                case 101:
                    tvCategory.setText("?????? > ??????");
                    break;
                case 102:
                    tvCategory.setText("?????? > ?????????");
                    break;
                case 103:
                    tvCategory.setText("?????? > ??????/?????????");
                    break;
                case 104:
                    tvCategory.setText("?????? > ?????????");
                    break;
                case 201:
                    tvCategory.setText("?????? > ?????? ??????");
                    break;
                case 202:
                    tvCategory.setText("?????? > ?????? ??????");
                    break;
                case 203:
                    tvCategory.setText("?????? > ?????????");
                    break;
                case 204:
                    tvCategory.setText("?????? > ????????????");
                    break;
                case 301:
                    tvCategory.setText("?????? > ?????????/??????");
                    break;
                case 302:
                    tvCategory.setText("?????? > ?????????/?????????");
                    break;
                case 303:
                    tvCategory.setText("?????? > ??????/?????????");
                    break;
                case 304:
                    tvCategory.setText("?????? > ??????");
                    break;
                case 401:
                    tvCategory.setText("?????? > ???/????????????");
                    break;
                case 402:
                    tvCategory.setText("?????? > ??????/??????");
                    break;
                case 403:
                    tvCategory.setText("?????? > ??????/????????????");
                    break;
                case 404:
                    tvCategory.setText("?????? > ??????");
                    break;
            }

            tvPname.setText(data.getPname());
            Glide.with(itemView).load(data.getImg())
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL) //?????? ?????? ??? ????????? ????????? ???????????? ??????
                    .into(ivProduct);
            tvPrice.setText(new DecimalFormat("###,###,###,###").format(data.getPrice()) + "???");
            tvFavorite.setText(String.valueOf(data.getLcount()));
            ratingBar.setRating(data.getStar());
            tvRate.setText(String.valueOf(data.getRcount()));

            itemView.setOnClickListener(this);
            tvCategory.setOnClickListener(this);
            tvPname.setOnClickListener(this);
            tvPrice.setOnClickListener(this);

            if (data.getStock() == 0) {
                ivProduct.setAlpha(32);
                tvSoldOut.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.list_product_category:
                case R.id.list_product_contents:
                case R.id.list_product_image:
                case R.id.body:
                case R.id.list_product_price:
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("PID", data.getPID());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
