package com.example.ygm.MyMenu.ServiceCenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQListViewHolder> {

    private ArrayList<FAQItem> item;

    public FAQAdapter(ArrayList<FAQItem> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public FAQListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.faq_item, parent, false);
        return new FAQListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQListViewHolder holder, int position) {
        FAQItem item = this.item.get(position);

        holder.tvCategory.setText("[" + item.getCategory() + "]");
        holder.tvTitle.setText(item.getTitle());
        holder.tvBody.setText(item.getBody());

        holder.faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isExpand()) {
                    holder.body.setVisibility(View.GONE);
                    item.setExpand(false);
                } else {
                    holder.body.setVisibility(View.VISIBLE);
                    item.setExpand(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class FAQListViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory;
        TextView tvTitle;
        TextView tvBody;

        LinearLayout faq;
        LinearLayout body;

        public FAQListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.textView_faq_category);
            tvTitle = itemView.findViewById(R.id.textView_faq_title);
            tvBody = itemView.findViewById(R.id.textView_faq_body);

            faq = itemView.findViewById(R.id.linearLayout_faq_item);
            body = itemView.findViewById(R.id.linearLayout_body_faq);
        }
    }
}
