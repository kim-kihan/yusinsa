package com.example.ygm.Search;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygm.DB.DB;
import com.example.ygm.R;
import com.example.ygm.User;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment {

    ArrayList<SearchResultItem> item;

    String searchResult;

    SearchView searchView;
    RecyclerView searchResultView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        Activity activity = getActivity();

        searchResult = getArguments().getString("Name");    //Name 받기.

        searchView = v.findViewById(R.id.search_fragment_search_result);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Bundle bundle = new Bundle();
                bundle.putString("Name", s);
                Navigation.findNavController(v).navigate(R.id.action_searchResultFragment_to_searchResultFragment, bundle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                return false;
            }
        });

        searchResultView = v.findViewById(R.id.recyclerView_search_result);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        searchResultView.setLayoutManager(linearLayoutManager);

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    searchResultView.setAdapter(new SearchAdapter(item, searchResult, getActivity()));
                }
            }
        }, getActivity(), R.id.count_fragment_search_result, item);
        task.execute("http://15.165.152.15//Search.php", (((User) activity.getApplication()).getUID() == null ? "0" : ((User) activity.getApplication()).getUID()), searchResult);

        return v;
    }
}