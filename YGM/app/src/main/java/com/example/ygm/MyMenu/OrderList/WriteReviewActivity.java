package com.example.ygm.MyMenu.OrderList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.DB.DB;
import com.example.ygm.DB.InsertReviewWithImage;
import com.example.ygm.User;
import com.example.ygm.R;

import java.io.IOException;

public class WriteReviewActivity extends AppCompatActivity {

    ImageView ivProduct;
    TextView tvPname, tvOption;
    RatingBar ratingBar;
    EditText edtHeight, edtWeight;
    EditText edtBody;
    ImageView ivReview;
    ImageButton btnUpload;
    Button btnCancel, btnRegister;

    Uri uri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        Activity activity = this;

        User user = (User) getApplication();

        ivProduct = findViewById(R.id.imageView_product_write_review);
        tvPname = findViewById(R.id.textView_product_name_write_review);
        tvOption = findViewById(R.id.textView_product_option_write_review);
        ratingBar = findViewById(R.id.ratingBar_write_review);
        edtHeight = findViewById(R.id.editTextHeight_write_review);
        edtWeight = findViewById(R.id.editTextWeight_write_review);
        edtBody = findViewById(R.id.editTextTextReview);
        ivReview = findViewById(R.id.imageView_img_write_review);
        btnUpload = findViewById(R.id.imageButton_img_upload_write_review);
        btnCancel = findViewById(R.id.button_cancel_write_review);
        btnRegister = findViewById(R.id.button_register_write_review);

        ReviewItem item = (ReviewItem) getIntent().getSerializableExtra("product");

        Glide.with(this).load(item.getImg()).override(Target.SIZE_ORIGINAL).into(ivProduct);
        tvPname.setText(item.getName());
        tvOption.setText(item.getOption());
        edtHeight.setText(Integer.toString(user.getHeight()));
        edtWeight.setText(Integer.toString(user.getWeight()));

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h, w;
                try {
                    h = Integer.parseInt(edtHeight.getText().toString());
                } catch(NumberFormatException e) {
                    h = 0;
                }

                try {
                    w = Integer.parseInt(edtWeight.getText().toString());
                } catch(NumberFormatException e) {
                    w = 0;
                }

                DB task = new DB(new DB.OnResultListener() {
                    @Override
                    public void onComplete(boolean result) {
                        if (!result) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                            dlg.setTitle("리뷰가 등록되었습니다.");
                            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            dlg.show();
                        }
                    }
                }, activity, R.id.button_register_write_review);
                if (uri == null) {
                    task.execute("http://15.165.152.15//InsertReviewNoimage.php", item.getPID(), item.getSID(), edtBody.getText().toString(), "2020-11-18 17:00:00", Float.toString(ratingBar.getRating()), user.getID(), String.valueOf(user.getLV()), Integer.toString(w), Integer.toString(h));
                } else {
                    try{
                        new InsertReviewWithImage(bitmap, "InsertReview.php", activity, item.getPID(), item.getSID(), edtBody.getText().toString(), "2020-11-18 17:00:00", Float.toString(ratingBar.getRating()), user.getID(), String.valueOf(user.getLV()), Integer.toString(w), Integer.toString(h));
                        AlertDialog.Builder dlg = new AlertDialog.Builder(v.getContext());
                        dlg.setTitle("리뷰가 등록되었습니다.");
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dlg.show();
                    } catch(Exception e) {}
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1 && resultCode == RESULT_OK && intent != null && intent.getData() != null) {

            uri = intent.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivReview.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}