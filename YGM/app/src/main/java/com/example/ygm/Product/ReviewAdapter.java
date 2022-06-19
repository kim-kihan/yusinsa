package com.example.ygm.Product;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.DB.DB;
import com.example.ygm.LogIn.LogInActivity;
import com.example.ygm.R;
import com.example.ygm.User;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {

    private Activity context;
    private ArrayList<ReviewItem> listData;
    private ArrayList<CommentItem> output = new ArrayList<>();
    private String s2, s3, s4;
    private ReviewCommentAdapter adapter;
    int chk = 0;


    public ReviewAdapter(Activity context, ArrayList<ReviewItem> listData, String s2) {
        this.context = context;
        this.listData = listData;
        this.s2 = s2;
        this.s3 = ((User) context.getApplication()).getID();
        this.s4 = String.valueOf(((User) context.getApplication()).getExp());
    }

    @NonNull
    public ReviewAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_review, parent, false);
        return new ReviewAdapter.ItemViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ReviewAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position), position);
    }

    public int getItemCount() {
        return listData.size();
    }

    public void addItem(ReviewItem data) {
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvLevel;
        private TextView tvName;
        private TextView tvDate;
        private TextView textView4;
        private TextView textView5;
        private TextView textView6;
        private TextView tvWeight;
        private TextView tvCountComment;
        private ImageView tvProductImage;
        private ImageView tvProductImageReview;
        LinearLayoutManager linearLayoutManager;
        private RecyclerView commentList;
        private RatingBar ratingBar;
        private ReviewItem data;
        private Button configureComment;
        private int position;
        private EditText comment;

        ArrayList<CommentItem> arrayList;


        ItemViewHolder(View itemView) {
            super(itemView);

            tvLevel = itemView.findViewById(R.id.level_list_review);
            tvName = itemView.findViewById(R.id.name_list_review);
            tvDate = itemView.findViewById(R.id.date_list_review);
            tvWeight = itemView.findViewById(R.id.weight_product_list_review);
            tvProductImage = itemView.findViewById(R.id.photo_product_list_review);
            textView4 = itemView.findViewById(R.id.title_product_list_review);
            textView5 = itemView.findViewById(R.id.size_product_list_review);
            ratingBar = itemView.findViewById(R.id.ratingBar_product_list_review);
            tvProductImageReview = itemView.findViewById(R.id.photo_list_review);
            textView6 = itemView.findViewById(R.id.content_list_review);
            tvCountComment = itemView.findViewById(R.id.textView8);
            configureComment = itemView.findViewById(R.id.button5);
            commentList = itemView.findViewById(R.id.recyclerView_list_review);
            comment = itemView.findViewById(R.id.write_comment_review_product);
        }

        void onBind(ReviewItem data, int position) {
            this.data = data;
            this.position = position;

            tvLevel.setText("Lv " + data.getLevel());
            tvName.setText(data.getName());
            tvDate.setText(data.getDate());
            tvWeight.setText(data.getHeight() + " / " + data.getWeight());
            if (!data.getResId().equals("null")) {
                Glide.with(context).load(data.getResId())
                        .fitCenter()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                        .into(tvProductImage);
            }
            textView4.setText(data.getTitle());
            textView5.setText(data.getSize());
            if (!data.getResId().equals("null")) {
                Glide.with(context).load(data.getReviewId())
                        .fitCenter()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                        .into(tvProductImageReview);
            }

            ratingBar.setRating(Float.parseFloat(data.getRatingnum()));
            textView6.setText(data.getContent());

            arrayList = new ArrayList<>();
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        commentList.setLayoutManager(new LinearLayoutManager(context));
                        commentList.setAdapter(new ReviewCommentAdapter(arrayList));
                        tvCountComment.setText("댓글 " + arrayList.size());
                    }
                }
            }, (Activity) context, R.id.recyclerView_list_review, arrayList);
            task.execute("http://15.165.152.15//ShowProduct6.php", listData.get(position).getRID());

            configureComment.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (!comment.getText().toString().equals("")) {
                if (((User) context.getApplication()).getUID() == null) {
                    Intent intent = new Intent(context, LogInActivity.class);
                    intent.putExtra("Value", data.getPID());
                    context.startActivity(intent);
                    return;
                }
                if (Integer.parseInt(s4) <= 2000) {
                    s4 = "1";
                } else if (Integer.parseInt(s4) <= 10000 && Integer.parseInt(s4) > 2000) {
                    s4 = "2";
                } else if (Integer.parseInt(s4) > 10000 && Integer.parseInt(s4) <= 100000) {
                    s4 = "3";
                } else if (Integer.parseInt(s4) > 100000 && Integer.parseInt(s4) <= 200000) {
                    s4 = "4";
                } else if (Integer.parseInt(s4) > 200000 && Integer.parseInt(s4) <= 500000) {
                    s4 = "5";
                } else if (Integer.parseInt(s4) > 500000 && Integer.parseInt(s4) <= 1000000) {
                    s4 = "6";
                } else if (Integer.parseInt(s4) < 1000000 && Integer.parseInt(s4) <= 2000000) {
                    s4 = "7";
                } else {
                    s4 = "8";
                }
                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            comment.setText(null);
                            DB task2 = new DB(new DB.OnResultListener() {
                                @Override
                                public void onComplete(boolean result) {
                                    if (!result) {
                                        commentList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                        tvCountComment.setText("댓글 " + output.size());
                                        adapter = new ReviewCommentAdapter(output);
                                        commentList.setAdapter(adapter);
                                    }
                                }
                            }, (Activity) context, R.id.recyclerView_list_review, output);
                            task2.execute("http://15.165.152.15//ShowProduct6.php", data.getRID());
                        }
                    }
                }, (Activity) context, R.id.write_comment_review_product);
                task.execute("http://15.165.152.15//InsertComment.php", data.getRID(), ((User) context.getApplication()).getID(), String.valueOf(((User) context.getApplication()).getExp()), comment.getText().toString());
            } else {
                if (((User) context.getApplication()).getUID() == null) {
                    Intent intent = new Intent(context, LogInActivity.class);
                    intent.putExtra("Value", data.getPID());
                    context.startActivity(intent);
                    return;
                }
                Toast toast1 = Toast.makeText(context.getApplicationContext(), "댓글을 입력해주세요", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }

}