package com.example.ygm.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.R;

import java.util.ArrayList;

public class HomeRankingSliderAdapter extends RecyclerView.Adapter<HomeRankingSliderAdapter.SliderViewHolder> {

    HomeRankingGridItem[][] item;
    Context context;

    public HomeRankingSliderAdapter(ArrayList<HomeRankingGridItem> item) {
        this.item = new HomeRankingGridItem[6][];
        for (int i = 0; i < 6; i++) {
            this.item[i] = new HomeRankingGridItem[6];
        }

        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                this.item[j][k] = item.get(j * 6 + k);
            }
        }

        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 6; k++) {
                this.item[j][k].setRank(j * 6 + k + 1);
            }
        }
        for (int j = 2; j < 6; j++) {
            for (int k = 0; k < 6; k++) {
                this.item[j][k].setRank(k + 1);
            }
        }
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.home_ranking_grid_view, parent, false);
        return new SliderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder holder, int position) {
        holder.rankingView.setAdapter(new HomeRankingGridAdapter(item[position % 6]));
        holder.rankingView.setLayoutManager(new GridLayoutManager(context, 3));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rankingView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            rankingView = itemView.findViewById(R.id.gridView_home_ranking);
        }
    }
}
