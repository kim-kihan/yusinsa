package com.example.ygm.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.PaymentListViewHolder> {

    private ArrayList<PaymentItem> item;

    View v;

    public PaymentListAdapter(ArrayList<PaymentItem> item) {
        this.item = item;
    }

    @Override
    public PaymentListViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.payment_list_item, parent, false);
        return new PaymentListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PaymentListViewHolder holder, int position) {
        PaymentItem item = this.item.get(position);
        Glide.with(v).load(item.getImg()).override(Target.SIZE_ORIGINAL).into(holder.ivProduct);
        holder.tvPname.setText(item.getName());
        holder.tvOption.setText(String.format("옵션 %s SIZE", item.getOption()));
        holder.tvNum.setText(String.format("수량 %d개", item.getNum()));
        holder.tvPrice.setText(new DecimalFormat("###,###,###,###").format(item.getPrice()) + "원");
        holder.tvPoint.setText(new DecimalFormat("###,###,###,###").format((item.getPrice() * item.getNum()) * 8 / 100) + "원적립");
        holder.tvTotal.setText(new DecimalFormat("###,###,###,###").format(item.getPrice() * item.getNum()) + "원");
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class PaymentListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvPname, tvOption, tvNum, tvPrice, tvPoint, tvTotal;

        public PaymentListViewHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageView_product_payment);
            tvPname = itemView.findViewById(R.id.textView_product_name_payment);
            tvOption = itemView.findViewById(R.id.textView_product_option_payment);
            tvNum = itemView.findViewById(R.id.textView_product_num_payment);
            tvPrice = itemView.findViewById(R.id.textView_product_price_payment);
            tvPoint = itemView.findViewById(R.id.textView_product_point_payment);
            tvTotal = itemView.findViewById(R.id.textView_product_total_payment);
        }
    }
}
