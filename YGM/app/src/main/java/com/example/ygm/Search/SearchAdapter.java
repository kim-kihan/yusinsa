package com.example.ygm.Search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.ygm.DB.DB;
import com.example.ygm.Product.ProductActivity;
import com.example.ygm.R;
import com.example.ygm.User;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder> {

    private ArrayList<SearchResultItem> item;

    public String searchResult;

    public Context context;

    public SearchAdapter(ArrayList<SearchResultItem> item, String searchResult, Context context) {
        this.context = context;
        this.item = item;
        this.searchResult = searchResult;
    }

    @NonNull
    public SearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_list, parent, false);
        return new SearchAdapter.ItemViewHolder(v);
    }

    public void onBindViewHolder(@NonNull SearchAdapter.ItemViewHolder holder, int position) {
        holder.onBind(item.get(position), position);
    }

    public int getItemCount() {
        return item.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SearchResultItem data;

        private ImageView ivProduct;
        private TextView tvCategory, tvPname, tvPrice, tvFavorite, tvRate, tvSoldOut;
        private ImageView ivHeart;
        private RatingBar ratingBar;

        ItemViewHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.list_product_image);
            tvCategory = itemView.findViewById(R.id.list_product_category);
            tvPname = itemView.findViewById(R.id.list_product_contents);
            tvPrice = itemView.findViewById(R.id.list_product_price);
            tvFavorite = itemView.findViewById(R.id.list_product_like);
            tvRate = itemView.findViewById(R.id.ratingBar_product_list);
            ivHeart = itemView.findViewById(R.id.like_product);
            ratingBar = itemView.findViewById(R.id.ratingBar_product);
            tvSoldOut = itemView.findViewById(R.id.textView_soldout_product_list_item);
        }

        void onBind(SearchResultItem data, int position) {
            this.data = data;

            switch (Integer.parseInt(data.getCategory())) {
                case 100:
                    tvCategory.setText("상의 > 전체");
                    break;
                case 200:
                    tvCategory.setText("하의 > 전체");
                    break;
                case 300:
                    tvCategory.setText("신발 > 전체");
                    break;
                case 400:
                    tvCategory.setText("모자 > 전체");
                    break;
                case 101:
                    tvCategory.setText("상의 > 셔츠");
                    break;
                case 102:
                    tvCategory.setText("상의 > 아우터");
                    break;
                case 103:
                    tvCategory.setText("상의 > 후드/맨투맨");
                    break;
                case 104:
                    tvCategory.setText("상의 > 티셔츠");
                    break;
                case 201:
                    tvCategory.setText("하의 > 데님 팬츠");
                    break;
                case 202:
                    tvCategory.setText("하의 > 코튼 팬츠");
                    break;
                case 203:
                    tvCategory.setText("하의 > 슬랙스");
                    break;
                case 204:
                    tvCategory.setText("하의 > 트레이닝");
                    break;
                case 301:
                    tvCategory.setText("신발 > 캔버스/단화");
                    break;
                case 302:
                    tvCategory.setText("신발 > 러닝화/운동화");
                    break;
                case 303:
                    tvCategory.setText("신발 > 샌들/슬리퍼");
                    break;
                case 304:
                    tvCategory.setText("신발 > 로퍼");
                    break;
                case 401:
                    tvCategory.setText("모자 > 캡/야구모자");
                    break;
                case 402:
                    tvCategory.setText("모자 > 헌팅/베레");
                    break;
                case 403:
                    tvCategory.setText("모자 > 버킷/사파리햇");
                    break;
                case 404:
                    tvCategory.setText("모자 > 비니");
                    break;
            }

            if (item.size() > 0) {
                if (((User) ((Activity) context).getApplication()).getUID() != null) {
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {

                            }
                        }
                    }, (Activity) context, R.id.listView_search);
                    task.execute("http://15.165.152.15//InsertRecentsearch.php", ((User) ((Activity) context).getApplication()).getUID(), searchResult);
                }
            }

            Glide.with(itemView).load(data.getImg())
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                    .into(ivProduct);
            tvPname.setText(data.getPname());
            tvPrice.setText(new DecimalFormat("###,###,###,###").format(data.getPrice()) + "원");
            tvFavorite.setText(String.valueOf(data.getLcount()));
            ratingBar.setRating(data.getStar());
            tvRate.setText(String.valueOf(data.getRcount()));

            if (data.getStock() == 0) {
                ivProduct.setAlpha(32);
                tvSoldOut.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(this);
            tvCategory.setOnClickListener(this);
            tvPname.setOnClickListener(this);
            tvPrice.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.list_product_image:
                case R.id.list_product_category:
                case R.id.list_product_contents:
                case R.id.list_product_price:
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("PID", data.getPID());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}