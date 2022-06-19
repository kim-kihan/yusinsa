package com.example.ygm.Payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.LogIn.UserItem;
import com.example.ygm.User;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    ArrayList<PaymentItem> item;

    User user;

    ConstraintLayout[] title = new ConstraintLayout[4];
    LinearLayout[] body = new LinearLayout[4];

    TextView tvOrdererInfo, tvDestinationInfo, tvProductInfo, tvDiscountInfo;

    EditText edtName, edtEmail;
    EditText[] edtPhone = new EditText[3];

    TextView tvZipcode, tvAddress;
    EditText edtDetailedAddress;
    Button btnFindAddress;

    RecyclerView paymentList;

    TextView tvDiscountByLV, tvPointPreSale, tvSavePoint, tvUserPoint;
    EditText edtUsePoint;
    Button btnUsePreSale, btnUseMaxPoint;
    TextView tvDiscountTotal;

    int usePoint;

    TextView pay;

    int num;
    int total;
    int discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Activity activity = this;

        setResult(0);

        int from = getIntent().getIntExtra("from", -1);

        item = (ArrayList<PaymentItem>) getIntent().getSerializableExtra("items");

        title[0] = findViewById(R.id.constraintLayout_orderer_info);
        title[1] = findViewById(R.id.constraintLayout_destination_info);
        title[2] = findViewById(R.id.constraintLayout_product_info);
        title[3] = findViewById(R.id.constraintLayout_coupon_discount_point);
        body[0] = findViewById(R.id.linearLayout_orderer_info);
        body[1] = findViewById(R.id.linearLayout_destination);
        body[2] = findViewById(R.id.linearLayout_product_info);
        body[3] = findViewById(R.id.linearLayout_coupon_discount_point);

        title[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (body[0].getVisibility() == View.VISIBLE) {
                    tvOrdererInfo.setText(edtName.getText() + " | " + edtPhone[0].getText() + "-" + edtPhone[1].getText() + "-" + edtPhone[2].getText());
                    tvOrdererInfo.setVisibility(View.VISIBLE);
                    body[0].setVisibility(View.GONE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_orderer_info)).setImageResource(R.drawable.expand_more);
                } else {
                    tvOrdererInfo.setVisibility(View.INVISIBLE);
                    body[0].setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_orderer_info)).setImageResource(R.drawable.expand_less);
                }
            }
        });
        title[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (body[1].getVisibility() == View.VISIBLE) {
                    tvDestinationInfo.setText(user.getUname() + " | " + edtDetailedAddress.getText());
                    tvDestinationInfo.setVisibility(View.VISIBLE);
                    body[1].setVisibility(View.GONE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_destination)).setImageResource(R.drawable.expand_more);
                } else {
                    tvDestinationInfo.setVisibility(View.INVISIBLE);
                    body[1].setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_destination)).setImageResource(R.drawable.expand_less);
                }
            }
        });
        title[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (body[2].getVisibility() == View.VISIBLE) {
                    tvProductInfo.setVisibility(View.VISIBLE);
                    body[2].setVisibility(View.GONE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_product_info)).setImageResource(R.drawable.expand_more);
                } else {
                    tvProductInfo.setVisibility(View.INVISIBLE);
                    body[2].setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_product_info)).setImageResource(R.drawable.expand_less);
                }
            }
        });
        title[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (body[3].getVisibility() == View.VISIBLE) {
                    tvDiscountInfo.setText(new DecimalFormat("###,###,###,###").format(discount) + "원");
                    tvDiscountInfo.setVisibility(View.VISIBLE);
                    body[3].setVisibility(View.GONE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_coupon_discount_point)).setImageResource(R.drawable.expand_more);
                } else {
                    tvDiscountInfo.setVisibility(View.INVISIBLE);
                    body[3].setVisibility(View.VISIBLE);
                    ((ImageButton) findViewById(R.id.imageButton_expand_coupon_discount_point)).setImageResource(R.drawable.expand_less);
                }
            }
        });

        tvOrdererInfo = findViewById(R.id.textView_orderer_name_phone);
        tvDestinationInfo = findViewById(R.id.textView_orderer_name_destination);
        tvProductInfo = findViewById(R.id.textView_product_info);
        tvDiscountInfo = findViewById(R.id.textView_discount);

        edtName = findViewById(R.id.editTextOrdererName);
        edtEmail = findViewById(R.id.editTextOrdererName);
        edtPhone[0] = findViewById(R.id.editTextNumber1);
        edtPhone[1] = findViewById(R.id.editTextNumber2);
        edtPhone[2] = findViewById(R.id.editTextNumber3);

        tvZipcode = findViewById(R.id.textView_zipcode_payment);
        tvAddress = findViewById(R.id.textView_address_payment);
        edtDetailedAddress = findViewById(R.id.editTextDetailedAddress);
        btnFindAddress = findViewById(R.id.button_find_address_payment);

        btnFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(activity, FindAddressActivity.class);
                startActivityForResult(i, 10000);
            }
        });

        user = (User) this.getApplication();

        String number = user.getPhone();
        tvOrdererInfo.setText(user.getUname() + " | " + number);
        edtName.setText(user.getUname());
        edtPhone[0].setText(number.substring(0, 3));
        edtPhone[1].setText(number.substring(3, 7));
        edtPhone[2].setText(number.substring(7, 11));

        num = 0;
        total = 0;
        for (int i = 0; i < item.size(); i++) {
            PaymentItem item = this.item.get(i);
            num += item.getNum();
            total += item.getPrice() * item.getNum();
        }

        tvProductInfo.setText(num + "건 | " + new DecimalFormat("###,###,###,###").format(total) + "원");

        paymentList = findViewById(R.id.recyclerView_payment_list);
        paymentList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        paymentList.setAdapter(new PaymentListAdapter(item));

        ////////////////////////////////////////////////////////////////////////////////////////////

        discount = total * user.getLV() / 100;

        tvDiscountByLV = findViewById(R.id.textView_lv_discount);
        tvDiscountByLV.setText("-" + new DecimalFormat("###,###,###,###").format(total * user.getLV() / 100) + "원(" + user.getGrade() + ")");

        tvPointPreSale = findViewById(R.id.textView_use_save_point);
        tvSavePoint = findViewById(R.id.textView_save_point_amount);

        tvSavePoint.setText("적립 " + new DecimalFormat("###,###,###,###").format(total * user.getLV() / 100) + "원");

        btnUsePreSale = findViewById(R.id.button_use_save_point);
        btnUsePreSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnUsePreSale.getText().toString().equals("사용")) {
                    btnUsePreSale.setText("사용취소");
                    tvPointPreSale.setText("- " + total * user.getLV() / 100 + " 원");
                    tvSavePoint.setText("적립 0 원");
                } else {
                    btnUsePreSale.setText("사용");
                    tvPointPreSale.setText(null);
                    tvSavePoint.setText("적립 " + new DecimalFormat("###,###,###,###").format(total * user.getLV() / 100) + " 원");
                }

                usePoint();
            }
        });

        usePoint = 0;
        edtUsePoint = findViewById(R.id.editText_use_point);
        edtUsePoint.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String tmp = edtUsePoint.getText().toString();
                tmp = tmp.replaceAll(",", "");
                if (edtUsePoint.getText().toString().equals("")) {
                    usePoint = 0;
                } else {
                    usePoint = Integer.parseInt(tmp);
                }
                edtUsePoint.setText(new DecimalFormat("###,###,###,###").format(usePoint));
                edtUsePoint.setSelection(edtUsePoint.getText().length());

                edtUsePoint.setTextColor(Color.parseColor("#03A9F4"));
                ((TextView) findViewById(R.id.textView_won_payment)).setTextColor(Color.parseColor("#03A9F4"));

                if (usePoint > user.getPoint()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                    dlg.setTitle("보유하신 적립금을 초과하였습니다.");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            usePoint = 0;
                            edtUsePoint.setText("0");
                            btnUseMaxPoint.setText("최대 사용");
                            usePoint();
                        }
                    });
                    dlg.show();
                } else if(usePoint > total - total * user.getLV() / 100 - ((tvSavePoint.getText().toString().equals("적립 0 원")) ? total * user.getLV() / 100 : 0)) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                    dlg.setTitle("최대로 사용할 수 있는\n적립금을 초과하였습니다.");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            usePoint = total - total * user.getLV() / 100 - ((tvSavePoint.getText().toString().equals("적립 0 원")) ? total * user.getLV() / 100 : 0);
                            edtUsePoint.setText(new DecimalFormat("###,###,###,###").format(usePoint));
                            btnUseMaxPoint.setText("사용취소");
                            usePoint();
                        }
                    });
                    dlg.show();
                } else {
                    btnUseMaxPoint.setText("사용취소");
                    usePoint();
                }

                return false;
            }
        });

        btnUseMaxPoint = findViewById(R.id.button_use_max_point);
        btnUseMaxPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnUseMaxPoint.getText().toString().equals("최대 사용")) {
                    usePoint = total - total * user.getLV() / 100 - ((tvSavePoint.getText().toString().equals("적립 0 원")) ? total * user.getLV() / 100 : 0);

                    btnUseMaxPoint.setText("사용취소");

                    edtUsePoint.setText(new DecimalFormat("###,###,###,###").format(usePoint));

                    edtUsePoint.setTextColor(Color.parseColor("#03A9F4"));
                    ((TextView) findViewById(R.id.textView_won_payment)).setTextColor(Color.parseColor("#03A9F4"));
                } else {
                    usePoint = 0;

                    btnUseMaxPoint.setText("최대 사용");
                    edtUsePoint.setText("0");

                    edtUsePoint.setTextColor(Color.parseColor("#40F2F2F2"));
                    ((TextView) findViewById(R.id.textView_won_payment)).setTextColor(Color.parseColor("#40F2F2F2"));
                }

                usePoint();
            }
        });

        tvUserPoint = findViewById(R.id.textView_user_point);
        tvUserPoint.setText(new DecimalFormat("###,###,###,###").format(user.getPoint()) + "원");

        tvDiscountTotal = findViewById(R.id.textView_total_discount);
        tvDiscountTotal.setText(new DecimalFormat("###,###,###,###").format(discount) + "원");

        pay = findViewById(R.id.textView_pay_payment);
        pay.setText(new DecimalFormat("###,###,###,###").format(total - discount) + "원 결제하기");
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (from) {
                    case R.id.button10:
                        DB task = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    ArrayList<UserItem> userItem = new ArrayList<>();
                                    DB task = new DB(new DB.OnResultListener() {
                                        @Override
                                        public void onComplete(boolean result) {
                                            if (!result) {
                                                UserItem tmp = userItem.get(0);

                                                user.setExp(tmp.getExp());
                                                user.setPoint(tmp.getPoint());

                                                AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                                                dlg.setTitle("결제가 완료되었습니다.");
                                                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        setResult(1);
                                                        finish();
                                                    }
                                                });
                                                dlg.show();
                                            }
                                        }
                                    }, activity, R.id.pw_fragment_log_in_home, userItem);
                                    task.execute("http://15.165.152.15//GetUser.php", user.getID());
                                }
                            }
                        }, activity, 100);
                        PaymentItem tmp = item.get(0);
                        task.execute("http://15.165.152.15//Buy.php", user.getUID(), tmp.getPID(), tmp.getSID(), String.valueOf(tmp.getNum()), String.valueOf(tmp.getPrice() * tmp.getNum()), String.valueOf(((tvSavePoint.getText().toString().equals("적립 0 원")) ? 0 : total * user.getLV() / 100) - usePoint), String.valueOf(tmp.getPrice() * tmp.getNum() * 10 / 100), tvZipcode + ", " + tvAddress + ", " + edtDetailedAddress.getText().toString(), "2020-11-19 02:00:00");
                        break;
                    case R.id.textView_pay_cart:
                        try {
                            for (int i = 0; i < item.size(); i++) {
                                int check = i;
                                DB task1 = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            if (check == item.size() - 1) {
                                                ArrayList<UserItem> userItem = new ArrayList<>();
                                                DB task = new DB(new DB.OnResultListener() {
                                                    @Override
                                                    public void onComplete(boolean result) {
                                                        if (!result) {
                                                            UserItem tmp = userItem.get(0);

                                                            user.setExp(tmp.getExp());
                                                            user.setPoint(tmp.getPoint());

                                                            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                                                            dlg.setTitle("결제가 완료되었습니다.");
                                                            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    setResult(1);
                                                                    finish();
                                                                }
                                                            });
                                                            dlg.show();
                                                        }
                                                    }
                                                }, activity, R.id.pw_fragment_log_in_home, userItem);
                                                task.execute("http://15.165.152.15//GetUser.php", user.getID());
                                            }
                                        }
                                    }
                                }, activity, R.id.textView_pay_payment);
                                PaymentItem tmp1 = item.get(i);
                                task1.execute("http://15.165.152.15//BuyCart.php", user.getUID(), tmp1.getPID(), tmp1.getSID(), String.valueOf(tmp1.getNum()), String.valueOf(tmp1.getPrice() * tmp1.getNum()), String.valueOf((((tvSavePoint.getText().toString().equals("적립 0 원")) ? 0 : total * user.getLV() / 100) - usePoint) / item.size()), String.valueOf(tmp1.getPrice() * tmp1.getNum() * 10 / 100), tvZipcode + ", " + tvAddress + ", " + edtDetailedAddress.getText().toString(), "2020-11-19 02:00:00", tmp1.getCID());
                            }
                        } catch (Exception e) {

                        }
                        break;
                }
            }
        });
    }

    public void usePoint() {
        discount = total * user.getLV() / 100;
        discount += (tvSavePoint.getText().toString().equals("적립 0 원")) ? total * user.getLV() / 100 : 0;
        discount += usePoint;
        tvDiscountTotal.setText("-" + new DecimalFormat("###,###,###,###").format(discount) + "원");
        pay.setText(new DecimalFormat("###,###,###,###").format(total - discount) + "원 결제하기");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case 10000:
                if (resultCode == RESULT_OK) {
                    String data = intent.getStringExtra("data");
                    if (data != null) {
                        String zipcode = data.substring(0, data.indexOf(','));
                        String address = data.substring(data.indexOf(',') + 1);
                        tvZipcode.setText(zipcode);
                        tvAddress.setText(address);
                    }
                }
                break;
        }

    }
}