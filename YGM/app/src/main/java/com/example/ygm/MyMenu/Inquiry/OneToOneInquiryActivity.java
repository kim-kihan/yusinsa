package com.example.ygm.MyMenu.Inquiry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.R;

import java.util.ArrayList;

public class OneToOneInquiryActivity extends AppCompatActivity {

    ArrayList<OneToOneInquiryItem> item;

    RecyclerView inquiryList;

    TextView tvInquire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_to_one_inquiry);

        Activity activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar_activity_onetoone_inquiry);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inquiryList = findViewById(R.id.recyclerView_onetoone_inquiry);
        inquiryList.setLayoutManager(new LinearLayoutManager(this));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    inquiryList.setAdapter(new OneToOneInquiryAdapter(item));
                }
            }
        }, this, R.id.button_onetoone_inquiry, item);
        task.execute("http://15.165.152.15//ShowQnA.php", ((User) getApplication()).getUID());

        tvInquire = findViewById(R.id.textView_inquire_onetoone_inquiry);

        tvInquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(activity, OneToOneInquireActivity.class), 40000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 40000:
                if (resultCode == RESULT_OK) {
                    item = new ArrayList<>();
                    DB task = new DB(new DB.OnResultListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (!result) {
                                inquiryList.setAdapter(new OneToOneInquiryAdapter(item));
                            }
                        }
                    }, this, R.id.button_onetoone_inquiry, item);
                    task.execute("http://15.165.152.15//ShowQnA.php", ((User) getApplication()).getUID());
                }
                break;
        }
    }
}