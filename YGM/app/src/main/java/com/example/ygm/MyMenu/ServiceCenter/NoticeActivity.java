package com.example.ygm.MyMenu.ServiceCenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ygm.DB.DB;
import com.example.ygm.R;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    ArrayList<NoticeItem> item;

    RecyclerView noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Toolbar toolbar = findViewById(R.id.toolbar_activity_notice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noticeList = findViewById(R.id.recyclerView_notice_list);
        noticeList.setLayoutManager(new LinearLayoutManager(this));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    noticeList.setAdapter(new NoticeAdapter(item));
                }
            }
        }, this, R.id.button_notice, item);
        task.execute("http://15.165.152.15//ShowNotice.php");
    }
}