package com.example.ygm.Cart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ygm.DB.DB;
import com.example.ygm.Payment.PaymentItem;
import com.example.ygm.User;
import com.example.ygm.Payment.PaymentActivity;
import com.example.ygm.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<CartListItem> item;

    public static LinearLayout selectAll;
    public static ToggleButton togSelectAll;
    public static TextView tvSelectAll;
    public static TextView tvDeleteSelect, tvDeleteSoldOut;
    TextView tvPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setResult(RESULT_CANCELED);

        Activity activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar_activity_cart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectAll = findViewById(R.id.linearLayout_select_all);
        togSelectAll = findViewById(R.id.toggleButton_select_all_cart);
        tvSelectAll = findViewById(R.id.textView_select_all_cart);
        tvDeleteSelect = findViewById(R.id.textView_delete_select_cart);
        tvDeleteSoldOut = findViewById(R.id.textView_delete_sold_out);
        tvPay = findViewById(R.id.textView_pay_cart);

        item = new ArrayList<>();
        DB task = new DB(new DB.OnResultListener() {
            @Override
            public void onComplete(boolean result) {
                if (!result) {
                    if (item.size() == 0) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_cart, new EmptyCartFragment()).commit();
                        tvPay.setText("쇼핑 계속하기");
                        tvPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_cart, new NonEmptyCartFragment(item)).commit();
                        tvPay.setText("결제하기");
                        tvPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, PaymentActivity.class);
                                intent.putExtra("from", R.id.textView_pay_cart);
                                intent.putExtra("items", getPaymentItem());
                                startActivityForResult(intent, 0);
                            }
                        });
                    }
                }
            }
        }, this, R.id.recyclerView_cartlist, item);
        task.execute("http://15.165.152.15//ShowCart.php", ((User) getApplication()).getUID());
    }

    public ArrayList<PaymentItem> getPaymentItem() {

        ArrayList<PaymentItem> paymentItems = new ArrayList<>();

        for (int i = 0; i < item.size(); i++) {
            CartListItem tmp = item.get(i);
            if (tmp.isSelected()) {
                paymentItems.add(new PaymentItem(tmp.getPID(), tmp.getSID(), tmp.getImg(), tmp.getName(), tmp.getOption(), tmp.getPrice(), tmp.getNum(), tmp.getPK()));
            }
        }

        return paymentItems;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if (resultCode == 1) {
                    startActivity(new Intent(this, CartActivity.class));
                    finish();
                }
        }
    }
}