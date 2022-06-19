package com.example.ygm.MyMenu.Inquiry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class OneToOneInquiryAdapter extends RecyclerView.Adapter<OneToOneInquiryAdapter.OneToOneInquiryListViewHolder> {

    ArrayList<OneToOneInquiryItem> item;

    public OneToOneInquiryAdapter(ArrayList<OneToOneInquiryItem> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public OneToOneInquiryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.onetoone_inquiry_item, parent, false);
        return new OneToOneInquiryListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OneToOneInquiryListViewHolder holder, int position) {
        OneToOneInquiryItem item = this.item.get(position);
        holder.tvStatus1.setText(item.getStatus());
        holder.tvType.setText(item.getType());
        holder.tvTitle.setText(item.getTitle());
        holder.tvBody.setText(item.getBody());
        holder.tvDate1.setText(item.getInquireDate());
        holder.tvStatus2.setText(item.getStatus());
        holder.tvAnswer.setText(item.getAnswer());
        holder.tvDate2.setText(item.getAnswerDate());

        if (item.getAnswer() == "null") {
            holder.answer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class OneToOneInquiryListViewHolder extends RecyclerView.ViewHolder {

        TextView tvStatus1, tvType, tvTitle, tvBody, tvDate1, tvStatus2, tvAnswer, tvDate2;

        ConstraintLayout answer;

        public OneToOneInquiryListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus1 = itemView.findViewById(R.id.textView_status_onetoone_inquiry1);
            tvType = itemView.findViewById(R.id.textView_inquiry_type_onetoone_inquiry);
            tvTitle = itemView.findViewById(R.id.textView_title_onetoone_inquiry);
            tvBody = itemView.findViewById(R.id.textView_body_onetoone_inquiry);
            tvDate1 = itemView.findViewById(R.id.textView_inquire_date_onetoone_inquiry);
            tvStatus2 = itemView.findViewById(R.id.textView_status_onetoone_inquiry2);
            tvAnswer = itemView.findViewById(R.id.textView_answer_onetoone_inquiry);
            tvDate2 = itemView.findViewById(R.id.textView_answer_date_onetoone_inquiry);

            answer = itemView.findViewById(R.id.constraintLayout_answer_onetoone_inquiry);
        }
    }
}
