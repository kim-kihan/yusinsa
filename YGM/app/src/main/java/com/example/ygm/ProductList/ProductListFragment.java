package com.example.ygm.ProductList;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ygm.DB.DB;
import com.example.ygm.MainActivity;
import com.example.ygm.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductListFragment extends Fragment {

    ArrayList<ProductListItem> item;

    RecyclerView productList;

    TextView tvNum;
    String cid;

    int chk = 0;

    CheckBox cbSoldOut;
    Spinner spinnerSort;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, container, false);

        Bundle bundle = getArguments();         //번들 받기. getArguments() 메소드로 받음.

        if (bundle != null) {
            cid = bundle.getString("Name");   //Name 받기.
        }
        item = new ArrayList<>();
        // Adapter 생성
        spinnerSort = v.findViewById(R.id.nomination_product_list);
        cbSoldOut = v.findViewById(R.id.sold_out_check_product_list);
        tvNum = v.findViewById(R.id.Count_product_list);

        productList = v.findViewById(R.id.recyclerview_product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productList.setLayoutManager(linearLayoutManager);

        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(v, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));

        if (cid.equals("100") || cid.equals("200") || cid.equals("300") || cid.equals("400")) {
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        for (int i = 0; i < item.size(); i++) {
                            ProductListItem tmp = item.get(i);
                            if (tmp.getStock() == 0) {
                                item.remove(tmp);
                                i--;
                            }
                        }
                        Collections.sort(item, new Comparator<ProductListItem>() {
                            @Override
                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                return Integer.compare(rhs.getLcount(), lhs.getLcount());
                            }
                        });
                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                        tvNum.setText("상품 " + item.size() + "개");
                    }
                }
            }, getActivity(), R.id.Count_product_list, item);
            task.execute("http://15.165.152.15//ShowALLCategory.php", cid, String.valueOf(Integer.parseInt(cid) + 99));
        } else {
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        for (int i = 0; i < item.size(); i++) {
                            ProductListItem tmp = item.get(i);
                            if (tmp.getStock() == 0) {
                                item.remove(tmp);
                                i--;
                            }
                        }
                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                        tvNum.setText("상품 " + item.size() + "개");
                    }
                }
            }, getActivity(), R.layout.fragment_product_list, item);
            task.execute("http://15.165.152.15//ShowPListALL.php", cid);
        }
        cbSoldOut.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbSoldOut.isChecked()) {
                    item.clear();
                    if (cid.equals("100") || cid.equals("200") || cid.equals("300") || cid.equals("400")) {
                        DB task = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    if (spinnerSort.getItemAtPosition(chk).equals("추천순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(rhs.getLcount(), lhs.getLcount());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("신상품(재발매)순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return rhs.getDate().compareTo(lhs.getDate());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("낮은 가격순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(lhs.getPrice(), rhs.getPrice());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("높은 가격순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(rhs.getPrice(), lhs.getPrice());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("리뷰 순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return rhs.getRcount() - lhs.getRcount();
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(rhs.getPcount(), lhs.getPcount());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    }
                                    tvNum.setText("상품 " + item.size() + "개");
                                }
                            }
                        }, getActivity(), R.id.Count_product_list, item);
                        task.execute("http://15.165.152.15//ShowALLCategory.php", cid, String.valueOf(Integer.parseInt(cid) + 99));
                    } else {
                        DB task = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    if (spinnerSort.getItemAtPosition(chk).equals("추천순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(rhs.getLcount(), lhs.getLcount());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("신상품(재발매)순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return rhs.getDate().compareTo(lhs.getDate());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("낮은 가격순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(lhs.getPrice(), rhs.getPrice());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("높은 가격순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(rhs.getPrice(), lhs.getPrice());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else if (spinnerSort.getItemAtPosition(chk).equals("리뷰 순")) {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return rhs.getRcount() - lhs.getRcount();
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    } else {
                                        Collections.sort(item, new Comparator<ProductListItem>() {
                                            @Override
                                            public int compare(ProductListItem lhs, ProductListItem rhs) {
                                                return Integer.compare(rhs.getPcount(), lhs.getPcount());
                                            }
                                        });
                                        productList.setAdapter(new ProductListAdapter(item, getActivity()));
                                    }
                                    tvNum.setText("상품 " + item.size() + "개");
                                }
                            }
                        }, getActivity(), R.layout.fragment_product_list, item);
                        task.execute("http://15.165.152.15//ShowPListALL.php", cid);
                    }
                } else {
                    for (int i = 0; i < item.size(); i++) {
                        ProductListItem tmp = item.get(i);
                        if (tmp.getStock() == 0) {
                            item.remove(tmp);
                            i--;
                        }
                    }
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                    tvNum.setText("상품 " + item.size() + "개");
                }
            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long id) {
                chk = position;
                if (parent.getItemAtPosition(position).equals("추천순")) {
                    Collections.sort(item, new Comparator<ProductListItem>() {
                        @Override
                        public int compare(ProductListItem lhs, ProductListItem rhs) {
                            return Integer.compare(rhs.getLcount(), lhs.getLcount());
                        }
                    });
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                } else if (parent.getItemAtPosition(position).equals("신상품(재발매)순")) {
                    Collections.sort(item, new Comparator<ProductListItem>() {
                        @Override
                        public int compare(ProductListItem lhs, ProductListItem rhs) {
                            return rhs.getDate().compareTo(lhs.getDate());
                        }
                    });
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                } else if (parent.getItemAtPosition(position).equals("낮은 가격순")) {
                    Collections.sort(item, new Comparator<ProductListItem>() {
                        @Override
                        public int compare(ProductListItem lhs, ProductListItem rhs) {
                            return Integer.compare(lhs.getPrice(), rhs.getPrice());
                        }
                    });
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                } else if (parent.getItemAtPosition(position).equals("높은 가격순")) {
                    Collections.sort(item, new Comparator<ProductListItem>() {
                        @Override
                        public int compare(ProductListItem lhs, ProductListItem rhs) {
                            return Integer.compare(rhs.getPrice(), lhs.getPrice());
                        }
                    });
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                } else if (parent.getItemAtPosition(position).equals("리뷰 순")) {
                    Collections.sort(item, new Comparator<ProductListItem>() {
                        @Override
                        public int compare(ProductListItem lhs, ProductListItem rhs) {
                            return rhs.getRcount() - lhs.getRcount();
                        }
                    });
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                } else {
                    Collections.sort(item, new Comparator<ProductListItem>() {
                        @Override
                        public int compare(ProductListItem lhs, ProductListItem rhs) {
                            return Integer.compare(rhs.getPcount(), lhs.getPcount());
                        }
                    });
                    productList.setAdapter(new ProductListAdapter(item, getActivity()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        return v;
    }
}