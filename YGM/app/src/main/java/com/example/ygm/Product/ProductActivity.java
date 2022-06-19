package com.example.ygm.Product;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.Cart.CartActivity;
import com.example.ygm.DB.DB;
import com.example.ygm.User;
import com.example.ygm.LogIn.LogInActivity;
import com.example.ygm.MainActivity;
import com.example.ygm.Payment.PaymentActivity;
import com.example.ygm.Payment.PaymentItem;
import com.example.ygm.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductActivity extends AppCompatActivity {

    ViewPager2 productImageBanner;
    TextView tvViewPager_size, tvContents, tvPrice, tvCount, tvOption, tvReviewSize;
    ImageView ivProduct, ivPlus, ivMinus;
    ImageButton btnLike, btnBack, btnCart;
    RatingBar rbAverageRatingProduct;
    ReviewAdapter reviewAdapter;
    InquireAdapter inquireAdapter;
    LinearLayout linearLayoutOption, linearLayoutPaymentOrCart, linearLayoutCount;
    Button btnQnA, btnMovCart, btnPayment, btnSelectOption;
    RecyclerView rvReview, rvQnAList;
    ArrayList<ProductItem> productList = new ArrayList<>();
    ArrayList<ReviewItem> reviewList = new ArrayList<>();
    ArrayList<InquireItem> inquireList = new ArrayList<>();
    ArrayList<OptionItem> optionList = new ArrayList<>();
    ArrayList<String> colorList = new ArrayList<>();
    ArrayList<String> sizeList = new ArrayList<>();
    ArrayList<String> insertCart = new ArrayList<>();
    ArrayList<String> likeCount = new ArrayList<>();
    Spinner spinnerColor, spinnerSize, spinnerReview;
    String colorPosition, sizePosition;
    int tvCountPosition = 1;
    int likePosition = 0;
    int price = 0;
    int productImageBannerSize = 1;
    int checkPosition = 0;
    String optionScount;
    String productID, userID, sid, thumb, content, color, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String PID = getIntent().getStringExtra("PID");

        Activity activity = this;

        productImageBanner = findViewById(R.id.small_product_image);
        tvViewPager_size = findViewById(R.id.count_small_product_image);
        tvContents = findViewById(R.id.contents_product);
        tvPrice = findViewById(R.id.price_product);
        ivProduct = findViewById(R.id.image_product);
        rbAverageRatingProduct = findViewById(R.id.Average_rating_Product);
        btnQnA = findViewById(R.id.QnA_button_product);
        btnMovCart = findViewById(R.id.button8);
        btnPayment = findViewById(R.id.button9);
        rvReview = (RecyclerView) findViewById(R.id.review_product);
        rvQnAList = (RecyclerView) findViewById(R.id.review_product_QnA);
        spinnerColor = findViewById(R.id.spinner);
        spinnerSize = findViewById(R.id.spinner2);
        btnSelectOption = findViewById(R.id.button10);
        linearLayoutOption = findViewById(R.id.option_product);
        linearLayoutPaymentOrCart = findViewById(R.id.payment_and_cart_product);
        linearLayoutCount = findViewById(R.id.option_product1);
        tvCount = findViewById(R.id.textView17);
        tvOption = findViewById(R.id.textView16);
        ivPlus = findViewById(R.id.imageView2);
        ivMinus = findViewById(R.id.imageView3);
        btnLike = findViewById(R.id.imageButton);
        spinnerReview = findViewById(R.id.nomination_fragment_product);
        btnCart = findViewById(R.id.cart_fragment_product);
        tvReviewSize = findViewById(R.id.Count_review_fragment_product);
        btnBack = findViewById(R.id.button_back_activity_product);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((User) activity.getApplication()).getUID() != null) {
                    startActivityForResult(new Intent(activity, CartActivity.class), 22222);
                    return;
                }
                Intent intent = new Intent(activity, LogInActivity.class);
                intent.putExtra("Value", productID);
                startActivityForResult(new Intent(activity, LogInActivity.class), 0);
            }
        });

        userID = ((User) activity.getApplication()).getUID();


        linearLayoutOption.setVisibility(View.GONE);

        colorList.add("옵션 선택-Color-");
        sizeList.add("옵션 선택-Size-");

        productID = getIntent().getStringExtra("PID");

        ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int pos) {
                super.onPageSelected(pos);
                int k = pos + 1;
                tvViewPager_size.setText(String.valueOf(k) + "/" + String.valueOf(productImageBannerSize));
            }
        };

        productImageBanner.registerOnPageChangeCallback(callback);


        btnQnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = ((User) activity.getApplication()).getUID();
                if (userID == null) {
                    Intent intent = new Intent(activity, LogInActivity.class);
                    intent.putExtra("Value", productID);
                    startActivityForResult(new Intent(activity, LogInActivity.class), 0);
                    return;
                }

                Intent intent = new Intent(activity, InquireActivity.class);
                intent.putExtra("PID", productID);
                intent.putExtra("resId", productList.get(0).getThumbnail());
                intent.putExtra("title1", productList.get(0).getContent());
                startActivityForResult(intent, 33333);
            }
        });
        btnMovCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = ((User) activity.getApplication()).getUID();
                if (userID == null) {
                    Intent intent = new Intent(activity, LogInActivity.class);
                    intent.putExtra("Value", productID);
                    startActivityForResult(new Intent(activity, LogInActivity.class), 0);
                    return;
                }

                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            for (int i = 0; i < optionList.size(); i++) {
                                colorList.add(optionList.get(i).getColor());
                            }
                            ArrayList<String> a = new ArrayList<>();
                            for (int i = 0; i < colorList.size(); i++) {
                                if (!a.contains(colorList.get(i)))
                                    a.add(colorList.get(i));
                            }
                            sizeList.add(optionList.get(0).getSize());
                            for (int i = 0; i < optionList.size(); i++) {
                                sizeList.add(optionList.get(i).getSize());
                            }
                            ArrayList<String> a2 = new ArrayList<>();
                            for (int i = 0; i < sizeList.size(); i++) {
                                if (!a2.contains(sizeList.get(i))) {
                                    a2.add(sizeList.get(i));
                                }
                            }

                            linearLayoutPaymentOrCart.setVisibility(view.GONE);
                            linearLayoutOption.setVisibility(view.VISIBLE);
                            ArrayAdapter<String> reviewAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, a);
                            spinnerColor.setAdapter(reviewAdapter);
                            spinnerColor.setSelection(0);
                            ArrayAdapter<String> inquireAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, a2);
                            spinnerSize.setAdapter(inquireAdapter);
                            spinnerSize.setSelection(0);
                            linearLayoutCount.setVisibility(view.INVISIBLE);

                            spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    colorPosition = a.get(position);
                                    checkPosition = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    sizePosition = a2.get(position);
                                    linearLayoutCount.setVisibility(view.VISIBLE);
                                    tvOption.setText(colorPosition + " / " + sizePosition);

                                    ivPlus.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            tvCountPosition++;
                                            tvCount.setText(String.valueOf(tvCountPosition));
                                        }
                                    });
                                    ivMinus.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            if(tvCountPosition>1){
                                                tvCountPosition--;
                                            }
                                            tvCount.setText(String.valueOf(tvCountPosition));
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            btnSelectOption.setText("장바구니 담기");
                            btnSelectOption.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    if(checkPosition!=0){
                                        for (int i = 0; i < optionList.size(); i++) {
                                            if (optionList.get(i).getColor().equals(colorPosition)) {
                                                if (optionList.get(i).getSize().equals(sizePosition)) {
                                                    optionScount = optionList.get(i).getScount();
                                                    sid = optionList.get(i).getSID();
                                                    color = optionList.get(i).getColor();
                                                    size = optionList.get(i).getSize();
                                                    break;
                                                }
                                            }
                                        }
                                        if (Integer.parseInt(optionScount) < Integer.parseInt(tvCount.getText().toString())) {
                                            Toast toast1 = Toast.makeText(activity.getApplicationContext(), "재고 부족! 현재 재고 개수 : " + optionScount, Toast.LENGTH_SHORT);
                                            toast1.show();
                                            return;
                                        }
                                        DB task2 = new DB(new DB.OnResultListener() {
                                            @Override
                                            public void onComplete(boolean result) {
                                                if (!result) {
                                                    String p = insertCart.get(0);
                                                    DB task3 = new DB(new DB.OnResultListener() {
                                                        @Override
                                                        public void onComplete(boolean result) {
                                                            if (!result) {
                                                                Toast toast1 = Toast.makeText(activity.getApplicationContext(), "장바구니 담기 완료!", Toast.LENGTH_SHORT);
                                                                toast1.show();
                                                            }
                                                        }
                                                    }, activity, R.id.button10);
                                                    task3.execute("http://15.165.152.15//InsertCart2.php", userID, p, String.valueOf(tvCountPosition));
                                                }
                                            }
                                        }, activity, R.id.button8, insertCart);
                                        task2.execute("http://15.165.152.15//InsertCart1.php", productID, colorPosition, sizePosition);
                                    }else{
                                        Toast toast1 = Toast.makeText(activity.getApplicationContext(), "옵션을 선택해주세요!", Toast.LENGTH_SHORT);
                                        toast1.show();
                                    }
                                }
                            });
                        }
                    }
                }, activity, R.id.price_product, optionList);
                task.execute("http://15.165.152.15//ShowProduct3.php", productID, userID);
            }
        });
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = ((User) activity.getApplication()).getUID();
                if (userID == null) {
                    Intent intent = new Intent(activity, LogInActivity.class);
                    intent.putExtra("Value", productID);
                    startActivityForResult(new Intent(activity, LogInActivity.class), 0);
                    return;
                }

                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            int chk = 0;
                            for (int i = 0; i < optionList.size(); i++) {
                                colorList.add(optionList.get(i).getColor());
                            }
                            ArrayList<String> a = new ArrayList<>();
                            for (int i = 0; i < colorList.size(); i++) {
                                if (!a.contains(colorList.get(i)))
                                    a.add(colorList.get(i));
                            }
                            sizeList.add(optionList.get(0).getSize());
                            for (int i = 0; i < optionList.size(); i++) {
                                sizeList.add(optionList.get(i).getSize());
                            }
                            ArrayList<String> a2 = new ArrayList<>();
                            for (int i = 0; i < sizeList.size(); i++) {
                                if (!a2.contains(sizeList.get(i))) {
                                    a2.add(sizeList.get(i));
                                }
                            }
                            linearLayoutPaymentOrCart.setVisibility(view.GONE);
                            linearLayoutOption.setVisibility(view.VISIBLE);
                            ArrayAdapter<String> reviewAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, a);
                            spinnerColor.setAdapter(reviewAdapter);
                            spinnerColor.setSelection(0);
                            ArrayAdapter<String> inquireAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, a2);
                            spinnerSize.setAdapter(inquireAdapter);
                            spinnerSize.setSelection(0);
                            spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    colorPosition = a.get(position);
                                    checkPosition = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    sizePosition = a2.get(position);
                                    linearLayoutCount.setVisibility(view.VISIBLE);
                                    tvOption.setText(colorPosition + " / " + sizePosition);

                                    ivPlus.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            tvCountPosition++;
                                            tvCount.setText(String.valueOf(tvCountPosition));
                                        }
                                    });
                                    ivMinus.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View view) {
                                            if(tvCountPosition>1){
                                                tvCountPosition--;
                                            }
                                            tvCount.setText(String.valueOf(tvCountPosition));
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            btnSelectOption.setText("구매하기");
                            btnSelectOption.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    if(checkPosition!=0){
                                        for (int i = 0; i < optionList.size(); i++) {
                                            if (optionList.get(i).getColor().equals(colorPosition)) {
                                                if (optionList.get(i).getSize().equals(sizePosition)) {
                                                    optionScount = optionList.get(i).getScount();
                                                    sid = optionList.get(i).getSID();
                                                    color = optionList.get(i).getColor();
                                                    size = optionList.get(i).getSize();
                                                    break;
                                                }
                                            }
                                        }
                                        if (Integer.parseInt(optionScount) < Integer.parseInt(tvCount.getText().toString())) {
                                            Toast toast1 = Toast.makeText(activity.getApplicationContext(), "재고 부족! 현재 재고 개수 : " + optionScount, Toast.LENGTH_SHORT);
                                            toast1.show();
                                            return;
                                        }
                                        Intent intent = new Intent(activity, PaymentActivity.class);
                                        intent.putExtra("from", R.id.button10);
                                        ArrayList<PaymentItem> item = new ArrayList<>();
                                        item.add(new PaymentItem(((User) activity.getApplication()).getPID(), sid, thumb, content, color + " / " + size, price, Integer.parseInt(tvCount.getText().toString())));
                                        intent.putExtra("items", item);
                                        startActivity(intent);
                                    }else{
                                        Toast toast1 = Toast.makeText(activity.getApplicationContext(), "옵션을 선택해주세요!", Toast.LENGTH_SHORT);
                                        toast1.show();
                                    }
                                }
                            });
                        }
                    }
                }, activity, R.id.price_product, optionList);
                task.execute("http://15.165.152.15//ShowProduct3.php", productID, ((User) activity.getApplication()).getUID());
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = ((User) activity.getApplication()).getUID();
                if (userID == null) {
                    ((User) activity.getApplication()).setPID(productID);
                    Intent intent = new Intent(activity, LogInActivity.class);
                    intent.putExtra("Value", productID);
                    startActivityForResult(new Intent(activity, LogInActivity.class), 0);
                    return;
                }
                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            if (likeCount.get(likePosition++).equals("1")) {
                                DB task2 = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            btnLike.setImageResource(R.drawable.outline_favorite_border_white_18);
                                            Toast toast1 = Toast.makeText(activity.getApplicationContext(), "좋아요 취소!", Toast.LENGTH_SHORT);
                                            toast1.show();
                                        }
                                    }
                                }, activity, R.id.payment_and_cart_product);
                                task2.execute("http://15.165.152.15//MinusLike.php", productID, userID);
                            } else {
                                DB task2 = new DB(new DB.OnResultListener() {
                                    @Override
                                    public void onComplete(boolean result) {
                                        if (!result) {
                                            findViewById(R.id.imageView_favorite_on).setVisibility(View.VISIBLE);
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findViewById(R.id.imageView_favorite_on).setVisibility(View.INVISIBLE);
                                                }
                                            }, 2000);
                                            btnLike.setImageResource(R.drawable.heart);
                                            Toast toast1 = Toast.makeText(activity.getApplicationContext(), "좋아요 완료!", Toast.LENGTH_SHORT);
                                            toast1.show();
                                        }
                                    }
                                }, activity, R.id.imageButton);
                                task2.execute("http://15.165.152.15//PlusLike.php", productID, userID);
                            }
                        }
                    }
                }, activity, R.id.Average_rating_Product, likeCount);
                task.execute("http://15.165.152.15//ShowProduct1.php", productID, userID);
            }
        });

        spinnerReview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long id) {
                if (parent.getItemAtPosition(position).equals("최신순")) {
                    Collections.sort(reviewList, new Comparator<ReviewItem>() {
                        @Override
                        public int compare(ReviewItem lhs, ReviewItem rhs) {
                            return Integer.compare(Integer.parseInt(rhs.getDate()), Integer.parseInt(lhs.getDate()));
                        }
                    });
                    rvReview.setAdapter(reviewAdapter);
                } else if (parent.getItemAtPosition(position).equals("추천순")) {
                    Collections.sort(reviewList, new Comparator<ReviewItem>() {
                        @Override
                        public int compare(ReviewItem lhs, ReviewItem rhs) {
                            return Integer.compare(Integer.parseInt(rhs.getLevel()), Integer.parseInt(lhs.getLevel()));
                        }
                    });
                    rvReview.setAdapter(reviewAdapter);
                } else if (parent.getItemAtPosition(position).equals("높은 평점순")) {
                    Collections.sort(reviewList, new Comparator<ReviewItem>() {
                        @Override
                        public int compare(ReviewItem lhs, ReviewItem rhs) {
                            return Float.compare(Float.parseFloat(rhs.getRatingnum()), Float.parseFloat(lhs.getRatingnum()));
                        }
                    });
                    rvReview.setAdapter(reviewAdapter);
                } else {
                    Collections.sort(reviewList, new Comparator<ReviewItem>() {
                        @Override
                        public int compare(ReviewItem lhs, ReviewItem rhs) {
                            return Float.compare(Float.parseFloat(lhs.getRatingnum()), Float.parseFloat(rhs.getRatingnum()));
                        }
                    });
                    rvReview.setAdapter(reviewAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvReview.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvQnAList.setLayoutManager(linearLayoutManager2);

        if (((User) activity.getApplication()).getUID() == null) {
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        ArrayList<ProductImageItem> item = new ArrayList<>();
                        item.add(new ProductImageItem(productList.get(0).getTitle1()));
                        if (productList.get(0).getTitle2() != "null") {
                            item.add(new ProductImageItem(productList.get(0).getTitle2()));
                            productImageBannerSize++;
                            if (productList.get(0).getTitle3() != "null") {
                                item.add(new ProductImageItem(productList.get(0).getTitle3()));
                                productImageBannerSize++;
                            }
                        }


                        productImageBanner.setAdapter(new ProductSliderAdapter(item));

                        thumb = productList.get(0).getThumbnail();
                        content = productList.get(0).getContent();
                        tvContents.setText(productList.get(0).getContent().toString());
                        price = Integer.parseInt(productList.get(0).getPrice());
                        tvPrice.setText(new DecimalFormat("###,###,###,###").format(Integer.parseInt(productList.get(0).getPrice())) + "원");
                        Glide.with(activity).load(productList.get(0).getMain())
                                .fitCenter()
                                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                                .into(ivProduct);
                        if (!productList.get(0).getStar().equals("null")) {
                            rbAverageRatingProduct.setRating(Float.parseFloat(productList.get(0).getStar()));
                        }
                        DB task2 = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    reviewAdapter = new ReviewAdapter(activity, reviewList, "0");
                                    rvReview.setAdapter(reviewAdapter);
                                    tvReviewSize.setText("상품 전체 후기(" + reviewList.size() + ")");
                                    DB task3 = new DB(new DB.OnResultListener() {
                                        @Override
                                        public void onComplete(boolean result) {
                                            if (!result) {
                                                inquireAdapter = new InquireAdapter(inquireList);
                                                rvQnAList.setAdapter(inquireAdapter);
                                            }
                                        }
                                    }, activity, R.id.review_product, inquireList);
                                    task3.execute("http://15.165.152.15//ShowProduct4.php", productID, userID);
                                }
                            }
                        }, activity, R.id.Count_review_fragment_product, reviewList);
                        task2.execute("http://15.165.152.15//ShowProduct5.php", productID, userID);
                    }
                }
            }, activity, R.id.contents_product, productList);
            task.execute("http://15.165.152.15//ShowProduct2.php", productID, "0");
        } else {
            DB task = new DB(new DB.OnResultListener() {
                @Override
                public void onComplete(boolean result) {
                    if (!result) {
                        if (likeCount.get(likePosition++).equals("1")) {
                            btnLike.setImageResource(R.drawable.heart);
                        }
                        DB task4 = new DB(new DB.OnResultListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (!result) {
                                    thumb = productList.get(0).getThumbnail();
                                    ArrayList<ProductImageItem> item = new ArrayList<>();
                                    item.add(new ProductImageItem(productList.get(0).getTitle1()));
                                    if (productList.get(0).getTitle2() != "null") {
                                        item.add(new ProductImageItem(productList.get(0).getTitle2()));
                                        productImageBannerSize++;
                                        if (productList.get(0).getTitle3() != "null") {
                                            item.add(new ProductImageItem(productList.get(0).getTitle3()));
                                            productImageBannerSize++;
                                        }
                                    }

                                    productImageBanner.setAdapter(new ProductSliderAdapter(item));
                                    content = productList.get(0).getContent();
                                    tvContents.setText(productList.get(0).getContent().toString());
                                    price = Integer.parseInt(productList.get(0).getPrice());
                                    tvPrice.setText(new DecimalFormat("###,###,###,###").format(Integer.parseInt(productList.get(0).getPrice())) + "원");
                                    Glide.with(activity).load(productList.get(0).getMain())
                                            .fitCenter()
                                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                                            .into(ivProduct);
                                    if (!productList.get(0).getStar().equals("null")) {
                                        rbAverageRatingProduct.setRating(Float.parseFloat(productList.get(0).getStar()));
                                    }
                                    DB task2 = new DB(new DB.OnResultListener() {
                                        @Override
                                        public void onComplete(boolean result) {
                                            if (!result) {
                                                reviewAdapter = new ReviewAdapter(activity, reviewList, ((User) activity.getApplication()).getUID());
                                                rvReview.setAdapter(reviewAdapter);
                                                tvReviewSize.setText("상품 전체 후기(" + reviewList.size() + ")");
                                                DB task3 = new DB(new DB.OnResultListener() {
                                                    @Override
                                                    public void onComplete(boolean result) {
                                                        if (!result) {
                                                            inquireAdapter = new InquireAdapter(inquireList);
                                                            rvQnAList.setAdapter(inquireAdapter);
                                                        }
                                                    }
                                                }, activity, R.id.review_product, inquireList);
                                                task3.execute("http://15.165.152.15//ShowProduct4.php", productID, userID);
                                            }
                                        }
                                    }, activity, R.id.Count_review_fragment_product, reviewList);
                                    task2.execute("http://15.165.152.15//ShowProduct5.php", productID, userID);
                                }
                            }
                        }, activity, R.id.contents_product, productList);
                        task4.execute("http://15.165.152.15//ShowProduct2.php", productID, userID);
                    }
                }
            }, activity, R.id.Average_rating_Product, likeCount);
            task.execute("http://15.165.152.15//ShowProduct1.php", productID, userID);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == 1) {
                    Intent intent = new Intent(this, ProductActivity.class);
                    intent.putExtra("PID", productID);
                    startActivity(intent);
                    finish();
                }
            case 33333:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, ProductActivity.class);
                    intent.putExtra("PID", productID);
                    startActivity(intent);
                    finish();
                }
            case 22222:
                if (resultCode == RESULT_OK) {
                    //
                }
        }
    }
}