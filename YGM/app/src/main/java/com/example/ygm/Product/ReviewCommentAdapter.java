package com.example.ygm.Product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class ReviewCommentAdapter extends RecyclerView.Adapter<ReviewCommentAdapter.ItemViewHolder> {
    private ArrayList<CommentItem> listData;
    String s4;

    public ReviewCommentAdapter(ArrayList<CommentItem> listData) {
        this.listData = listData;
    }

    @NonNull
    public ReviewCommentAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review_comment, parent, false);
        return new ReviewCommentAdapter.ItemViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ReviewCommentAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position), position);
    }

    public int getItemCount() {
        return listData.size();
    }

    public void addItem(CommentItem data) {
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvLevel;
        private TextView tvUserName;
        private TextView tvDate;
        private TextView tvContent;
        private CommentItem data;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvLevel = itemView.findViewById(R.id.textView2);
            tvUserName = itemView.findViewById(R.id.textView9);
            tvDate = itemView.findViewById(R.id.textView10);
            tvContent = itemView.findViewById(R.id.textView11);
        }

        void onBind(CommentItem data, int position) {
            this.data = data;
            this.position = position;

            if (Integer.parseInt(data.getLevel()) <= 2000) {
                s4 = "1";
            } else if (Integer.parseInt(data.getLevel()) <= 10000 && Integer.parseInt(data.getLevel()) > 2000) {
                s4 = "2";
            } else if (Integer.parseInt(data.getLevel()) > 10000 && Integer.parseInt(data.getLevel()) <= 100000) {
                s4 = "3";
            } else if (Integer.parseInt(data.getLevel()) > 100000 && Integer.parseInt(data.getLevel()) <= 200000) {
                s4 = "4";
            } else if (Integer.parseInt(data.getLevel()) > 200000 && Integer.parseInt(data.getLevel()) <= 500000) {
                s4 = "5";
            } else if (Integer.parseInt(data.getLevel()) > 500000 && Integer.parseInt(data.getLevel()) <= 1000000) {
                s4 = "6";
            } else if (Integer.parseInt(data.getLevel()) < 1000000 && Integer.parseInt(data.getLevel()) <= 2000000) {
                s4 = "7";
            } else {
                s4 = "8";
            }

            tvLevel.setText("Lv " + s4);
            tvUserName.setText(data.getName());
            tvDate.setText(data.getDate());
            tvContent.setText(data.getContent());
        }

        @Override
        public void onClick(View v) {

        }
    }
}