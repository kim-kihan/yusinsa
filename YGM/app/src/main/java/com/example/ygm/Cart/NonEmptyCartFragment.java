package com.example.ygm.Cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ygm.DB.DB;
import com.example.ygm.Payment.PaymentActivity;
import com.example.ygm.Payment.PaymentItem;
import com.example.ygm.R;

import java.util.ArrayList;

public class NonEmptyCartFragment extends Fragment {

    private ArrayList<CartListItem> item;

    RecyclerView cartList;

    public static TextView tvNum, tvPrice, tvAmount;
    public static int num, price;

    NonEmptyCartFragment(ArrayList<CartListItem> item) {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nonempty_cart, container, false);

        num = 0; price = 0;

        tvNum = v.findViewById(R.id.textView_cart_select_num);
        tvPrice = v.findViewById(R.id.textView_cart_select_price);
        tvAmount = v.findViewById(R.id.textView_cart_total_price);

        cartList = v.findViewById(R.id.recyclerView_cartlist);
        cartList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartList.setAdapter(new CartListAdapter(item, getActivity()));

        CartActivity.tvSelectAll.setText("전체 선택");
        CartActivity.togSelectAll.setChecked(true);

        CartActivity.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.togSelectAll.setChecked(!CartActivity.togSelectAll.isChecked());
                boolean areSelected = CartActivity.togSelectAll.isChecked();

                for (int i = 0; i < item.size(); i++) {
                    if (item.get(i).getStock() > 0) {
                        item.get(i).setSelected(areSelected);
                    }
                }

                num = 0; price = 0;
                cartList.getAdapter().notifyDataSetChanged();
            }
        });

        CartActivity.tvDeleteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setTitle("상품을 삭제하시겠습니까?");
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < item.size(); i++) {
                                    CartListItem tmp = item.get(i);
                                    if (tmp.isSelected()) {
                                        DB task = new DB(new DB.OnResultListener() {
                                            @Override
                                            public void onComplete(boolean result) {
                                                if (!result) {
                                                    item.remove(tmp);
                                                    if (item.size() == 0) {
                                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                        transaction.replace(R.id.frameLayout_cart, new EmptyCartFragment()).commit();
                                                    } else {
                                                        num = 0; price = 0;
                                                        cartList.getAdapter().notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }, getActivity(), R.id.imageButton_remove);
                                        task.execute("http://15.165.152.15//DeleteCart.php", tmp.getPK());
                                    }
                                }
                                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                                dlg.setTitle("장바구니에서 삭제되었습니다.");
                                dlg.setPositiveButton("확인", null);
                                dlg.show();
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        CartActivity.tvDeleteSoldOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                dlg.setTitle("상품을 삭제하시겠습니까?");
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < item.size(); i++) {
                                    CartListItem tmp = item.get(i);
                                    if (tmp.getStock() == 0) {
                                        DB task = new DB(new DB.OnResultListener() {
                                            @Override
                                            public void onComplete(boolean result) {
                                                if (!result) {
                                                    item.remove(tmp);
                                                    if (item.size() == 0) {
                                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                        transaction.replace(R.id.frameLayout_cart, new EmptyCartFragment()).commit();
                                                    } else {
                                                        num = 0; price = 0;
                                                        cartList.getAdapter().notifyDataSetChanged();
                                                    }
                                                }
                                            }
                                        }, getActivity(), R.id.imageButton_remove);
                                        task.execute("http://15.165.152.15//DeleteCart.php", tmp.getPK());
                                    }
                                }
                                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                                dlg.setTitle("장바구니에서 삭제되었습니다.");
                                dlg.setPositiveButton("확인", null);
                                dlg.show();
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        return v;
    }
}
