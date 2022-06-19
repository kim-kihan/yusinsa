package com.example.ygm.MyMenu.ServiceCenter;

import android.os.Bundle;

import com.example.ygm.DB.DB;
import com.example.ygm.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FAQActivity extends AppCompatActivity {

    ArrayList<FAQItem> item;

    RecyclerView faqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Toolbar toolbar = findViewById(R.id.toolbar_activity_faq);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        faqList = findViewById(R.id.recyclerView_faq_list);
        faqList.setLayoutManager(new LinearLayoutManager(this));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    faqList.setAdapter(new FAQAdapter(item));
                }
            }
        }, this, R.id.button_faq, item);
        task.execute("http://15.165.152.15//ShowFAQ.php");
    }
}