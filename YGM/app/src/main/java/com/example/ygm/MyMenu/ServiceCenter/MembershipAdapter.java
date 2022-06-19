package com.example.ygm.MyMenu.ServiceCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.MembershipListViewHolder> {

    ArrayList<MembershipItem> item;

    Activity activity;

    public MembershipAdapter(ArrayList<MembershipItem> item, Activity activity) {
        this.item = item;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MembershipListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.membership_item, parent, false);
        return new MembershipListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MembershipListViewHolder holder, int position) {
        MembershipItem item = this.item.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MembershipBodyActivity.class);
                intent.putExtra("membershipTitle", item.title);
                intent.putExtra("membershipBody", item.body);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class MembershipListViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        public MembershipListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView_membership_title);
        }
    }
}
