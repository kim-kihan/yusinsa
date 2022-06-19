package com.example.ygm.Favorite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygm.R;

import java.util.ArrayList;

public class NonEmptyFavoriteFragment extends Fragment {

    RecyclerView favoriteList;

    ArrayList<FavoriteListItem> item;

    NonEmptyFavoriteFragment(ArrayList<FavoriteListItem> item) {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nonempty_favorite, container, false);

        favoriteList = v.findViewById(R.id.recyclerView_favorite);
        favoriteList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        favoriteList.setAdapter(new FavoriteListAdapter(item, getActivity()));

        return v;
    }
}