package com.example.ygm.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.ygm.DB.DB;
import com.example.ygm.R;
import com.example.ygm.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class PushAlarmActivity extends AppCompatActivity {

    ArrayList<PushAlarmItem> item;

    RecyclerView pushAlarmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_alarm);

        Toolbar toolbar = findViewById(R.id.toolbar_activity_push_alarm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pushAlarmList = findViewById(R.id.recyclerView_push_alarm);
        pushAlarmList.setLayoutManager(new LinearLayoutManager(this));

        if (((User) getApplication()).isPushAlarm()) {
            item = new ArrayList<>();
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        pushAlarmList.setAdapter(new PushAlarmAdapter(item));
                    }
                }
            }, this, R.id.recyclerView_push_alarm, item);
            task.execute("http://15.165.152.15//ShowPushAlarm.php");
        }
    }
}