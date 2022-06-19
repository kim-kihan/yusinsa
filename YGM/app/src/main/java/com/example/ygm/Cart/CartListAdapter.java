package com.example.ygm.Cart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.DB.DB;
import com.example.ygm.MainActivity;
import com.example.ygm.Payment.PaymentItem;
import com.example.ygm.Product.ProductActivity;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListViewHolder> {

    private ArrayList<CartListItem> item;

    View v;
    Activity activity;

    public CartListAdapter(ArrayList<CartListItem> item, Activity activity) {
        this.item = item;
        this.activity = activity;
    }

    @Override
    public CartListViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.cart_list_item, parent, false);
        return new CartListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartListViewHolder holder, int position) {
        CartListItem tmp = item.get(position);
        Glide.with(v).load(tmp.getImg()).override(Target.SIZE_ORIGINAL).into(holder.ivProduct);
        holder.tvPname.setText(tmp.getName());
        holder.tvOption.setText(String.format("옵션 : %s SIZE", tmp.getOption()));
        holder.tvNum.setText(Integer.toString(tmp.getNum()));
        holder.tvPrice.setText(new DecimalFormat("###,###,###,###").format(tmp.getPrice()) + "원");
        int stock = tmp.getStock();
        if (stock == 0) {
            holder.tvStock.setText("품절");
        } else if (stock < 5){
            holder.tvStock.setText(String.format("재고 %d개", stock));
        } else {
            holder.tvStock.setText("재고 5개 이상");
        }
        holder.tvTotal.setText(new DecimalFormat("###,###,###,###").format(tmp.getPrice() * tmp.getNum()) + "원");

        if (stock > 0) {
            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tmp.getNum() + 1 > stock) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        dlg.setTitle("재고가 부족합니다.");
                        dlg.setPositiveButton("확인", null);
                        dlg.show();
                    } else {
                        DB task = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                                    dlg.setTitle("수량이 변경되었습니다.");
                                    dlg.setPositiveButton("확인", null);
                                    dlg.show();

                                    tmp.setNum(tmp.getNum() + 1);
                                    NonEmptyCartFragment.num = 0; NonEmptyCartFragment.price = 0;
                                    notifyDataSetChanged();
                                }
                            }
                        }, activity, R.id.imageButton_plus);
                        task.execute("http://15.165.152.15//PlustCart.php", tmp.getPK());
                    }
                }
            });
            holder.btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tmp.getNum() == 1) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        dlg.setTitle("더 이상 수량을 줄일 수 없습니다.");
                        dlg.setPositiveButton("확인", null);
                        dlg.show();
                    } else {
                        DB task = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                                    dlg.setTitle("수량이 변경되었습니다.");
                                    dlg.setPositiveButton("확인", null);
                                    dlg.show();

                                    tmp.setNum(tmp.getNum() - 1);
                                    NonEmptyCartFragment.num = 0; NonEmptyCartFragment.price = 0;
                                    notifyDataSetChanged();
                                }
                            }
                        }, activity, R.id.imageButton_minus);
                        task.execute("http://15.165.152.15//MinusCart.php", tmp.getPK());

                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        dlg.setTitle("수량이 변경되었습니다.");
                        dlg.setPositiveButton("확인", null);
                        dlg.show();
                    }
                }
            });
        }

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(activity);
                dlg.setTitle("상품을 삭제하시겠습니까?");
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DB task = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                                            dlg.setTitle("장바구니에서 삭제되었습니다.");
                                            dlg.setPositiveButton("확인", null);
                                            dlg.show();

                                            item.remove(tmp);
                                            NonEmptyCartFragment.num = 0; NonEmptyCartFragment.price = 0;
                                            notifyDataSetChanged();
                                        }
                                    }
                                }, activity, R.id.imageButton_remove);
                                task.execute("http://15.165.152.15//DeleteCart.php", tmp.getPK());
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        holder.togSelect.setChecked(tmp.isSelected());
        if (stock > 0) {

            if (tmp.isSelected()) {
                NonEmptyCartFragment.num += tmp.getNum();
                NonEmptyCartFragment.price += tmp.getPrice() * tmp.getNum();
            }

            NonEmptyCartFragment.tvNum.setText(String.valueOf(NonEmptyCartFragment.num));
            NonEmptyCartFragment.tvPrice.setText(String.valueOf(NonEmptyCartFragment.price));
            NonEmptyCartFragment.tvAmount.setText(String.valueOf(NonEmptyCartFragment.price));

            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tmp.setSelected(!tmp.isSelected());
                    boolean areSelected = true;
                    for (int i = 0; i < item.size(); i++) {
                        if (!item.get(i).isSelected()) {
                            areSelected = false;
                            break;
                        }
                    }
                    CartActivity.togSelectAll.setChecked(areSelected);

                    NonEmptyCartFragment.num = 0; NonEmptyCartFragment.price = 0;
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.select.setVisibility(View.GONE);
            holder.tvSoldOut.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class CartListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        TextView tvPname, tvOption, tvNum, tvPrice, tvStock, tvTotal;
        ImageButton btnPlus, btnMinus, btnRemove;
        ToggleButton togSelect;
        LinearLayout select;
        TextView tvSoldOut;

        public CartListViewHolder(View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageView_product_cart);
            tvPname = itemView.findViewById(R.id.textView_product_name_cart);
            tvOption = itemView.findViewById(R.id.textView_product_option_cart);
            tvNum = itemView.findViewById(R.id.textView_product_num_cart);
            tvPrice = itemView.findViewById(R.id.textView_product_price_cart);
            tvStock = itemView.findViewById(R.id.textView_product_stock_cart);
            tvTotal = itemView.findViewById(R.id.textView_product_total_cart);
            btnPlus = itemView.findViewById(R.id.imageButton_plus);
            btnMinus = itemView.findViewById(R.id.imageButton_minus);
            btnRemove = itemView.findViewById(R.id.imageButton_remove);
            togSelect = itemView.findViewById(R.id.toggleButton_select);
            select = itemView.findViewById(R.id.linearLayout_select_cart_list_item);
            tvSoldOut = itemView.findViewById(R.id.textView_sold_out_cart);
        }
    }
}
