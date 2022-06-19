package com.example.ygm.MyMenu.OrderList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.R;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    ArrayList<OrderListItem> item;

    RecyclerView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        Toolbar toolbar = findViewById(R.id.toolbar_activity_orderlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderList = findViewById(R.id.recyclerView_order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    orderList.setAdapter(new OrderListAdapter(item));
                }
            }
        }, this, R.id.button_all_orderlist_mymenu, item);
        task.execute("http://15.165.152.15//ShowBuylist.php", ((User) getApplication()).getUID());
    }
}