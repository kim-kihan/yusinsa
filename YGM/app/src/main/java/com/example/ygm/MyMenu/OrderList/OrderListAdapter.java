package com.example.ygm.MyMenu.OrderList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.MyMenu.Inquiry.ProductInquiryActivity;
import com.example.ygm.MyMenu.Inquiry.ProductInquiryAdapter;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<OrderListItem> item;

    View v;

    public OrderListAdapter(ArrayList<OrderListItem> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.order_list_item, parent, false);
        return new OrderListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        OrderListItem item = this.item.get(position);
        Glide.with(v).load(item.getImage()).override(Target.SIZE_ORIGINAL).into(holder.ivProduct);
        holder.tvPname.setText(item.getName());
        holder.tvOption.setText("옵션 : " + item.getOption());
        holder.tvPrice.setText(new DecimalFormat("###,###,###,###").format(item.getAmount()) + "원");
        holder.tvNum.setText("수량 " + item.getNum() + "개");
        holder.btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, WriteReviewActivity.class);
                intent.putExtra("product", new ReviewItem(item.getPID(), item.getSID(), item.getImage(), item.getName(), item.getOption()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvPname;
        TextView tvOption;
        TextView tvPrice;
        TextView tvNum;
        Button btnWriteReview;

        public OrderListViewHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageView_ordierlist);
            tvPname = itemView.findViewById(R.id.textView_product_name_orderlist);
            tvOption = itemView.findViewById(R.id.textView_product_option_orderlist);
            tvPrice = itemView.findViewById(R.id.textView_price_orderlist);
            tvNum = itemView.findViewById(R.id.textView_num_orderlist);
            btnWriteReview = itemView.findViewById(R.id.button_write_review);
        }
    }
}
