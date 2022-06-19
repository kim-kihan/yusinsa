package com.example.ygm.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RankingFragment extends Fragment {

    ArrayList<RankingListItem> item;

    RecyclerView rankingList;
    ImageView changeView;

    int view = 0;

    TextView tvNum;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ranking, container, false);

        MainActivity.title.setText("상품 랭킹");

        tvNum = v.findViewById(R.id.textView_num_ranking_fragment);

        changeView = v.findViewById(R.id.imageButton_change_view);
        changeView.setImageResource(R.drawable.icon_gridview);
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(view) {
                    case 0: // Grid
                        rankingList.setLayoutManager(new LinearLayoutManager(getContext()));
                        rankingList.setAdapter(new RankingAdapter(item, LinearLayout.HORIZONTAL, getActivity()));
                        changeView.setImageResource(R.drawable.icon_listview);
                        break;
                    case 1: // Linear
                        rankingList.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        rankingList.setAdapter(new RankingAdapter(item, LinearLayout.VERTICAL, getActivity()));
                        changeView.setImageResource(R.drawable.icon_gridview);
                        break;
                }
                view = (view + 1) % 2;
            }
        });

        rankingList = v.findViewById(R.id.recyclerView_rankinglist);
        rankingList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    tvNum.setText(new DecimalFormat("###,###,###,###").format(item.size()));
                    rankingList.setAdapter(new RankingAdapter(item, LinearLayout.VERTICAL, getActivity()));
                }
            }
        }, getActivity(), R.id.recyclerView_rankinglist, item);
        task.execute("http://15.165.152.15//ShowRankALL.php");

        return v;
    }
}