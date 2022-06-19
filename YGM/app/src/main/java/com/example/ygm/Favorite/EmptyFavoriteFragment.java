package com.example.ygm.Favorite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ygm.MainActivity;
import com.example.ygm.R;

public class EmptyFavoriteFragment extends Fragment {

    Button btnRanking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_empty_favorite, container, false);

        btnRanking = v.findViewById(R.id.button_look_ranking);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(R.id.action_favoriteFragment_to_rankingFragment);
            }
        });

        return v;
    }
}