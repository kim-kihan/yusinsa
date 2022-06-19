package com.example.ygm.MyMenu.Inquiry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.R;

import java.util.ArrayList;

public class ProductInquiryActivity extends AppCompatActivity {

    ArrayList<ProductInquiryItem> item;

    RecyclerView inquiryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inquiry);

        Activity activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar_activity_product_inquiry);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inquiryList = findViewById(R.id.recyclerView_product_inquiry);
        inquiryList.setLayoutManager(new LinearLayoutManager(this));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    inquiryList.setAdapter(new ProductInquiryAdapter(item, activity));
                }
            }
        }, this, R.id.button_product_inquiry, item);
        task.execute("http://15.165.152.15//ShowUserProductQ.php", ((User) getApplication()).getUID());
    }
}