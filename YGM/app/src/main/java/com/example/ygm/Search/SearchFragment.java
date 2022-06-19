package com.example.ygm.Search;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ygm.DB.DB;
import com.example.ygm.MainActivity;
import com.example.ygm.User;
import com.example.ygm.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ArrayList<String> item;

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        MainActivity.title.setText("검색");

        searchView = v.findViewById(R.id.search_fragment_search);
        listView = v.findViewById(R.id.listView_search);

        String uid = "0";

        User user = (User) getActivity().getApplication();

        if (user.getUID() != null) {
            uid = user.getUID();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Bundle bundle = new Bundle();
                bundle.putString("Name", s);
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_searchResultFragment, bundle);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                return false;
            }
        });

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, item);
                    listView.setAdapter(arrayAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView parent, View v, int position, long id) {
                            Bundle bundle = new Bundle();
                            bundle.putString("Name", (String)parent.getAdapter().getItem(position));
                            MainActivity.navController.navigate(R.id.action_searchFragment_to_searchResultFragment,bundle);
                        }
                    });
                }
            }
        }, getActivity(), R.id.search_fragment_search, item);
        task.execute("http://15.165.152.15//RecentSearch.php", uid);

        return v;
    }
}