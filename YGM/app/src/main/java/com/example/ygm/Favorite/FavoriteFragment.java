package com.example.ygm.Favorite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    ArrayList<FavoriteListItem> item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        MainActivity.navView.setVisibility(View.VISIBLE);
        MainActivity.title.setText("좋아요");

        if (((User) getActivity().getApplication()).getUID() == null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout_favorite, new EmptyFavoriteFragment()).commit();
        } else {
            item = new ArrayList<>();
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        if (item.size() == 0) {
                            transaction.replace(R.id.frameLayout_favorite, new EmptyFavoriteFragment()).commit();
                        } else {
                            transaction.replace(R.id.frameLayout_favorite, new NonEmptyFavoriteFragment(item)).commit();
                        }
                    }
                }
            }, getActivity(), R.id.recyclerView_favorite, item);
            task.execute("http://15.165.152.15//ShowLike.php", ((User) getActivity().getApplication()).getUID());
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    if (item.size() == 0) {
                        transaction.replace(R.id.frameLayout_favorite, new EmptyFavoriteFragment()).commit();
                    } else {
                        transaction.replace(R.id.frameLayout_favorite, new NonEmptyFavoriteFragment(item)).commit();
                    }
                }
            }
        }, getActivity(), R.id.recyclerView_favorite, item);
        task.execute("http://15.165.152.15//ShowLike.php", ((User) getActivity().getApplication()).getUID());
    }
}