package com.example.ygm.Category;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ygm.DB.DB;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryFragment extends Fragment {

    ArrayList<String> item;

    RecyclerView categoryList;
    CategoryListAdapter categoryListAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        MainActivity.title.setText("카테고리");

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    categoryList = view.findViewById(R.id.recyclerView_category);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    categoryList.setLayoutManager(linearLayoutManager);
                    categoryListAdapter = new CategoryListAdapter();
                    categoryList.setAdapter(categoryListAdapter);
                    getData();
                }
            }
        }, getActivity(), R.id.categoryFragment, item);
        task.execute("http://15.165.152.15//ShowFirstALL.php");

        return view;
    }

    private void getData() {
        // 임의의 데이터입니다.
        List<String> listTitle_kor = Arrays.asList("상의", "하의", "신발", "모자");
        List<String> listTitle_eng = Arrays.asList("Top", "Bottom", "Shoes", "Headwear");
        List<String> listContent = Arrays.asList("전체", "셔츠", "아우터", "후드/맨투맨", "티셔츠",
                "전체", "데님 팬츠", "코튼 팬츠", "슬랙스", "트레이닝",
                "전체", "캔버스/단화", "러닝화/운동화", "샌들/슬리퍼", "로퍼",
                "전체", "캡/야구 모자", "헌팅/베레", "버킷/사파리햇", "비니");
        int count = 0;
        for (int i = 0; i < listTitle_kor.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            int j = 0;
            CategoryItem data = new CategoryItem(item.get(i), listTitle_kor.get(i), listTitle_eng.get(i), listContent.get(count++), listContent.get(count++), listContent.get(count++), listContent.get(count++), (i != 4) ? listContent.get(count++) : listContent.get(count));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            categoryListAdapter.addItem(data);
        }
    }
}