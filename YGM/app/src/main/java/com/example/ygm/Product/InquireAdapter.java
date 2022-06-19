package com.example.ygm.Product;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.R;

import java.util.ArrayList;

public class InquireAdapter extends RecyclerView.Adapter<InquireAdapter.ItemViewHolder> {

    private ArrayList<InquireItem> item;
    Context context;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public InquireAdapter(ArrayList<InquireItem> item) {
        this.item = item;
    }

    @NonNull
    public InquireAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_qna, parent, false);
        return new InquireAdapter.ItemViewHolder(view);
    }

    public void onBindViewHolder(@NonNull InquireAdapter.ItemViewHolder holder, int position) {
        holder.onBind(item.get(position), position);
    }

    public int getItemCount() {
        return item.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvAnswerState;
        private TextView tvProductTitle;
        private TextView tvQnAOption;
        private TextView tvDate;
        private TextView tvID;
        private TextView tvQuestion;
        private TextView tvAnswer;
        private ImageView ivQuestionImage;
        private TextView tvAnswerDate;
        private LinearLayout linearLayoutAnswer;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);

            tvAnswerState = itemView.findViewById(R.id.textView3);
            tvProductTitle = itemView.findViewById(R.id.textView4);
            tvQnAOption = itemView.findViewById(R.id.textView5);
            tvDate = itemView.findViewById(R.id.textView6);
            linearLayoutAnswer = itemView.findViewById(R.id.answer_product_qna);
            tvID = itemView.findViewById(R.id.textView12);
            ivQuestionImage = itemView.findViewById(R.id.question_product_qna);
            tvQuestion = itemView.findViewById(R.id.textView13);
            tvAnswer = itemView.findViewById(R.id.textView14);
            tvAnswerDate = itemView.findViewById(R.id.textView15);
        }

        void onBind(InquireItem data, int position) {
            this.position = position;

            if (data.getAnswer() != "null") {
                tvAnswerState.setText("답변완료");
                tvAnswerState.setTextColor(Color.parseColor("#FF00BCD4"));
            } else {
                tvAnswerState.setText("답변예정");
                tvAnswerState.setTextColor(Color.parseColor("#000000"));
            }

            tvProductTitle.setText(data.getTitle());
            tvQnAOption.setText(data.getCategory());
            tvDate.setText(data.getDate());
            tvID.setText(data.getId());
            tvQuestion.setText(data.getQuestion());
            Glide.with(context).load(data.getImage())
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                    .into(ivQuestionImage);
            tvAnswer.setText(data.getAnswer());
            tvAnswerDate.setText(data.getAnswerdate());

            changeVisibility(selectedItems.get(position));

            tvAnswerState.setOnClickListener(this);
            tvProductTitle.setOnClickListener(this);
            tvQnAOption.setOnClickListener(this);
            tvDate.setOnClickListener(this);
            tvID.setOnClickListener(this);
            tvQuestion.setOnClickListener(this);
            tvAnswer.setOnClickListener(this);
            tvAnswerDate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.textView3:
                case R.id.textView6:
                case R.id.textView4:
                case R.id.textView5:
                case R.id.textView12:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }
        }

        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            tvQuestion.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            if(tvAnswerState.getText().toString().equals("답변예정")){
                linearLayoutAnswer.setVisibility(View.GONE);
            }else{
                linearLayoutAnswer.setVisibility(View.VISIBLE);
                tvAnswer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }

            tvAnswerDate.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }
}