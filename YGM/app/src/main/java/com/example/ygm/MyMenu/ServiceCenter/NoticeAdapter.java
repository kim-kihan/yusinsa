package com.example.ygm.MyMenu.ServiceCenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    ArrayList<NoticeItem> item;

    NoticeAdapter(ArrayList<NoticeItem> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.notice_item, parent, false);
        return new NoticeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        NoticeItem item = this.item.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvDate.setText(item.date);
        holder.ivExpand.setImageResource(R.drawable.expand_more);
        holder.tvBody.setText(item.body);

        holder.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.expand) {
                    holder.body.setVisibility(View.GONE);
                    holder.ivExpand.setImageResource(R.drawable.expand_more);
                    item.expand = false;
                } else {
                    holder.body.setVisibility(View.VISIBLE);
                    holder.ivExpand.setImageResource(R.drawable.expand_less);
                    item.expand = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDate;
        ImageView ivExpand;
        TextView tvBody;

        ConstraintLayout notice;
        LinearLayout body;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView_notice_title);
            tvDate = itemView.findViewById(R.id.textView_notice_date);
            ivExpand = itemView.findViewById(R.id.imageView_notice_expand);
            tvBody = itemView.findViewById(R.id.textView_notice_body);

            notice = itemView.findViewById(R.id.constraintLayout_notice_item);
            body = itemView.findViewById(R.id.linearLayout_body_notice);
        }
    }
}
