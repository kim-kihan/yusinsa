package com.example.ygm.MyMenu.ServiceCenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.R;

import java.util.ArrayList;

public class MembershipActivity extends AppCompatActivity {

    ArrayList<MembershipItem> item;

    RecyclerView membershipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        Activity activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar_activity_membership);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        membershipList = findViewById(R.id.recyclerView_membership_list);
        membershipList.setLayoutManager(new LinearLayoutManager(this));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    membershipList.setAdapter(new MembershipAdapter(item, activity));
                }
            }
        }, this, R.id.button_membership, item);
        task.execute("http://15.165.152.15//ShowBenefit.php");
    }
}