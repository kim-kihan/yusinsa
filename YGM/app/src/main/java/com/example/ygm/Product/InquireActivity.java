package com.example.ygm.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.DB.DB;
import com.example.ygm.DB.InsertInquiryWithImage;
import com.example.ygm.User;
import com.example.ygm.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InquireActivity extends AppCompatActivity {

    Spinner spinnerInquireOption;
    EditText edtTitle, edtContent;
    TextView tvProductTitle;
    ImageView ivContentImage, ivProductImage;
    String ProductID, UserID, thumb, ProductContent;
    Button btnCancel, btnConfirm, btnInsertImage;

    Uri uri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inquire);

        setResult(RESULT_CANCELED);

        User user = (User) getApplication();

        Activity activity = this;

        ProductID = getIntent().getStringExtra("PID");
        UserID = user.getUID();
        thumb = getIntent().getStringExtra("resId");
        ProductContent = getIntent().getStringExtra("title1");

        spinnerInquireOption = findViewById(R.id.category_inquire_product_inquire);
        edtTitle = findViewById(R.id.title_inquire_product_inquire);
        edtContent = findViewById(R.id.content_inquire_product_inquire);
        ivContentImage = findViewById(R.id.photo_inquire_product_inquire);
        ivProductImage = findViewById(R.id.image_product_inquire);
        tvProductTitle = findViewById(R.id.title_product_product_inquire);
        btnCancel = findViewById(R.id.cancel_product_inquire);
        btnConfirm = findViewById(R.id.confirm_product_inquire);
        btnInsertImage = findViewById(R.id.button_photo_product_inquire);

        Glide.with(this).load(thumb)
                .fitCenter()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                .into(ivProductImage);
        tvProductTitle.setText(ProductContent);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = mFormat.format(date);
                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            Toast toast1 = Toast.makeText(activity, "등록이 완료되었습니다.", Toast.LENGTH_SHORT);
                            toast1.show();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                }, activity, R.id.image_product_inquire);
                if (uri == null) {
                    task.execute("http://15.165.152.15//InsertProductQNoimage.php", UserID, ProductID, edtTitle.getText().toString(), edtContent.getText().toString(), time, spinnerInquireOption.getSelectedItem().toString());
                } else {
                    try {
                        new InsertInquiryWithImage(bitmap, "InsertProductQ.php", activity, UserID, ProductID, edtTitle.getText().toString(), edtContent.getText().toString(), time, "TYPE");
                        Toast toast1 = Toast.makeText(activity, "등록이 완료되었습니다.", Toast.LENGTH_SHORT);
                        toast1.show();
                        setResult(RESULT_OK);
                        finish();
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }
                }
            }
        });

        btnInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivContentImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}