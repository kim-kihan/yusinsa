package com.example.ygm.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class PushAlarmAdapter extends RecyclerView.Adapter<PushAlarmAdapter.PushAlarmViewHolder> {

    ArrayList<PushAlarmItem> item;

    PushAlarmAdapter(ArrayList<PushAlarmItem> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public PushAlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.push_alarm_item, parent, false);
        return new PushAlarmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PushAlarmViewHolder holder, int position) {
        PushAlarmItem item = this.item.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDate.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class PushAlarmViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDate;

        public PushAlarmViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textView_title_push_alarm);
            tvDate = itemView.findViewById(R.id.textView_date_push_alarm);
        }
    }
}
