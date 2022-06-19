package com.example.ygm.MyMenu.Inquiry;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.User;
import com.example.ygm.R;

import java.util.ArrayList;

public class ProductInquiryAdapter extends RecyclerView.Adapter<ProductInquiryAdapter.ProductInquiryListViewHolder>{

    ArrayList<ProductInquiryItem> item;

    Activity activity;

    public ProductInquiryAdapter(ArrayList<ProductInquiryItem> item, Activity activity) {
        this.item = item;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductInquiryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.product_inquiry_item, parent, false);
        return new ProductInquiryListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInquiryListViewHolder holder, int position) {
        ProductInquiryItem item = this.item.get(position);
        holder.tvStatus.setText(item.getStatus());
        holder.tvType.setText(item.getType());
        holder.tvTitle.setText(item.getTitle());
        holder.tvBody.setText(item.getBody());
        holder.tvDate1.setText(item.getInquireDate());
        holder.tvAnswer.setText(item.getAnswer());
        holder.tvDate2.setText(item.getAnswerDate());
        holder.tvName.setText(item.getProductName());

        holder.Inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.body.setVisibility((holder.body.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
            }
        });


        if (item.getAnswer() == "null") {
            holder.answer.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#03A9F4"));
        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ProductInquiryListViewHolder extends RecyclerView.ViewHolder {

        TextView tvStatus, tvType, tvTitle, tvBody, tvDate1, tvAnswer, tvDate2, tvName;

        LinearLayout Inquiry, body, answer;

        public ProductInquiryListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.textView_status_product_inquiry);
            tvType = itemView.findViewById(R.id.textView_type_product_inquiry);
            tvTitle = itemView.findViewById(R.id.textView_title_product_inquiry);
            tvBody = itemView.findViewById(R.id.textView_body_product_inquiry);
            tvDate1 = itemView.findViewById(R.id.textView_inquire_date_product_inquiry);
            tvAnswer = itemView.findViewById(R.id.textView_answer_product_inquiry);
            tvDate2 = itemView.findViewById(R.id.textView_answer_date_product_inquiry);
            tvName = itemView.findViewById(R.id.textView_product_name_product_inquiry);

            Inquiry = itemView.findViewById(R.id.linearLayout_product_inquiry);
            body = itemView.findViewById(R.id.linearLayout_product_inquiry_body);
            answer = itemView.findViewById(R.id.linearLayout_answer_product_inquiry);
        }
    }
}
